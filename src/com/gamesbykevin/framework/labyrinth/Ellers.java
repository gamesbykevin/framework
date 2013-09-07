package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.labyrinth.Location.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using Eller's algorithm
 * @author GOD
 */
public final class Ellers extends LabyrinthHelper implements LabyrinthRules
{
    //the current row we are looking at
    private int currentRow = 0;
    
    //List of Location(s) for the currentRow
    private List<Location> locations;
    
    //current location of List<Location>
    private int index;
    
    public Ellers(final int cols, final int rows)
    {
        super(cols, rows);
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();
        
        super.setProgressGoal(super.getRowCount() - 1);
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
        
        //the maze is not complete until all rows are processed
        if (currentRow < super.getRowCount())
        {
            //if the List of locations is empty get the Locations for the current row
            if (locations == null || locations.isEmpty())
                locations = getLocations(currentRow);

            //do we randomly create a passage to the west, and make sure we are in bounds
            if (Math.random() > .5 && index > 0)
            {
                if (locations.get(index).getGroup() != locations.get(index - 1).getGroup())
                {
                    //create a passage between the two Locations
                    locations.get(index).remove(Wall.West);
                    locations.get(index - 1).remove(Wall.East);

                    //change all Locations of the same group to another
                    super.changeGroup(locations.get(index - 1).getGroup(), locations.get(index).getGroup());
                }
            }
            else
            {
                //create a passage to the east

                //make sure that our index is in bounds so we can check
                if (index < locations.size() - 1)
                {
                    if (locations.get(index).getGroup() != locations.get(index + 1).getGroup())
                    {
                        //create a passage between the two Locations
                        locations.get(index).remove(Wall.East);
                        locations.get(index + 1).remove(Wall.West);

                        //change all Locations of the same group to another
                        super.changeGroup(locations.get(index + 1).getGroup(), locations.get(index).getGroup());
                    }
                }
            }
            
            //move to the next
            index++;

            //have we completed the List of Location(s)
            if (index == locations.size() - 1)
            {
                //make sure we haven't processed the last row yet
                if (currentRow < super.getRowCount() - 1)
                {
                    //reset index
                    index = 0;
                    
                    //List where each item contains a different group for the current row
                    List<Long> groups = new ArrayList<>();

                    //create a list of all the unique groups
                    for (Location cell : locations)
                    {
                        //if the group is not in our List yet
                        if (groups.indexOf(cell.getGroup()) < 0)
                            groups.add(cell.getGroup());
                    }

                    //now we have our unique groups and need to pick a random location from each one
                    for (Long group : groups)
                    {
                        //get list of locations from the current row that are in the same group
                        List<Location> locationGroupRow = getLocationsGroup(currentRow, group);

                        //here we will take a random number (at least 1) Location(s) and make passages with the South Locaitons
                        int limit = (int)(Math.random() * (locationGroupRow.size() - 1)) + 1;

                        while (limit > 0)
                        {
                            final int randomIndex = (int)(Math.random() * locationGroupRow.size());

                            //get random Location from List
                            Location random = locationGroupRow.get(randomIndex);

                            //get the Location below the current one
                            Location southNeighbor = super.getLocation(random.getCol(), random.getRow() + 1);

                            //create a passage between Locations
                            random.remove(Wall.South);
                            southNeighbor.remove(Wall.North);

                            //also change group of new Location so 
                            super.changeGroup(southNeighbor.getGroup(), random.getGroup());

                            //remove from List
                            locationGroupRow.remove(randomIndex);
                            limit--;
                        }
                    }
                }
                else
                {
                    //if we are on the last row make sure every adjacent Location is part of the same group
                    for (int i=0; i < locations.size(); i++)
                    {
                        if (i > 0)
                        {
                            if (locations.get(i).getGroup() != locations.get(i - 1).getGroup())
                            {
                                //create a passage between the two Locations
                                locations.get(i).remove(Wall.West);
                                locations.get(i - 1).remove(Wall.East);

                                //change all Locations of the same group to another
                                super.changeGroup(locations.get(i - 1).getGroup(), locations.get(i).getGroup());
                            }
                        }

                        if (i < locations.size() - 1)
                        {
                            if (locations.get(i).getGroup() != locations.get(i + 1).getGroup())
                            {
                                //create a passage between the two Locations
                                locations.get(i).remove(Wall.East);
                                locations.get(i + 1).remove(Wall.West);

                                //change all Locations of the same group to another
                                super.changeGroup(locations.get(i + 1).getGroup(), locations.get(i).getGroup());
                            }
                        }
                    }
                }
                
                //lets go to the next row
                currentRow++;

                //clear all items from list since finished row
                locations.clear();
                
                if (currentRow < super.getRowCount() - 1)
                {
                    //increase progress
                    super.getProgress().increase();
                }
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
    
    /**
     * Get a List of Location(s) that are all in the same row and group.
     * 
     * @param row
     * @param group
     * @return List<Location>
     */
    private List<Location> getLocationsGroup(final int row, final long group)
    {
        //get all locations in the same row
        List<Location> locationsGroup = getLocations(row);
        
        for (int i=0; i < locationsGroup.size(); i++)
        {
            if (locationsGroup.get(i).getGroup() != group)
            {
                locationsGroup.remove(i);
                i--;
            }
        }
        
        return locationsGroup;
    }
    
    /**
     * Get a List of Location(s) that are all in the same row and order them by their column in ascending order
     * @param row
     * @return List<Location>
     */
    private List<Location> getLocations(final int row)
    {
        List<Location> orderedLocations = new ArrayList<>();
        
        //loop through all the Locations in the current row
        for (int col=0; col < super.getColumnCount(); col++)
        {
            orderedLocations.add(getLocation(col, row));
        }
        
        return orderedLocations;
    }
}