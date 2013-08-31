package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.labyrinth.Location.Wall;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using Kruskals algorithm
 * @author GOD
 */
public final class Kruskals extends LabyrinthHelper implements LabyrinthRules
{
    private Location current;
    
    //has the maze been created
    private boolean created = false;
    
    public Kruskals(final int cols, final int rows)
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
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();

        //our starting position
        current = getLocation(getStart());
        
        //the goal will be the maze size
        super.setProgressGoal(getLocations().size());
    }
    
    @Override
    public void update() throws Exception
    {
        //initialize() has not been called yet
        if (!super.hasChecked())
            throw new Exception("initialize() must be called first before update()");
        
        //this maze will be done when all Locations are part of same group
        if (!created)
        {
            List<Wall> valid = new ArrayList<>();

            //add valid walls to list
            for (Wall wall : current.getWalls())
            {
                //make sure neighbor on other side of wall exists and is not part of the same group
                if (getNeighbor(current, wall) != null && current.getGroup() != getNeighbor(current, wall).getGroup())
                    valid.add(wall);
            }

            if (!valid.isEmpty())
            {
                Wall wall = valid.get((int)(Math.random() * valid.size()));

                Location neighbor = getNeighbor(current, wall);

                //make all Location(s) that have the same group as the neighbor the same group as part of the current group
                changeGroup(neighbor.getGroup(), current.getGroup());

                //update progress
                super.getProgress().increase();
                
                //now need to make a passage between the two locations
                current.remove(wall);

                switch(wall)
                {
                    case North:
                        neighbor.remove(Wall.South);
                        break;

                    case South:
                        neighbor.remove(Wall.North);
                        break;

                    case West:
                        neighbor.remove(Wall.East);
                        break;

                    case East:
                        neighbor.remove(Wall.West);
                        break;
                }
            }

            //get the Location with the lowest count that have the same group
            current = getLowestWeight();
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
    
    /**
     * Get a random Location of a group that has the lowest total count of Location(s).
     * Also, check if there is only one group remaining so we know puzzle is complete.
     * 
     * @return Location
     */
    private Location getLowestWeight()
    {
        //lowest count
        int count = 0;
        
        //lowest count group
        long group = 0;
        
        for (Location cell : getLocations())
        {
            int currentLocationCount = getLocationCount(cell.getGroup());
            
            //if the current group count is less than the lowest count or we haven't set the lowest count yet
            if (currentLocationCount < count || count == 0)
            {
                count = currentLocationCount;
                group = cell.getGroup();
            }
        }
        
        //we have created maze
        if (count == getLocations().size())
        {
            created = true;
            return null;
        }
        else
        {
            List<Location> locations = getGroupLocations(group);

            return locations.get((int)(Math.random() * locations.size()));
        }
    }
    
    /**
     * Gets all the Location(s) for a specific group
     * @param group
     * @return List<Location>
     */
    private List<Location> getGroupLocations(final long group)
    {
        List<Location> locations = new ArrayList<>();
        
        for (Location cell : getLocations())
        {
            if (cell.getGroup() == group)
                locations.add(cell);
        }
        
        return locations;
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
        
        for (Location cell : getLocations())
        {
            if (cell.getGroup() == group)
                count++;
        }
        
        return count;
    }
}