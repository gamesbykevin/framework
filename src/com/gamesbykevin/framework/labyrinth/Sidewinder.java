package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.labyrinth.Location.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a Labyrinth using the Sidewinder algorithm
 * @author GOD
 */
public final class Sidewinder extends LabyrinthHelper implements LabyrinthRules
{
    private int currentRow = 0;
    
    private Location current;
    
    private List<Location> currentSet;
    
    public Sidewinder(final int cols, final int rows)
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
        
        if (currentSet != null)
        {
            for (Location cell : currentSet)
            {
                cell.dispose();
                cell = null;
            }
            
            currentSet.clear();
            currentSet = null;
        }
    }
    
    @Override
    public void initialize() throws Exception
    {
        //verify initial variables are set
        super.check();
        
        current = super.getLocation(super.getStart());
        
        currentSet = new ArrayList<>();
        
        super.setProgressGoal(super.getRowCount());
    }
    
    @Override
    public void update() throws Exception
    {
        //initialize() has not been called yet
        if (!super.hasChecked())
            throw new Exception("initialize() must be called first before update()");
        
        //loop until we process all rows
        if (currentRow < super.getRowCount())
        {
            //add the current position to the run set
            currentSet.add(current);

            //if random is true and we can still create a passage east, or if we are in the first row
            if (Math.random() > .5 && current.getCol() + 1 < super.getColumnCount() || currentRow == 0)
            {
                //if there is another Location to the east create a passage
                if (getLocationEast(current) != null)
                {
                    //remove walls to create passage
                    current.remove(Wall.East);
                    getLocationEast(current).remove(Wall.West);

                    //the tmp location is now the current one
                    current = getLocationEast(current);
                }
                else
                {
                    beginNextRow();
                }
            }
            else
            {
                //a passage was not created east so get a random Location from List set
                Location tmp = currentSet.get((int)(Math.random() * currentSet.size()));
                
                //make sure current set is populated
                if (tmp != null)
                {
                    if (tmp.getRow() > 0)
                    {
                        //make a passage north
                        tmp.remove(Wall.North);

                        //get Location to the north and make passage south
                        super.getLocation(tmp.getCol(), tmp.getRow() - 1).remove(Wall.South);
                    }
                    
                    //clear the set
                    currentSet.clear();
                }
                
                //if not at the end yet, continue with next cell
                if (getLocationEast(current) != null)
                {
                    current = getLocationEast(current);
                }
                else
                {
                    beginNextRow();
                }
            }
        }
        else
        {
            super.getProgress().setComplete();
        }
    }
    
    private void beginNextRow()
    {
        currentSet.clear();

        //we reached the end move to next row
        currentRow++;

        //start again at first cell in next row
        current = super.getLocation(0, currentRow);

        //increase progress
        super.getProgress().increase();
    }
    
    /**
     * Gets the Location to the east of the current one. If a Location was not found null is returned.
     * @param current
     * @return Location
     */
    private Location getLocationEast(Location current)
    {
        return super.getLocation(current.getCol() + 1, current.getRow());
    }
}