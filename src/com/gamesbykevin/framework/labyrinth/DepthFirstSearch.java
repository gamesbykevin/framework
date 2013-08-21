package com.gamesbykevin.framework.labyrinth;

import static com.gamesbykevin.framework.labyrinth.Location.Wall.East;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.North;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.South;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.West;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using the DepthFirstSearch algorithm
 * @author GOD
 */
public final class DepthFirstSearch extends LabyrinthHelper
{
    public DepthFirstSearch(final int cols, final int rows)
    {
        super(cols, rows);
    }
    
    /**
     * Creates the maze
     */
    public void create() throws Exception
    {
        super.create();
        
        //our starting position
        Location current = getLocation(getStart());

        //mark the starting location as visited
        current.markVisited();

        List<Location> stack = new ArrayList<>();

        //loop until every cell has been visited
        while (!hasVisitedAll())
        {
            List<Location.Wall> valid = new ArrayList<>();

            //add valid walls to list
            for (Location.Wall wall : current.getWalls())
            {
                //make sure neighbor exists and has not been visited
                if (getNeighbor(current, wall) != null && !getNeighbor(current, wall).hasVisited())
                    valid.add(wall);
            }
            
            //is there at least one valid wall
            if (!valid.isEmpty())
            {
                //add the current Location to the stack
                stack.add(current);
                
                final int index = (int)(Math.random() * valid.size());
                
                //get random wall
                Location.Wall wall = valid.get(index);
                
                //get random neighbor
                Location neighbor = getNeighbor(current, wall);
                
                //mark the neighbor as visited
                neighbor.markVisited();
                
                //remove wall from current Locaiton to create passage to neighbor
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
                
                //now the neighbor is the current Location
                current = neighbor;
            }
            else
            {
                if (stack.size() > 0)
                {
                    current = stack.get((int)(Math.random() * stack.size()));
                    current.markVisited();
                }
                else
                {
                    current = getRandomUnvisited();
                    current.markVisited();
                }
            }
        }
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