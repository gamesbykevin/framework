/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamesbykevin.framework.labyrinth;

import static com.gamesbykevin.framework.labyrinth.Location.Wall.East;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.North;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.South;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.West;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using Kruskals algorithm
 * @author GOD
 */
public final class Kruskals extends LabyrinthHelper
{
    public Kruskals(final int cols, final int rows)
    {
        super(cols, rows);
    }
    
    public void create() throws Exception
    {
        super.create();
        
        //our starting position
        Location current = getLocation(getStart());

        //this maze will be done when all Locations are part of same group
        while (getGroupCount() > 1)
        {
            List<Location.Wall> valid = new ArrayList<>();

            //add valid walls to list
            for (Location.Wall wall : current.getWalls())
            {
                //make sure neighbor on other side of wall exists and is not part of the same group
                if (getNeighbor(current, wall) != null && current.getGroup() != getNeighbor(current, wall).getGroup())
                    valid.add(wall);
            }

            if (valid.size() > 0)
            {
                Location.Wall wall = valid.get((int)(Math.random() * valid.size()));

                Location neighbor = getNeighbor(current, wall);

                //make all Location(s) that have the same group as the neighbor the same group as part of the current group
                changeGroup(neighbor.getGroup(), current.getGroup());

                //now need to make a passage between the two locations
                current.remove(wall);

                switch(wall)
                {
                    case North:
                        neighbor.remove(Location.Wall.South);
                        break;

                    case South:
                        neighbor.remove(Location.Wall.North);
                        break;

                    case West:
                        neighbor.remove(Location.Wall.East);
                        break;

                    case East:
                        neighbor.remove(Location.Wall.West);
                        break;
                }
            }

            //get the Location with the lowest count that have the same group
            current = getLowestWeight();
        }
    }
    
    /**
     * Change all Location(s) of a specific group to another
     * @param groupStart The current group to search for
     * @param groupEnd   The group we want it to be
     */
    private void changeGroup(final long groupStart, final long groupEnd)
    {
        for (Location cell : getCells())
        {
            //if we found a Location with the group change it accordingly
            if (cell.getGroup() == groupStart)
                cell.setGroup(groupEnd);
            
        }        
    }
    
    /**
     * Get a random Location of the group that has the lowest count of Location(s)
     * @return 
     */
    private Location getLowestWeight()
    {
        //lowest count
        int count = 0;
        
        //lowest count group
        long group = 0;
        
        for (Location cell : getCells())
        {
            //if the current group count is less than the lowest count or we haven't set the lowest count yet
            if (getLocationCount(cell.getGroup()) < count || count == 0)
            {
                count = getLocationCount(cell.getGroup());
                group = cell.getGroup();
            }
        }
        
        List<Location> locations = getGroupLocations(group);
        
        return locations.get((int)(Math.random() * locations.size()));
    }
    
    /**
     * Gets all the Location(s) for a specific group
     * @param group
     * @return List<Location>
     */
    private List<Location> getGroupLocations(final long group)
    {
        List<Location> locations = new ArrayList<>();
        
        for (Location cell : getCells())
        {
            if (cell.getGroup() == group)
                locations.add(cell);
        }
        
        return locations;
    }
    
    /**
     * For Kruskal's algorithm get the count of different unique groups
     * @return int
     */
    private int getGroupCount()
    {
        List<Long> eachGroup = new ArrayList<>();
        
        for (Location cell : getCells())
        {
            if (eachGroup.indexOf(cell.getGroup()) < 0)
                eachGroup.add(cell.getGroup());
        }
        
        return eachGroup.size();
    }
    
    /**
     * Get the count of Location(s) for the parameter group.
     * For Kruskal's algorithm
     * @param group
     * @return int
     */
    private int getLocationCount(final long group)
    {
        int count = 0;
        
        for (Location cell : getCells())
        {
            if (cell.getGroup() == group)
                count++;
        }
        
        return count;
    }
}