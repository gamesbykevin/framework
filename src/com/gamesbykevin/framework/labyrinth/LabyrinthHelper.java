/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.East;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.North;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.South;
import static com.gamesbykevin.framework.labyrinth.Location.Wall.West;
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
    private Location start, finish;
    
    //the total number of cols and rows in this labyrinth
    private int cols, rows;
    
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
        
        for (int col = 0; col < cols; col++)
        {
            for (int row = 0; row < rows; row++)
            {
                this.cells.add(new Location(col, row));
            }
        }
    }
    
    public List<Location> getCells()
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
    
    public void setFinish(final int col, final int row)
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
     * @return Location containing the col, row of the finish
     */
    public Location getFinish()
    {
        return this.finish;
    }
    
    /**
     * Get the start Location where the maze begins
     * @return Location containing the col, row of the start
     */
    public Location getStart()
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
                neighbor = new Location(current.getCol(), current.getRow() + 1);
                break;

            case North:
                neighbor = new Location(current.getCol(), current.getRow() - 1);
                break;

            case West:
                neighbor = new Location(current.getCol() - 1, current.getRow());
                break;

            case East:
                neighbor = new Location(current.getCol() + 1, current.getRow());
                break;
        }
        
        //get the Location of the neighbor, if it does not exist null will be returned
        return getLocation(neighbor);
    }
    
    protected Location getLocation(final Location current)
    {
        for (Location cell : cells)
        {
            if (cell.equals(current))
                return cell;
        }
        
        return null;
    }
    
    /**
     * For now this create method only checks if the appropriate values are set
     * @throws Exception 
     */
    protected void create() throws Exception
    {
        //start position must be set
        if (getStart() == null)
            throw new Exception("Start location needs to be set in order to create maze");
        
        //finish position must be set
        if (getFinish() == null)
            throw new Exception("Finish location needs to be set in order to create maze");
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
            final int drawX = screen.x + (cell.getCol() * cellW);
            final int drawY = screen.y + (cell.getRow() * cellH);

            //make the cell itself white
            graphics.setColor(Color.WHITE);
            graphics.fillRect(drawX, drawY, cellW, cellH);
            
            //all the walls will be red
            graphics.setColor(Color.RED);
            
            //draw the walls for the current Location
            for (Location.Wall wall : cell.getWalls())
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
}