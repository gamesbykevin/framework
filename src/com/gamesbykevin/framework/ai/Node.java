package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.labyrinth.Location;

/**
 * The Node is part of the path to the destination.
 * @author GOD
 */
public class Node extends Cell
{
    //each node is to be unique
    private final long id = System.nanoTime();
    
    //the parent node of the current node
    private long parent;
    
    //the current cost to reach this node
    private int cost = 0;
    
    public Node(final double col, final double row)
    {
        super(col, row);
    }
    
    public Node(final Cell cell)
    {
        this(cell.getCol(), cell.getRow());
    }
    
    public Node(final Location location)
    {
        this(location.getCol(), location.getRow());
    }
    
    public int getCost()
    {
        return this.cost;
    }
    
    public void setCost(final int cost)
    {
        this.cost = cost;
    }
    
    private void setParent(final long parent)
    {
        this.parent = parent;
    }
    
    public void setParent(final Node parent)
    {
        setParent(parent.getId());
    }
    
    public long getParent()
    {
        return this.parent;
    }
    
    public long getId()
    {
        return this.id;
    }
}