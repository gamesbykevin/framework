package com.gamesbykevin.framework.labyrinth;

import static com.gamesbykevin.framework.labyrinth.Location.Wall;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using the Hunt and Kill algorithm
 * @author GOD
 */
public final class HuntKill extends LabyrinthHelper implements LabyrinthRules
{
    private Location current;
    
    public HuntKill(final int cols, final int rows)
    {
        super(cols, rows);
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();
        
        //set the goal
        super.setProgressGoal(super.getCells().size() - 1);
        
        //start position
        current = super.getLocation(super.getStart());
        
        //mark as visited
        current.markVisited();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        if (current != null)
            current.dispose();
        
        current = null;
    }
    
    @Override
    public void update() throws Exception
    {
        //initialize() has not been called yet
        if (!super.hasChecked())
            throw new Exception("initialize() must be called first before update()");
        
        //continue until all Locations have been visited
        if (!this.hasVisitedAll())
        {
            List<Location.Wall> valid = getValidWalls(current);

            //is there at least one valid wall
            if (!valid.isEmpty())
            {
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
                
                //neighbor is now current Location
                current = neighbor;
            }
            else
            {
                //we go into hunt mode
                current = getRandomUnvisitedNeighbor();
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
    
    /**
     * Of all the Location(s) in the maze get a random one that has not yet been visited and is a neighbor to a visited Location
     * @return Location
     */
    private Location getRandomUnvisitedNeighbor()
    {
        List<Location> unvisited = new ArrayList<>();
        
        for (Location cell : getCells())
        {
            //good we found one that's been visited so now we need to look for neighbor that hasn't been visited
            if (cell.hasVisited())
            {
                //add valid walls to list
                for (Location.Wall wall : cell.getWalls())
                {
                    //make sure neighbor exists and has not been visited
                    if (getNeighbor(cell, wall) != null && !getNeighbor(cell, wall).hasVisited())
                    {
                        unvisited.add(cell);
                        break;
                    }
                }
            }
        }
        
        return unvisited.get((int)(Math.random() * unvisited.size()));
    }
}