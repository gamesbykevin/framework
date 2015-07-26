package com.gamesbykevin.framework.ai;

import java.util.UUID;

/**
 * A search node to assist in our pathfinding
 * @author GOD
 */
public final class Node 
{
    /**
     * The unique id of this node
     */
    private final UUID id;
    
    /**
     * The unique id of the parent
     */
    private UUID parentId;
    
    /**
     * The column location of this node
     */
    private final int column;
    
    /**
     * The row location of this node
     */
    private final int row;
    
    /**
     * Cost from this location to the goal
     */
    private int heuristic;
    
    /**
     * Movement cost to the next cell
     */
    private int movement;
    
    /**
     * Create a Node that will help us in pathfinding
     */
    protected Node(final int column, final int row, final Node parent, final int movement, final int heuristic)
    {
        //create a random id
        this.id = UUID.randomUUID();
        
        //assign the location
        this.column = column;
        this.row = row;
        
        //assign the cost
        setHeuristic(heuristic);
        setMovement(movement);
        
        //assign the parent id
        setParentId(parent);
    }
    
    /**
     * Assign the movement cost
     * @param movement The cost to move to the next cell
     */
    protected void setMovement(final int movement)
    {
        this.movement = movement;
    }
    
    /**
     * Get the movement cost
     * @return The cost to move to the next cell
     */
    protected int getMovement()
    {
        return this.movement;
    }
    
    /**
     * Assign the heuristic cost
     * @param heuristic The cost to move from the current location to the goal
     */
    protected void setHeuristic(final int heuristic)
    {
        this.heuristic = heuristic;
    }
    
    /**
     * Get the heuristic cost
     * @return The cost to move from the current location to the goal
     */
    protected int getHeuristic()
    {
        return this.heuristic;
    }
    
    /**
     * Get the total cost.<br>
     * The movement cost + heuristic cost
     * @return The calculated cost of the node
     */
    protected int getCost()
    {
        return (getMovement() + getHeuristic());
    }
    
    /**
     * Do we have this location?
     * @param node The node containing the (column, row) we want to check
     * @return true if both nodes have the same (column, row), otherwise false
     */
    protected boolean hasLocation(final Node node)
    {
        return hasLocation(node.getColumn(), node.getRow());
    }
    
    /**
     * Do we have this location?
     * @param column Column
     * @param row Row
     * @return true if this node has the same (column, row), otherwise false
     */
    protected boolean hasLocation(final int column, final int row)
    {
        return (getColumn() == column && getRow() == row);
    }
    
    /**
     * Get the column
     * @return The column location of this node
     */
    protected int getColumn()
    {
        return this.column;
    }
    
    /**
     * Get the row
     * @return The row location of this node
     */
    protected int getRow()
    {
        return this.row;
    }
    
    /**
     * Get the id
     * @return The unique if of this node
     */
    protected UUID getId()
    {
        return this.id;
    }
    
    /**
     * Does the id of the specified node match this node
     * @param node The node containing the id we want to check
     * @return true if the id's match, false otherwise
     */
    protected boolean hasId(final Node node)
    {
        return hasId(node.getId());
    }
    
    /**
     * Does this node have the specified id
     * @param id The id we want to check
     * @return true if the id's match, false otherwise
     */
    protected boolean hasId(final UUID id)
    {
        return (getId().equals(id));
    }
    
    /**
     * Get the parent id
     * @return The id of the parent
     */
    protected UUID getParentId()
    {
        return this.parentId;
    }
    
    /**
     * Assign the parent id
     * @param parent The parent node containing the id
     */
    protected void setParentId(final Node parent)
    {
        if (parent != null)
            this.parentId = parent.getId();
    }
}