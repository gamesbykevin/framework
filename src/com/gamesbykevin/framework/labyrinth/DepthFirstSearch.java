package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.labyrinth.Location.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using the DepthFirstSearch algorithm
 * @author GOD
 */
public final class DepthFirstSearch extends LabyrinthHelper implements LabyrinthRules
{
    private Location current;
    
    private List<Location> stack;
    
    public DepthFirstSearch(final int cols, final int rows)
    {
        super(cols, rows);
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        if (current != null)
            current.dispose();
        
        current = null;
        
        if (stack != null)
        {
            for (Location cell : stack)
            {
                cell.dispose();
                cell = null;
            }
            
            stack.clear();
            stack = null;
        }
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();

        //our starting position
        current = getLocation(getStart());

        //mark the starting location as visited
        current.markVisited();

        stack = new ArrayList<>();
        
        super.setProgressGoal(super.getCells().size() - 1);
    }
    
    /**
     * Creates the maze
     */
    @Override
    public void update() throws Exception
    {
        //initialize() has not been called yet
        if (!super.hasChecked())
            throw new Exception("initialize() must be called first before update()");
        
        //continue until every cell has been visited
        if (!hasVisitedAll())
        {
            List<Wall> valid = getValidWalls();

            //is there at least one valid wall
            if (!valid.isEmpty())
            {
                //if there is more than 1 valid wall we may use this Location again in the future, and we don't already have in List
                if (valid.size() > 1 && !hasStackLocation(current))
                    stack.add(current);
                
                final int index = (int)(Math.random() * valid.size());
                
                //get random wall
                Location.Wall wall = valid.get(index);
                
                //get random neighbor
                Location neighbor = getNeighbor(current, wall);
                
                //mark the neighbor as visited
                neighbor.markVisited();
                
                //update progress
                super.getProgress().increase();
                
                //remove wall from current Locaiton to create passage to neighbor
                current.remove(wall);
                
                //remove the wall from the neighbor to make passage to the current Location
                switch(wall)
                {
                   case South:
                       neighbor.remove(Wall.North);
                       break;

                   case North:
                       neighbor.remove(Wall.South);
                       break;

                   case West:
                       neighbor.remove(Wall.East);
                       break;

                   case East:
                       neighbor.remove(Wall.West);
                       break;
                }
                
                //now the neighbor is the current Location
                current = neighbor;
            }
            else
            {
                if (stack.size() > 0)
                {
                    //continue until we find a Location that has at least 1 valid wall
                    while(stack.size() > 0)
                    {
                        //get a random location from the List stack
                        final int index = (int)(Math.random() * stack.size());

                        //get a random Location from our List stack and mark visited
                        current = stack.get(index);
                        current.markVisited();

                        //remove Location from stack
                        stack.remove(index);

                        //if the new current Location has at least 1 valid wall we can continue
                        if (getValidWalls().size() > 0)
                            break;
                    }
                }
                else
                {
                    //get a random un-visited Location
                    current = getRandomUnvisited();
                    current.markVisited();
                }
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
    
    /**
     * Get a List of Wall. The logic is to make 
     * sure the neighbor exists and the neighbor 
     * hasn't been visited yet.
     * 
     * @return List<Wall>
     */
    private List<Wall> getValidWalls()
    {
        List<Wall> valid = new ArrayList<>();

        //add valid walls to list
        for (Wall wall : current.getWalls())
        {
            //make sure neighbor exists and has not been visited
            if (getNeighbor(current, wall) != null && !getNeighbor(current, wall).hasVisited())
                valid.add(wall);
        }
        
        return valid;
    }
    
    /**
     * Do we already have this Location in the List stack.
     * Will only check the row/column of the Locations to 
     * determine if they already exist.
     * 
     * @param current Location we want to check for
     * @return boolean
     */
    private boolean hasStackLocation(final Location current)
    {
        for (Location cell : stack)
        {
            if (cell.equals(current))
                return true;
        }
        
        return false;
    }
    
    /**
     * Of all the Location(s) in the maze get a random one that has not yet been visited
     * @return Location
     */
    private Location getRandomUnvisited()
    {
        List<Location> unvisited = new ArrayList<>();
        
        for (Location cell : getCells())
        {
            if (!cell.hasVisited())
                unvisited.add(cell);
        }
        
        return unvisited.get((int)(Math.random() * unvisited.size()));
    }
}