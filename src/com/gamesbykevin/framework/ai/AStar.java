package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.labyrinth.Location;
import com.gamesbykevin.framework.labyrinth.Location.Wall;
import com.gamesbykevin.framework.resources.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * This is my implementation of the A* algorithm.
 * This is used for calculating the shortest path.
 * 
 * @author GOD
 */
public class AStar implements Disposable
{
    //our start position
    private Node start;
    
    //the destination
    private Node goal;
    
    //list of locations to be considered
    private List<Node> open;
    
    //list of locations that we have already checked
    private List<Node> closed;
    
    //the map of the playing area
    private List<Location> map;
    
    //the path to the goal
    private List<Cell> path;
    
    //when calculating the path are diagonal moves allowed
    private boolean diagonal = false;
    
    //the cost moving in a diagonal direction (NW, NE, SE, SW)
    private static final int DIAGONAL_COST = 14;
    
    //the cost moving in a non-diagonal direction (W, S, E, N)
    private static final int NON_DIAGONAL_COST = 10;
    
    public AStar()
    {
        //create new open list
        this.open = new ArrayList<>();
        
        //create new closed list
        this.closed = new ArrayList<>();
        
        //create new list containing the path to the goal
        this.path = new ArrayList<>();
    }
    
    @Override
    public void dispose()
    {
        start = null;
        goal = null;
    
        if (map != null)
        {
            for (Location location : map)
            {
                if (location != null)
                    location.dispose();
                
                location = null;
            }
            
            map.clear();
            map = null;
        }
    
        open.clear();
        open = null;
        
        closed.clear();
        closed = null;
        
        path.clear();
        path = null;
    }
    
    /**
     * Get the shortest calculated path
     * @return List of steps needed to follow to reach the goal
     */
    public List<Cell> getPath()
    {
        return this.path;
    }
    
    /**
     * Are we to allow diagonal movement when calculating the shortest path
     * @param diagonal True if allowed, otherwise false
     */
    public void setDiagonal(final boolean diagonal)
    {
        this.diagonal = diagonal;
    }
    
    public boolean hasDiagonal()
    {
        return this.diagonal;
    }
    
    public void setStart(final Cell start)
    {
        setStart((int)start.getCol(), (int)start.getRow());
    }
    
    public void setStart(final int col, final int row)
    {
        this.start = new Node(col, row);
    }
    
    public void setGoal(final Cell goal)
    {
        setGoal((int)goal.getCol(), (int)goal.getRow());
    }
    
    public void setGoal(final int col, final int row)
    {
        this.goal = new Node(col, row);
    }
    
    public Node getStart()
    {
        return this.start;
    }
    
    public Node getGoal()
    {
        return this.goal;
    }
    
    /**
     * Set the map of the area we want to calculate the path.
     * @param map 
     */
    public void setMap(final List<Location> map)
    {
        this.map = map;
    }
    
    /**
     * Add node with no parent node
     * @param cell 
     */
    private void addOpen(final Cell cell)
    {
        addOpen(cell, null);
    }
    
    /**
     * Add node to the open list.<br><br>
     * If the node already exists another will not be added.<br><br>
     * In addition if the Cell belongs to the closed list it also will not be added.
     * @param cell The location we want to use to create a new node and add it to the list
     * @param parent The parent node of the one we are adding
     */
    private void addOpen(final Cell cell, final Node parent)
    {
        //do not add if the cell doesn't exist
        if (cell == null)
            return;
        
        //if the Cell exists in the closed list it will not be added
        for (Cell tmp : closed)
        {
            //if Cell already exists do not add to list
            if (tmp.equals(cell))
                return;
        }
        
        //create new Node
        Node node = new Node(cell);
        
        //make sure the parent exists
        if (parent != null)
        {
            //set the parent
            node.setParent(parent);
            
            //determine if this new node is diagonal or not
            if (parent.getCol() == cell.getCol() || parent.getRow() == cell.getRow())
            {
                node.setCost(parent.getCost() + NON_DIAGONAL_COST);
            }
            else
            {
                node.setCost(parent.getCost() + DIAGONAL_COST);
            }
        }
        
        //check to see if the Cell exists already
        for (int index = 0; index < open.size(); index++)
        {
            Node tmp = open.get(index);
            
            //if the cell already exists
            if (tmp.equals(cell))
            {
                //determine if the new node will have a lower cost
                if (node.getCost() < tmp.getCost())
                {
                    //since the new node has a lower cost remove the current one from the list
                    open.remove(index);
                    
                    //no need to continue loop
                    break;
                }
                else
                {
                    //the node already exists and the existing node is a lower cost so we do nothing
                    return;
                }
            }
        }
        
        //add the new node to open list
        open.add(node);
    }
    
    private void addClosed(final Node node)
    {
        this.closed.add(node);
    }
    
    /**
     * Remove cell from open list
     * @param cell 
     */
    private void removeOpen(final Cell cell)
    {
        for (int index=0; index < open.size(); index++)
        {
            if (open.get(index).equals(cell))
            {
                open.remove(index);
                break;
            }
        }
    }
    
    /**
     * Here we will check the open list for the lowest cost node.<br><br>
     * If the open list is empty we will return the start location.
     * @return Node
     */
    private Node getLowestCost()
    {
        if (open.isEmpty())
            return getStart();
        
        Node tmp = null;
        
        //an estimate of the highest maximum cost, we start out high
        int maxCost = (DIAGONAL_COST * map.size());
        
        //check each node in the open list
        for (Node node : open)
        {
            //calculate the total cost of the current node
            final int cost = getHeuristic(node) + node.getCost();
            
            //if the cost of the node is less than or equal to the max cost
            if (cost <= maxCost)
            {
                //the lowest cost node
                tmp = node;
                
                //lower the max cost to the current cost
                maxCost = cost;
            }
        }
        
        return tmp;
    }
    
    /**
     * Calculate the heuristic of the current node.<br><br>
     * The heuristic is the distance from the node to the goal <br>
     * ignoring all obstacles in play and no diagonal movement is allowed.
     * 
     * @param node
     * @return The heuristic
     */
    private int getHeuristic(final Node node)
    {
        //get the number of columns from our goal
        final int columnDistance;

        if (node.getCol() > getGoal().getCol())
        {
            columnDistance = (int)(node.getCol() - getGoal().getCol());
        }
        else if (node.getCol() < getGoal().getCol())
        {
            columnDistance = (int)(getGoal().getCol() - node.getCol());
        }
        else
        {
            columnDistance = 0;
        }

        //get the number of rows from our goal
        final int rowDistance;

        if (node.getRow() > getGoal().getRow())
        {
            rowDistance = (int)(node.getRow() - getGoal().getRow());
        }
        else if (node.getRow() < getGoal().getRow())
        {
            rowDistance = (int)(getGoal().getRow() - node.getRow());
        }
        else
        {
            rowDistance = 0;
        }
        
        //the heuristic cost
        return ((columnDistance + rowDistance) * NON_DIAGONAL_COST);
    }
    
    /**
     * Clear the open, closed, and path Lists
     */
    private void reset()
    {
        //clear list
        open.clear();
        
        //clear list
        closed.clear();
        
        //clear list
        path.clear();
    }
    
    /**
     * Calculate the shortest path
     * @throws Exception if the shortest path could not be found
     */
    public void calculate() throws Exception
    {
        if (getStart() == null)
            throw new Exception("Please set start position");
        
        if (getGoal() == null)
            throw new Exception("Please set goal position");
        
        if (map == null)
            throw new Exception("Please set the map");
        
        //reset all lists
        reset();
        
        while(true)
        {
            //get the lowest cost node
            Node current = getLowestCost();

            //add current Node to open list if it isn't already and is not in the closed list
            addOpen(current);

            //get the current location
            final Location location = getLocation(current);

            //check for any open places
            checkOpen(location, current);

            //remove the current Node from the open list
            removeOpen(current);

            //add the Node to the closed list because we don't need to check it for now
            addClosed(current);
            
            //we have added the goal to the closed list so exit loop
            if (current.equals(getGoal()))
                break;
            
            //if the open list is empty throw exception
            if (open.isEmpty())
                throw new Exception("Path to goal could not be found");
        }
        
        //the most recent added to the closed list is the goal
        Node current = closed.get(closed.size() - 1);
        
        //add node to our path
        path.add(new Cell(current));
        
        //continue to backtrack until we have reached the start location
        while(true)
        {
            //look for the parent node
            for (Node node : closed)
            {
                //we have found the parent
                if (node.getId() == current.getParent())
                {
                    //mark parent node as the current
                    current = node;
                    
                    //add current node to our path
                    path.add(new Cell(current));
                    
                    //exit loop
                    break;
                }
            }
            
            //if the current node equals our start position we are done
            if (current.equals(getStart()))
                break;
        }
        
    }
    
    /**
     * With the current location check for any open neighbors
     * @param location
     * @param current 
     */
    private void checkOpen(final Location location, final Node current)
    {
        //if there is no wall to the north
        if (!location.hasWall(Wall.North))
        {
            //add new location to list if appropriate and set the parent
            addOpen(getLocation(current.getCol(), current.getRow() - 1), current);
        }

        //if there is no wall to the east
        if (!location.hasWall(Wall.East))
        {
            //add new location to list if appropriate and set the parent
            addOpen(getLocation(current.getCol() + 1, current.getRow()), current);
        }

        //if there is no wall to the south
        if (!location.hasWall(Wall.South))
        {
            //add new location to list if appropriate and set the parent
            addOpen(getLocation(current.getCol(), current.getRow() + 1), current);
        }

        //if there is no wall to the west
        if (!location.hasWall(Wall.West))
        {
            //add new location to list if appropriate and set the parent
            addOpen(getLocation(current.getCol() - 1, current.getRow()), current);
        }

        //if we are allowed to check diagonal locations then we will
        if (this.hasDiagonal())
        {
            //if there is no wall to the west or north
            if (!location.hasWall(Wall.West) || !location.hasWall(Wall.North))
            {
                //add new location to list if appropriate and set the parent
                addOpen(getLocation(current.getCol() - 1, current.getRow() - 1), current);
            }

            //if there is no wall to the east or north
            if (!location.hasWall(Wall.East) || !location.hasWall(Wall.North))
            {
                //add new location to list if appropriate and set the parent
                addOpen(getLocation(current.getCol() + 1, current.getRow() - 1), current);
            }

            //if there is no wall to the east or south
            if (!location.hasWall(Wall.East) || !location.hasWall(Wall.South))
            {
                //add new location to list if appropriate and set the parent
                addOpen(getLocation(current.getCol() + 1, current.getRow() + 1), current);
            }

            //if there is no wall to the west or south
            if (!location.hasWall(Wall.West) || !location.hasWall(Wall.South))
            {
                //add new location to list if appropriate and set the parent
                addOpen(getLocation(current.getCol() - 1, current.getRow() + 1), current);
            }
        }
    }
    
    /**
     * Get the location at the specified cell.<br>
     * If the location is not found null will be returned.
     * 
     * @param cell
     * @return Location containing a list of existing walls
     */
    private Location getLocation(final Cell cell)
    {
        return getLocation(cell.getCol(), cell.getRow());
    }
    
    /**
     * Get the location at the specified column, row.<br>
     * If the location is not found null will be returned.
     * 
     * @param column
     * @param row
     * @return Location containing a list of existing walls
     */
    private Location getLocation(final double col, final double row)
    {
        for (Location location : map)
        {
            if (location.equals(col, row))
                return location;
        }
        
        return null;
    }
}