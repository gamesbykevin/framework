package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.labyrinth.Location.Wall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Labyrinth 
{
    //all of the cells in the labyrinth
    private List<Location> cells;
    
    //the Cells that are the start and finish of the maze
    private Location start, finish;
    
    //the total number of cols and rows in this labyrinth
    private int cols, rows;
    
    public enum Algorithm
    {
        DepthFirstSearch, Prims
    }
    
    public Labyrinth(final int cols, final int rows)
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
    
    public void setStart(final int col, final int row)
    {
        setStart(new Location(col, row));
    }
    
    public void setStart(final Location start)
    {
        this.start = start;
    }
    
    public void setFinish(final int col, final int row)
    {
        setFinish(new Location(col, row));
    }
    
    public void setFinish(final Location finish)
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
     * Return true if all Cells have been visited
     * @return 
     */
    private boolean hasVisitedAll()
    {
        for (Location cell : cells)
        {
            if (!cell.hasVisited())
                return false;
        }
        
        return true;
    }
    
    private Location getLocation(final Location current)
    {
        for (Location cell : cells)
        {
            if (cell.equals(current))
                return cell;
        }
        
        return null;
    }
    
    /**
     * Get a List of all neighbor Cells to the current
     * @param current
     * @return List<Cell>
     */
    private List<Location> getNeighbors(final Location current)
    {
        //List of neighbors found next to the test Cell
        List<Location> neighbors = new ArrayList<>();
        
        //check cell to the east
        Location east = new Location(current.getCol() + 1, current.getRow());
        if (getLocation(east) != null && !getLocation(east).hasVisited())
            neighbors.add(getLocation(east));
        
        //check cell to the west
        Location west = new Location(current.getCol() - 1, current.getRow());
        if (getLocation(west) != null && !getLocation(west).hasVisited())
            neighbors.add(getLocation(west));
        
        //check cell to the north
        Location north = new Location(current.getCol(), current.getRow() - 1);
        if (getLocation(north) != null && !getLocation(north).hasVisited())
            neighbors.add(getLocation(north));
        
        //check cell to the south
        Location south = new Location(current.getCol(), current.getRow() + 1);
        if (getLocation(south) != null && !getLocation(south).hasVisited())
            neighbors.add(getLocation(south));
        
        return neighbors;
    }
    
    private Location getNeighbor(Location current, Wall wall)
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
    
    /**
     * Create the Maze. 
     * 
     * @throws Exception If the start Location is not set
     */
    public void create(Algorithm algorithm) throws Exception
    {
        //start position must be set
        if (start == null)
            throw new Exception("Start location needs to be set in order to create maze");
        
        //finish position must be set
        if (finish == null)
            throw new Exception("Finish location needs to be set in order to create maze");
        
        Location current;
        
        switch(algorithm)
        {
            case Prims:
                //our starting position
                current = getLocation(start);

                //set Location as part of maze
                current.markVisited();

                List<Location> checkWalls = new ArrayList<>();

                while(!hasVisitedAll())
                {
                    List<Wall> valid = new ArrayList<>();

                    //add valid walls to list
                    for (Wall wall : current.getWalls())
                    {
                        if (getNeighbor(current, wall) != null && !getNeighbor(current, wall).hasVisited())
                            valid.add(wall);
                    }

                    if (valid.size() > 0)
                    {
                        //while we have valid walls to check
                        while (valid.size() > 0)
                        {
                            final int index = (int)(Math.random() * valid.size());

                            Wall wall = valid.get(index);

                            switch(wall)
                            {
                               case South:
                                   getNeighbor(current, wall).remove(Wall.North);
                                   break;

                               case North:
                                   getNeighbor(current, wall).remove(Wall.South);
                                   break;

                               case West:
                                   getNeighbor(current, wall).remove(Wall.East);
                                   break;

                               case East:
                                   getNeighbor(current, wall).remove(Wall.West);
                                   break;
                            }

                            valid.remove(index);

                            //mark neighbor as visited
                            getNeighbor(current, wall).markVisited();

                            //remove wall to create passage to neighbor
                            current.remove(wall);

                            //add neighbor as another spot to check
                            checkWalls.add(getNeighbor(current, wall));
                        }
                    }
                    else
                    {
                        current = checkWalls.get((int)(Math.random() * checkWalls.size()));
                    }
                }
                break;                
                
            case DepthFirstSearch:
                //our starting position
                current = getLocation(start);

                //mark the starting location as visited
                current.markVisited();

                List<Location> stack = new ArrayList<>();

                //loop until every cell has been visited
                while (!hasVisitedAll())
                {
                    //get a List of neighbors to the current Cell that have not been visited
                    List<Location> neighbors = getNeighbors(current);

                    //if neighbors exist
                    if (neighbors.size() > 0)
                    {
                        //add the current Location to the stack
                        stack.add(current);

                        //get random neighbor from the list and make it the current location
                        Location destination = neighbors.get((int)(Math.random() * neighbors.size()));

                        //mark the new destination as visited
                        destination.markVisited();

                        //if the columns are the same then the rows have to be different
                        if (destination.getCol() == current.getCol())
                        {
                            if (destination.getRow() > current.getRow())
                            {
                                destination.remove(Wall.North);
                                current.remove(Wall.South);
                            }
                            else
                            {
                                destination.remove(Wall.South);
                                current.remove(Wall.North);
                            }
                        }
                        else
                        {
                            if (destination.getCol() > current.getCol())
                            {
                                destination.remove(Wall.West);
                                current.remove(Wall.East);
                            }
                            else
                            {
                                destination.remove(Wall.East);
                                current.remove(Wall.West);
                            }
                        }

                        //now the destiantion is the current Location
                        current = destination;
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
                break;
        }
    }
    
    /**
     * Of all the Location(s) in the maze get a random one that has not yet been visited
     * @return Location
     */
    private Location getRandomUnvisited()
    {
        List<Location> unvisited = new ArrayList<>();
        
        for (Location cell : cells)
        {
            if (!cell.hasVisited())
                unvisited.add(cell);
        }
        
        return unvisited.get((int)(Math.random() * unvisited.size()));
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
}