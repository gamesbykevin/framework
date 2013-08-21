package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  Each location will be a Cell within a Labyrinth
 * 
 * @author GOD
 */
public class Location extends Cell
{
    public enum Wall
    {
        North, South, East, West
    }
    
    //List of Walls this Location will have
    private List<Wall> walls;
    
    //has this cell been visited
    private boolean visited = false;
    
    //for Kruskal's algorithm each Location will start out belonging to its own set
    private long group = System.nanoTime();
    
    public Location(final int col, final int row)
    {
        super(col, row);
        
        this.walls = new ArrayList<>();
        
        //each Location will have all 4 walls to start
        for (Wall wall : Wall.values())
        {
            this.walls.add(wall);
        }
    }
    
    //
    /**
     * For Kruskal's algorithm as each Location will begin with its own unique group
     * @return long
     */
    public long getGroup()
    {
        return group;
    }
    
    /**
     * For Kruskal's algorithm as each Location will begin with its own unique group.
     * As the maze is generated Locations are given the same group until all Locations match
     * 
     * @param group 
     */
    public void setGroup(final long group)
    {
        this.group = group;
    }
    
    /**
     * Remove the Wall from the List
     * @param wall 
     */
    public void remove(Wall wall)
    {
        final int index = walls.indexOf(wall);
        
        //if the wall exists
        if (index >= 0)
        {
            //remove it from the List
            walls.remove(index);
        }
    }
    
    /**
     * Get a List of all the walls in this Location
     * @return 
     */
    public List<Wall> getWalls()
    {
        return this.walls;
    }
    
    /**
     * Does this
     * @return 
     */
    public boolean hasWalls()
    {
        return (!walls.isEmpty());
    }
    
    /**
     * Has this Cell been visited
     * @return boolean
     */
    public boolean hasVisited()
    {
        return this.visited;
    }
    
    /**
     * Sets this Cell as visited to true
     */
    public void markVisited()
    {
        this.visited = true;
    }
    
    /**
     * Mark the Cell visited according to the boolean parameter passed
     * @param visited 
     */
    public void setVisited(final boolean visited)
    {
        this.visited = visited;
    }
}