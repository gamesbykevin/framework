package com.gamesbykevin.framework.labyrinth;

import static com.gamesbykevin.framework.labyrinth.Location.Wall.East;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.North;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.South;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.West;
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
        
        if(!hasVisitedAll())
        {
            List<Location.Wall> valid = new ArrayList<>();

            //add valid walls to list
            for (Location.Wall wall : current.getWalls())
            {
                if (getNeighbor(current, wall) != null && !getNeighbor(current, wall).hasVisited())
                    valid.add(wall);
            }

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
                           neighbor.remove(Location.Wall.North);
                           break;

                       case North:
                           neighbor.remove(Location.Wall.South);
                           break;

                       case West:
                           neighbor.remove(Location.Wall.East);
                           break;

                       case East:
                           neighbor.remove(Location.Wall.West);
                           break;
                    }

                    //remove the wall from list
                    valid.remove(index);

                    //add neighbor as another spot to check
                    checkWalls.add(getNeighbor(current, wall));
                }
            }
            else
            {
                current = checkWalls.get((int)(Math.random() * checkWalls.size()));
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
}