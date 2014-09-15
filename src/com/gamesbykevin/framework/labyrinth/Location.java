package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 *  Each Cell inside a Labyrinth is a Location
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
    
    //for different algorithms Location(s) may belong to a group
    private long group = System.nanoTime();
    
    //the cost will be used to calculate the minimum number of moves to get to the start Location
    private int cost;
    
    public Location(Location location)
    {
        this(location.getCol(), location.getRow());
        
        //copy data from instance location
        setCost(location.getCost());
        setGroup(location.getGroup());
        setVisited(location.hasVisited());
        
        //remove any existing walls
        walls.clear();
        
        //copy from instance location
        for (Wall wall : location.getWalls())
        {
            add(wall);
        }
    }
    
    /**
     * Create a new Location with the specified Location.
     * 
     * All new Locations will have all 4 walls added by default
     * @param col
     * @param row 
     */
    public Location(final double col, final double row)
    {
        super(col, row);
        
        this.walls = new ArrayList<>();
        
        //each Location will have all 4 walls to start
        for (Wall wall : Wall.values())
        {
            add(wall);
        }
    }
    
    /**
     * Free up resources
     */
    public void dispose()
    {
        if (walls != null)
            walls.clear();
        
        walls = null;
    }
    
    /**
     * Set the minimum amount of moves to reach the starting point
     * @param cost 
     */
    public void setCost(final int cost)
    {
        this.cost = cost;
    }
    
    /**
     * Get the minimum number of moves to reach the starting point
     * @return int
     */
    public int getCost()
    {
        return this.cost;
    }
    
    //
    /**
     * For certain algorithms each Location will have an assigned group
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
     * For Kruskal's algorithm as each Location will begin with its own unique group.
     * @param location Location that contains the group we want to set
     */
    public void setGroup(final Location location)
    {
        setGroup(location.getGroup());
    }
    
    /**
     * Remove the Wall from the List
     * @param wall 
     */
    public void remove(final Wall wall)
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
     * Add the Wall to the List.<br><br>
     * If the wall already exists it will not be added.
     * @param wall Wall to add (example: North, South, West, East)
     */
    public void add(final Wall wall)
    {
        final int index = walls.indexOf(wall);
        
        //if the wall exists
        if (index < 0)
        {
            //add it from the List
            walls.add(wall);
        }
    }
    
    /**
     * Get a List of all the walls in this Location
     * @return List<Wall>
     */
    public List<Wall> getWalls()
    {
        return this.walls;
    }
    
    /**
     * Does this Location have walls
     * @return boolean
     */
    public boolean hasWalls()
    {
        return (!walls.isEmpty());
    }
    
    /**
     * Does this Location have the specified wall
     * @param wall The wall we want to see if exists
     * @return boolean true if this location has the wall, false otherwise
     */
    public boolean hasWall(Wall wall)
    {
        for (int i = 0; i < walls.size(); i++)
        {
            if (walls.get(i) == wall)
                return true;
        }
        
        return false;
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