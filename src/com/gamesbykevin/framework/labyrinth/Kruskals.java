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
        
        super.setProgressGoal(getLocations().size() - 1);
    }
    
    @Override
    public void update() throws Exception
    {
        //initialize() has not been called yet
        if (!super.hasChecked())
            throw new Exception("initialize() must be called first before update()");
        
        //this maze will be done when all Locations are part of same group
        if (getGroupCount() > 1)
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
                super.getProgress().setCount(super.getProgress().getGoal() - getGroupCount());
                
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
     * Get a random Location of the group that has the lowest count of Location(s)
     * @return 
     */
    private Location getLowestWeight()
    {
        //lowest count
        int count = 0;
        
        //lowest count group
        long group = 0;
        
        for (Location cell : getLocations())
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
    
    /**
     * For Kruskal's algorithm get the count of different unique groups
     * @return int
     */
    private int getGroupCount()
    {
        List<Long> eachGroup = new ArrayList<>();
        
        for (Location cell : getLocations())
        {
            if (eachGroup.indexOf(cell.getGroup()) < 0)
                eachGroup.add(cell.getGroup());
        }
        
        return eachGroup.size();
    }
}