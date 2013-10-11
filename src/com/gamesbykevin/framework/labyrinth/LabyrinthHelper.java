package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.labyrinth.Location.Wall;
import com.gamesbykevin.framework.resources.Progress;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will contain common objects and methods used to generate a Labyrinth
 * @author GOD
 */
public class LabyrinthHelper 
{
    //all of the cells in the labyrinth
    private List<Location> cells;
    
    //the Cells that are the start and finish of the maze
    private Cell start, finish;
    
    //the total number of cols and rows in this labyrinth
    private int cols, rows;
    
    //our progress tracker
    private Progress progress;
    
    //has check() been called yet
    private boolean checked = false;
    
    /**
     * Constructor that creates a maze with the parameter cols/rows
     * All Location(s) inside the maze have 4 walls at the beginning
     * 
     * @param cols Number of columns the maze will have
     * @param rows Number of rows the maze will have
     */
    public LabyrinthHelper(final int cols, final int rows)
    {
        this.cols = cols;
        this.rows = rows;
        
        this.cells = new ArrayList<>();
        
        //add the Location(s) in this order so we can retrieve them easily
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                this.cells.add(new Location(col, row));
            }
        }
    }
    
    public void dispose()
    {
        progress = null;
        
        start = null;
        
        finish = null;
        
        for (Location cell : cells)
        {
            if (cell != null)
                cell.dispose();
            
            cell = null;
        }
        
        cells.clear();
        cells = null;
    }
    
    /**
     * Has the maze been created.
     * 
     * @return boolean
     */
    public boolean isComplete()
    {
        return this.progress.isComplete();
    }
    
    /**
     * Get our progress tracking object
     * @return Progress
     */
    public Progress getProgress()
    {
        return this.progress;
    }
    
    /**
     * Set the goal so we know when the maze is complete
     * @param goal 
     */
    protected void setProgressGoal(final int goal)
    {
        progress = new Progress(goal);
    }
    
    /**
     * Get the total number of Locations within the Maze
     * @return int
     */
    public int getCount()
    {
        return cells.size();
    }
    
    /**
     * Get the total number of rows in the maze
     * @return int
     */
    public int getRowCount()
    {
        return this.rows;
    }
    
    /**
     * Get the total number of columns in the maze
     * @return int
     */
    public int getColumnCount()
    {
        return this.cols;
    }
    
    public List<Location> getLocations()
    {
        return this.cells;
    }
    
    public void setStart(final int col, final int row)
    {
        setStart(new Location(col, row));
    }
    
    private void setStart(final Location start)
    {
        this.start = start;
    }
    
    protected void setFinish(final int col, final int row)
    {
        setFinish(new Location(col, row));
    }
    
    private void setFinish(final Location finish)
    {
        this.finish = finish;
    }
    
    /**
     * Get the Finish Cell indication the Labyrinth has been solved.
     * 
     * @return Cell containing the col, row of the finish
     */
    public Cell getFinish()
    {
        return this.finish;
    }
    
    /**
     * Get the start Location where the maze begins
     * @return Location containing the col, row of the start
     */
    public Cell getStart()
    {
        return this.start;
    }
    
    /**
     * Return true if all Cells have been visited
     * @return 
     */
    protected boolean hasVisitedAll()
    {
        for (Location cell : cells)
        {
            if (!cell.hasVisited())
                return false;
        }
        
        return true;
    }
    
    /**
     * Get the neighbor that is next to the current Location and separated by the wall
     * 
     * @param current The current Location
     * @param wall The wall that separates the current Location from the neighbor
     * @return Location
     */
    protected Location getNeighbor(Location current, Location.Wall wall)
    {
        Location neighbor = null;
        
        switch(wall)
        {
            case South:
                neighbor = new Location((int)current.getCol(), (int)(current.getRow() + 1));
                break;

            case North:
                neighbor = new Location((int)current.getCol(), (int)(current.getRow() - 1));
                break;

            case West:
                neighbor = new Location((int)(current.getCol() - 1), (int)current.getRow());
                break;

            case East:
                neighbor = new Location((int)(current.getCol() + 1), (int)current.getRow());
                break;
        }
        
        //get the Location of the neighbor, if it does not exist null will be returned
        return getLocation(neighbor);
    }
    
    /**
     * Returns true if the Column,Row specified are located inside the maze.
     * 
     * @param col Column
     * @param row Row
     * @return boolean
     */
    protected boolean hasLocation(final double col, final double row)
    {
        return (col >=0 && col < getColumnCount() && row >=0 && row < getRowCount());
    }
    
    /**
     * Returns true if the Cell(Column,Row) specified are located inside the maze.
     * @param cell
     * @return boolean
     */
    protected boolean hasLocation(final Cell cell)
    {
        return hasLocation(cell.getCol(), cell.getRow());
    }
    
    /**
     * Get the Location from the given parameters.
     * If the Location is not found null is returned
     * 
     * @param col Column
     * @param row Row
     * @return Location
     */
    public Location getLocation(final double col, final double row)
    {
        //if the Location is out of bounds return null
        if (!hasLocation(col, row))
            return null;
        
        //all object in ArrayList are in order so we can mathimatically calculate the index
        final int index = (int)((row * cols) + col);
        
        return cells.get(index);
    }
    
    /**
     * Get the Location from the given parameters.
     * If the Location is not found null is returned
     * 
     * @param current
     * @return Location
     */
    protected Location getLocation(final Location current)
    {
        return getLocation(current.getCol(), current.getRow());
    }
    
    /**
     * Get the Location from the given parameters.
     * If the Location is not found null is returned
     * 
     * @param cell
     * @return Location
     */
    protected Location getLocation(final Cell cell)
    {
        return getLocation(cell.getCol(), cell.getRow());
    }
    
    /**
     * Change all Location(s) of a specific group to another
     * @param groupSearch The current group we want to change
     * @param groupChange The group we want it to be
     */
    protected void changeGroup(final long groupSearch, final long groupChange)
    {
        for (Location cell : getLocations())
        {
            //if we found a Location with the group change it accordingly
            if (cell.getGroup() == groupSearch)
                cell.setGroup(groupChange);
        }
    }
    
    /**
     * Get a List of Wall. The logic is to make 
     * sure the neighbor exists and the neighbor 
     * hasn't been visited yet.
     * 
     * @return List<Wall>
     */
    protected List<Wall> getValidWalls(final Location location)
    {
        List<Wall> valid = new ArrayList<>();

        //add valid walls to list as long as they are valid
        for (Wall wall : location.getWalls())
        {
            //if the neighbor exists and we have not visited yet
            if (getNeighbor(location, wall) != null && !getNeighbor(location, wall).hasVisited())
                valid.add(wall);
        }
        
        return valid;
    }
    
    /**
     * Verify the if the appropriate values are set.
     * 
     * @throws Exception 
     */
    protected void check() throws Exception
    {
        //start position must be set
        if (getStart() == null)
            throw new Exception("Start location needs to be set in order to create maze");
        
        checked = true;
    }
    
    /**
     * Has check() been called yet
     * 
     * @return boolean
     */
    protected boolean hasChecked()
    {
        return this.checked;
    }
    
    /**
     * Begin at the Start Location and assign the cost to each Location in the maze
     */
    protected void setCost()
    {
        //begin by marking all Locations as not visited
        for (Location location : getLocations())
        {
            location.setVisited(false);
        }
        
        //current Locations we would like to check
        List<Location> checkList = new ArrayList<>();
        
        //add start location to check List
        addCheckList(checkList, getLocation(getStart()), 0);
        
        //continue until the checkList equals the size of the total Locations in the maze
        while (checkList.size() > 0)
        {
            //get the first element to check
            Location current = getLocation(checkList.get(0));
            
            if (!current.hasWall(Wall.East))
                addCheckList(checkList, getLocation(current.getCol() + 1, current.getRow()), current.getCost());
            
            if (!current.hasWall(Wall.West))
                addCheckList(checkList, getLocation(current.getCol() - 1, current.getRow()), current.getCost());
            
            if (!current.hasWall(Wall.North))
                addCheckList(checkList, getLocation(current.getCol(), current.getRow() - 1), current.getCost());
            
            if (!current.hasWall(Wall.South))
                addCheckList(checkList, getLocation(current.getCol(), current.getRow() + 1), current.getCost());
            
            //now that we have checked the current location remove it from our check List
            checkList.remove(0);
        }
    }
    
    /**
     * Check if the Location has not been visited yet.
     * If no visit then we will mark it as visited and
     * set the cost based on the cost of the current Location.
     * Finally the Location will be added to the check List
     * so we can check for its neighbors later.
     * 
     * @param checkList List of Locations to check to calculate cost
     * @param location New Location we need to check if valid
     * @param currentCost If the new Location is valid, we need to set the cost accordingly
     */
    private void addCheckList(final List<Location> checkList, final Location location, final int currentCost)
    {
        //make sure we haven't visited this neighbor yet
        if (location != null && !getLocation(location).hasVisited())
        {
            //mark new Location as visited and set the cost
            getLocation(location).markVisited();
            
            //since this Location is next to the neighbor the cost will be the current Location cost + 1
            getLocation(location).setCost(currentCost + 1);

            //add to List to check for additional Neighbors
            checkList.add(getLocation(location));
        }
    }
    
    /**
     * Draw a top down 2d maze within the specified screen.
     * NOTE: only to be used for testing purposes
     * 
     * @param graphics Graphics object
     * @param screen Container where we will draw the maze inside
     * @return 
     */
    public Graphics render(final Graphics graphics, final Rectangle screen)
    {
        final int cellW = screen.width  / cols;
        final int cellH = screen.height / rows;
        
        //draw the walls of each cell
        for (Location cell : cells)
        {
            final int drawX = (int)(screen.x + (cell.getCol() * cellW));
            final int drawY = (int)(screen.y + (cell.getRow() * cellH));

            //make the cell itself white
            graphics.setColor(Color.WHITE);
            graphics.fillRect(drawX, drawY, cellW, cellH);
            
            //all the walls will be red
            graphics.setColor(Color.RED);
            
            //draw the walls for the current Location
            for (Wall wall : cell.getWalls())
            {
                switch (wall)
                {
                    case West:
                        graphics.drawLine(drawX, drawY, drawX, drawY + cellH - 1);
                        break;
                        
                    case East:
                        graphics.drawLine(drawX + cellW - 1, drawY, drawX + cellW - 1, drawY + cellH - 1);
                        break;
                        
                    case North:
                        graphics.drawLine(drawX, drawY, drawX + cellW - 1, drawY);
                        break;
                        
                    case South:
                        graphics.drawLine(drawX, drawY + cellH - 1, drawX + cellW, drawY + cellH - 1);
                        break;
                }
            }
        }
        
        return graphics;
    }
    
    /**
     * Draw the progress for the user to see
     * @param graphics
     * @param screen
     * @return Graphics
     */
    public void renderProgress(final Graphics graphics, final Rectangle screen)
    {
        progress.render(graphics, screen);
    }
}