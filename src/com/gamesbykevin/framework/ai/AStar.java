package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.maze.Room;
import com.gamesbykevin.framework.maze.Room.Wall;
import com.gamesbykevin.framework.resources.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This is the A* algorithm.<br>
 * This is commonly used in video games for pathfinding.<br>
 * This algorithm will find the shortest path between two points on a 2d grid with obstacles
 * @author GOD
 */
public class AStar implements Disposable
{
    /**
     * The start position
     */
    private int startColumn, startRow;
    
    /**
     * The goal
     */
    private int goalColumn, goalRow;
    
    /**
     * Our rooms upon which to identify the path
     */
    private Room[][] rooms;
    
    /**
     * Do we allow diagonal movement while pathfinding
     */
    private boolean diagonal = false;
    
    /**
     * List of open Nodes to check
     */
    private List<Node> open;
    
    /**
     * List of closed Nodes we have already checked
     */
    private List<Node> closed;
    
    /**
     * Did we start path finding
     */
    private boolean start = false;
    
    /**
     * Have we finished the process
     */
    private boolean finish = false;
    
    /**
     * The additional cost to add when moving vertical or horizontal
     */
    protected static final int MOVE_VERTICAL_HORIZONTAL = 10;
    
    /**
     * The additional cost to add when moving diagonal
     */
    protected static final int MOVE_DIAGONAL = 14;
    
    /**
     * This list will contain the shortest path
     */
    private List<Cell> path;
    
    /**
     * Create the A* for pathfinding
     * @param start Start location
     * @param finish Finish location
     * @param rooms Array of Rooms where we need to identify the path
     */
    public AStar(final Cell start, final Cell finish, final Room[][] rooms)
    {
        this((int)start.getCol(), (int)start.getRow(), (int)finish.getCol(), (int)finish.getRow(), rooms);
    }
    
    /**
     * Create the A* for pathfinding
     * @param startColumn Start Column
     * @param startRow Start Row
     * @param goalColumn Goal Column
     * @param goalRow Goal Row
     * @param rooms Array of Rooms where we need to identify the path
     */
    public AStar(final int startColumn, final int startRow, final int goalColumn, final int goalRow, final Room[][] rooms)
    {
        //assign the start
        setStartColumn(startColumn);
        setStartRow(startRow);
        
        //assign the goal
        setGoalColumn(goalColumn);
        setGoalRow(goalRow);
        
        //assign the room which to do path finding
        setRooms(rooms);
        
        //create a new open and closed list
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        
        //create a new path list
        this.path = new ArrayList<>();
    }
    
    /**
     * Get the shortest path.<br>
     * The first cell in the list will be the goal, the last cell in the list will be the start
     * @return A list of the shortest path from start to finish
     */
    public List<Cell> getShortestPath()
    {
        return this.path;
    }
    
    /**
     * Assign diagonal pathfinding
     * @param diagonal true if we are allowed to move diagonal while pathfinding, otherwise false
     */
    public final void setDiagonal(final boolean diagonal)
    {
        this.diagonal = diagonal;
    }
    
    /**
     * Assign the rooms
     * @param rooms The rooms which we will identify the shortest path
     */
    public final void setRooms(final Room[][] rooms)
    {
        this.rooms = rooms;
    }
    
    /**
     * Set the start column.
     * @param startColumn The position where we will begin pathfinding
     */
    public final void setStartColumn(final int startColumn)
    {
        this.startColumn = startColumn;
    }
    
    /**
     * Set the start row.
     * @param startRow The position where we will begin pathfinding
     */
    public final void setStartRow(final int startRow)
    {
        this.startRow = startRow;
    }
    
    /**
     * Set the goal column.
     * @param goalColumn The position where we will end pathfinding
     */
    public final void setGoalColumn(final int goalColumn)
    {
        this.goalColumn = goalColumn;
    }
    
    /**
     * Set the goal row.
     * @param goalRow The position where we will end pathfinding
     */
    public final void setGoalRow(final int goalRow)
    {
        this.goalRow = goalRow;
    }
    
    /**
     * Get the room at the specified location
     * @param column Column
     * @param row Row
     * @return The room at the specified location, if out of bounds null is returned
     */
    private Room getRoom(final int column, final int row)
    {
        //if we don't have bounds, return null
        if (!hasBounds(column, row))
            return null;
        
        //get the specified room
        return this.rooms[row][column];
    }
    
    /**
     * Start pathfinding to locate the shortest path
     */
    public void generate()
    {
        //temporary node
        Node node;
        
        //the current location
        int column, row;
        
        //continue until we locate the path
        while (true)
        {
            if (!start)
            {
                //assign the (column, row)
                column = startColumn;
                row = startRow;

                //create the start node
                node = new Node(column, row, null, 0, getHeuristic(column, row));

                //flag true
                start = true;
            }
            else 
            {
                /**
                 * Identify the lowest cost node
                 */
                node = getLowestCost();

                //assign the (column, row)
                column = node.getColumn();
                row = node.getRow();
            }

            //remove this node from the open list
            removeOpen(node);

            //add this node to the closed list
            addClosed(node);

            //if we found the goal and added it to the closed list
            if (column == goalColumn && row == goalRow)
            {
                //get the node from the closed list
                node = getClosed(column, row);

                /**
                 * Start with the goal node added and follow the parent backwards until you get back to the start.<br>
                 * This will create the shortest path
                 */
                while (column != startColumn || row != startRow)
                {
                    //add this location to the path
                    path.add(new Cell(column, row));

                    //get the parent node
                    node = getClosed(node.getParentId());

                    //now assign the new location
                    column = node.getColumn();
                    row = node.getRow();
                }

                //add this location to the path
                path.add(new Cell(column, row));

                //we are finished
                finish = true;

                //exit the loop
                break;
            }
            else
            {
                //get the current room so we can determine the valid movement options
                Room room = getRoom(column, row);

                //if there is no wall, add it to the open list
                if (!room.hasWall(Wall.East))
                    addOpen(column + 1, row, node, MOVE_VERTICAL_HORIZONTAL);
                if (!room.hasWall(Wall.West))
                    addOpen(column - 1, row, node, MOVE_VERTICAL_HORIZONTAL);
                if (!room.hasWall(Wall.North))
                    addOpen(column, row - 1, node, MOVE_VERTICAL_HORIZONTAL);
                if (!room.hasWall(Wall.South))
                    addOpen(column, row + 1, node, MOVE_VERTICAL_HORIZONTAL);

                /**
                 * Make sure diagonal movement is allowed before checking
                 */
                if (diagonal)
                {
                    if (!room.hasWall(Wall.East) && !room.hasWall(Wall.North))
                        addOpen(column + 1, row - 1, node, MOVE_DIAGONAL);
                    if (!room.hasWall(Wall.West) && !room.hasWall(Wall.North))
                        addOpen(column - 1, row - 1, node, MOVE_DIAGONAL);
                    if (!room.hasWall(Wall.East) && !room.hasWall(Wall.South))
                        addOpen(column + 1, row + 1, node, MOVE_DIAGONAL);
                    if (!room.hasWall(Wall.West) && !room.hasWall(Wall.South))
                        addOpen(column - 1, row + 1, node, MOVE_DIAGONAL);
                }
            }
        }
    }
    
    /**
     * Remove the node from the open list
     * @param node The node we want to remove
     */
    private void removeOpen(final Node node)
    {
        for (int index = 0; index < open.size(); index++)
        {
            //if the locations match, remove it from the open list
            if (open.get(index).hasLocation(node))
                open.remove(index);
        }
    }
    
    /**
     * Get the node with the lowest cost
     * @return The node with the lowest cost in the open list
     */
    private Node getLowestCost()
    {
        //the winning node
        Node node = null;
        
        for (int index = 0; index < open.size(); index++)
        {
            //get the current node
            Node tmp = open.get(index);
            
            //if this node has a lower cost
            if (node == null || tmp.getCost() < node.getCost())
            {
                //assign the winning node
                node = tmp;
            }
        }
        
        //return the winnning node
        return node;
    }
    
    /**
     * Add this location to the open list
     * @param column Column
     * @param row Row
     * @param parent The parent node
     * @param movementCost The movement cost
     */
    private void addOpen(final int column, final int row, final Node parent, final int movementCost)
    {
        //if this location is on the closed list, we won't add to open
        if (hasClosed(column, row))
            return;
        
        /**
         * If the location is already in the open list, <br>
         * we need to check and see if the movement is better than 
         */
        if (hasOpen(column, row))
        {
            /**
             * If the new movement cost is lower than the existing, we need to update the existing node
             */
            if (parent.getMovement() + movementCost < getOpen(column, row).getMovement())
            {
               //assign the new movement cost
               getOpen(column, row).setMovement(parent.getMovement() + movementCost); 
               
               //assign the new parent
               getOpen(column, row).setParentId(parent);
               
               //no need to continue
               return;
            }
        }
        
        //add this node to the open list with the calculated cost
        open.add(new Node(column, row, parent, movementCost, getHeuristic(column, row)));
    }
    
    /**
     * Add node to the closed list
     * @param node The node we want to add
     */
    private void addClosed(final Node node)
    {
        closed.add(node);
    }
    
    /**
     * Is this location in our closed list?
     * @param column Column
     * @param row Row
     * @return true if a node with the matching (column, row) exists in this list, false otherwise
     */
    private boolean hasClosed(final int column, final int row)
    {
        for (int index = 0; index < closed.size(); index++)
        {
            if (closed.get(index).hasLocation(column, row))
                return true;
        }
        
        return false;
    }
    
    /**
     * Is this location in our open list?
     * @param column Column
     * @param row Row
     * @return true if a node with the matching (column, row) exists in this list, false otherwise
     */
    private boolean hasOpen(final int column, final int row)
    {
        return (getOpen(column, row) != null);
    }
    
    /**
     * Get the node from the closed list
     * @param id The id of the node we are looking for
     * @return The node in the list, if not found return null
     */
    private Node getClosed(final UUID id)
    {
        for (int index = 0; index < closed.size(); index++)
        {
            //if this node has the specified id
            if (closed.get(index).hasId(id))
                return closed.get(index);
        }
        
        //nothing found, return null
        return null;
    }

    /**
     * Get the node from the closed list at the specified location
     * @param column Column
     * @param row Row
     * @return The node in the list at the specified location, if not found return null
     */
    private Node getClosed(final int column, final int row)
    {
        for (int index = 0; index < closed.size(); index++)
        {
            //if this node has the location
            if (closed.get(index).hasLocation(column, row))
                return closed.get(index);
        }
        
        //nothing found, return null
        return null;
    }
    
    /**
     * Get the node in our open list with the matching (column, row)
     * @param column Column
     * @param row Row
     * @return The node at the specified (column, row), if no node is found null is returned
     */
    private Node getOpen(final int column, final int row)
    {
        for (int index = 0; index < open.size(); index++)
        {
            if (open.get(index).hasLocation(column, row))
                return open.get(index);
        }
        
        //return null
        return null;
    }
    
    /**
     * Get the heuristic.<br>
     * This is the number of cells to move until we reach the goal.<br>
     * Diagonal movement is not allowed in this calculation.
     * @param column Column
     * @param row Row
     * @return The calculated heuristic
     */
    private int getHeuristic(final int column, final int row)
    {
        //calculate the horizontal lengh from the specified column to the goal column
        int horizontal = (goalColumn > column) ? goalColumn - column : column - goalColumn;
        
        //calculate the vertical lengh from the specified row to the goal row
        int vertical = (goalRow > row) ? goalRow - row : row - goalRow;
        
        //return the calculated heuristic
        return ((horizontal + vertical) * MOVE_VERTICAL_HORIZONTAL);
    }
    
    /**
     * Do we have bounds?
     * @param column Column
     * @param row Row
     * @return true if the location is within the Room, false otherwise
     */
    private boolean hasBounds(final int column, final int row)
    {
        if (column < 0)
            return false;
        if (row < 0)
            return false;
        if (column > rooms[0].length - 1)
            return false;
        if (row > rooms.length - 1)
            return false;
        
        //we have a valid location
        return true;
    }
    
    @Override
    public void dispose()
    {
        if (rooms != null)
        {
            for (int row = 0; row < rooms.length; row++)
            {
                for (int col = 0; col < rooms[0].length; col++)
                {
                    rooms[row][col].dispose();
                    rooms[row][col] = null;
                }
            }
            
            rooms = null;
        }
        
        if (open != null)
        {
            for (int index = 0; index < open.size(); index++)
            {
                open.set(index, null);
            }

            open.clear();
            open = null;
        }
        
        if (closed != null)
        {
            for (int index = 0; index < closed.size(); index++)
            {
                closed.set(index, null);
            }

            closed.clear();
            closed = null;
        }
        
        if (path != null)
        {
            for (int index = 0; index < path.size(); index++)
            {
                path.set(index, null);
            }
            
            path.clear();
            path = null;
        }
    }
}