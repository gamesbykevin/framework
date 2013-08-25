package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.labyrinth.Location.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using Prim's algorithm
 * @author GOD
 */
public final class Prims extends LabyrinthHelper implements LabyrinthRules
{
    private Location current;
    
    private List<Location> checkWalls;
    
    public Prims(final int cols, final int rows)
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
        
        if (checkWalls != null)
        {
            for (Location cell : checkWalls)
            {
                cell.dispose();
                cell = null;
            }
            
            checkWalls.clear();
            checkWalls = null;
        }
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();
        
        //List
        checkWalls = new ArrayList<>();
        
        //our starting position
        current = getLocation(getStart());
        
        //set Location as part of maze
        current.markVisited();
        
        //update progress
        super.setProgressGoal(super.getLocations().size() - 1);
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
        
        if(!hasVisitedAll())
        {
            //get a List of valid walls for the current Location
            List<Wall> valid = getValidWalls(current);

            //make sure we have walls to check
            if (!valid.isEmpty())
            {
                //while we have valid walls to check
                while (!valid.isEmpty())
                {
                    final int index = (int)(Math.random() * valid.size());

                    //get random wall
                    Location.Wall wall = valid.get(index);

                    //get the random neighbor
                    Location neighbor = getNeighbor(current, wall);
                    
                    //mark the neighbor as visited
                    neighbor.markVisited();
                    
                    //update progress
                    super.getProgress().increase();
                    
                    //remove wall to create passage to neighbor
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

                    //remove the wall from list
                    valid.remove(index);

                    //add neighbor as another spot to check as long as it has valid walls
                    if (getValidWalls(neighbor).size() > 0)
                        checkWalls.add(neighbor);
                }
            }
            else
            {
                //get new random Location
                final int index = (int)(Math.random() * checkWalls.size());
                
                //get Location and set as current
                current = checkWalls.get(index);
                
                //remove from List
                checkWalls.remove(index);
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
}