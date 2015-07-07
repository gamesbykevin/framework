package com.gamesbykevin.framework.maze;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.base.Sprite;
import com.gamesbykevin.framework.maze.Room.Wall;
import com.gamesbykevin.framework.resources.Progress;

import java.awt.Graphics;
import java.util.Random;

/**
 * The parent Maze class
 * @author GOD
 */
public abstract class Maze extends Sprite implements IMaze
{
    /**
     * The number of columns in this maze
     */
    private final int cols;
    
    /**
     * The number of rows in this maze
     */
    private final int rows;
    
    /**
     * The rooms that make up the maze
     */
    private Room[][] rooms;
    
    //the start and finish locations
    private Cell start, finish;
    
    //do we display the maze creation progress
    private boolean display = true;
    
    /**
     * Our progress object so we can display to the user when rendering is complete
     */
    private Progress progress;
    
    protected static final int DEFAULT_MAZE_DIMENSION = 10;
    
    /**
     * Create a new maze of specified size
     * @param cols Total columns
     * @param rows Total rows
     */
    protected Maze(final int cols, final int rows)
    {
        this.cols = cols;
        this.rows = rows;
        
        //create a new array of rooms 
        this.rooms = new Room[rows][cols];
        
        //now create our rooms
        createRooms();
        
        //create a new progress object
        this.progress = new Progress(rows * cols);
        
        //create the start/finish locations
        this.start = new Cell();
        this.finish = new Cell();
    }
    
    /**
     * Get the object representing the progress
     * @return Object that tracks when the maze is complete
     */
    public Progress getProgress()
    {
        return this.progress;
    }
    
    /**
     * Has the maze been generated?
     * @return true if the entire maze has been generated, false otherwise
     */
    public boolean isGenerated()
    {
        return getProgress().isComplete();
    }
    
    /**
     * Create a room for every (column, row) in our maze
     */
    private void createRooms()
    {
        for (int row = 0; row < getRows(); row++)
        {
            for (int col = 0; col < getCols(); col++)
            {
                this.rooms[row][col] = new Room(col, row);
            }
        }
    }
    
    /**
     * Get the start column
     * @return The start column
     */
    public int getStartCol()
    {
        return (int)start.getCol();
    }
    
    /**
     * Get the start row
     * @return The start row
     */
    public int getStartRow()
    {
        return (int)start.getRow();
    }
    
    /**
     * Assign the starting location
     * @param col The start column
     * @param row The start row
     */
    public void setStartLocation(final int col, final int row)
    {
        this.start.setCol(col);
        this.start.setRow(row);
    }
    
    /**
     * Get the finish column
     * @return The finish column
     */
    public int getFinishCol()
    {
        return (int)finish.getCol();
    }
    
    /**
     * Get the finish row
     * @return The finish row
     */
    public int getFinishRow()
    {
        return (int)finish.getRow();
    }
    
    /**
     * Assign the finish location
     * @param col The finish column
     * @param row The finish row
     */
    public void setFinishLocation(final int col, final int row)
    {
        this.finish.setCol(col);
        this.finish.setRow(row);
    }
    
    /**
     * Fill each room with 4 walls
     */
    protected void populateRooms()
    {
        for (int row = 0; row < getRows(); row++)
        {
            for (int col = 0; col < getCols(); col++)
            {
                final Room room = getRoom(col, row);
                
                //make sure the room exists
                if (room != null)
                    getRoom(col, row).addAllWalls();
            }
        }
    }
    
    /**
     * Is this location within the bounds of this maze?
     * @param col Column
     * @param row Row
     * @return true = yes, false = no
     */
    @Override
    public boolean hasBounds(final int col, final int row)
    {
        return (col >= 0 && col < getCols() && row >= 0 && row < getRows());
    }
    
    @Override
    public void dispose()
    {
        if (getRooms() != null)
        {
            for (int row = 0; row < getRows(); row++)
            {
                for (int col = 0; col < getCols(); col++)
                {
                    this.rooms[row][col].dispose();
                    this.rooms[row][col] = null;
                }
            }
            
            this.rooms = null;
        }
    }
    
    /**
     * Get the room at the specified location
     * @param col Column
     * @param row Row
     * @return The room at the specified location, if the location is out of bounds, null is returned
     */
    public Room getRoom(final int col, final int row)
    {
        //if out of bounds return null
        if (!hasBounds(col, row))
            return null;
        
        return getRooms()[row][col];
    }
    
    /**
     * Get rooms
     * @return The array of rooms that make up the maze
     */
    protected Room[][] getRooms()
    {
        return this.rooms;
    }
    
    /**
     * Get the columns
     * @return The total number of columns in this maze
     */
    public int getCols()
    {
        return this.cols;
    }
    
    /**
     * Get the rows
     * @return The total number of rows in this maze
     */
    public int getRows()
    {
        return this.rows;
    }
    
    
    /**
     * Update the progress of our maze creation.<br>
     * Here we track the progress by the number of visited rooms.
     */
    protected void updateProgress()
    {
        int count = 0;
        
        //check all rooms
        for (int row = 0; row < getRows(); row++)
        {
            for (int col = 0; col < getCols(); col++)
            {
                //keep track of number of visited rooms
                if (getRoom(col, row).hasVisited())
                    count++;
            }
        }
        
        //update the progress
        getProgress().setCount(count);
    }
    
    /**
     * Set the progress bar to display while the maze has not yet been created.<br>
     * If display is false, we will see the maze the entire time it is created.<br>
     * If display is true, we will draw a progress bar so we know when complete
     * @param display true=yes, false=no
     */
    public void setDisplayProgress(final boolean display)
    {
        this.display = display;
    }
    
    /**
     * Each child maze needs to have logic to generate
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    @Override
    public abstract void update(final Random random) throws Exception;
    
    /**
     * Render the maze generation progress.<br>
     * Once the maze has been generated a generic 2d representation will be drawn
     * @param graphics 
     */
    @Override
    public void render(final Graphics graphics)
    {
        //if the maze has not genrated and we want to display the progress
        if (!isGenerated() && display)
        {
            getProgress().render(graphics);
        }
        else
        {
            graphics.drawRect((int)getX(), (int)getY(), (int)(getCols() * getWidth()), (int)(getRows() * getHeight()));
            
            for (int row = 0; row < getRows(); row++)
            {
                int y = (int)(getY() + (row * getHeight()));

                for (int col = 0; col < getCols(); col++)
                {
                    int x = (int)(getX() + (col * getWidth()));

                    final Room room = getRoom(col, row);

                    if (room.hasWall(Wall.East))
                        graphics.drawLine(x + (int)getWidth(), y, x + (int)getWidth(), y + (int)getHeight());
                    if (room.hasWall(Wall.West))
                        graphics.drawLine(x, y, x, y + (int)getHeight());
                    if (room.hasWall(Wall.North))
                        graphics.drawLine(x, y, x + (int)getWidth(), y);
                    if (room.hasWall(Wall.South))
                        graphics.drawLine(x, y + (int)getHeight(), x + (int)getWidth(), y + (int)getHeight());
                }
            }
        }
    }
}