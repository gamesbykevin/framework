package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.base.Cell;

public class Node extends Cell
{
    private Node parent;
    private double goalScore = 0;
    private double startScore = 0;
    
    public Node(Node node)
    {
        super(node.getLocationX(), node.getLocationY());
        setParent(node.getParent());
        super.setBounds(node.getMinCol(), node.getMaxCol(), node.getMinRow(), node.getMaxRow());
    }
    
    public Node(Cell location, Node parent)
    {
        this(location.getCol(), location.getRow(), parent);
    }
    
    public Node(int locationX, int locationY, Node parent)
    {
        super(locationX, locationY);
        setParent(parent);
    }
    
    public void dispose()
    {
        parent = null;
    }
    
    public void setLocationX(Cell cell)
    {
        setLocationX(cell.getCol());
    }
    
    public void setLocationX(int locationX)
    {
        setCol(locationX);
    }
    
    public void setLocationY(Cell cell)
    {
        setLocationY(cell.getRow());
    }
    
    public void setLocationY(int locationY)
    {
        setRow(locationY);
    }
    
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
    
    public int getLocationX()
    {
        return getCol();
    }
    
    public int getLocationY()
    {
        return getRow();
    }
    
    public Node getParent()
    {
        return parent;
    }
    
    public double getTotalScore()
    {
        return goalScore + startScore;
    }
    
    public double getGoalScore()
    {
        return goalScore;
    }
    
    public double getStartScore()
    {
        return startScore;
    }
    
    public void setGoalScore(double score)
    {
        this.goalScore = score;
    }
    
    public void setStartScore(double score)
    {
        this.startScore = score;
    }
    
    public boolean equals(Node node)
    {   
        return (node.getLocationX() == getLocationX() && node.getLocationY() == getLocationY());
    }
    
    public boolean hasBounds(Cell cell)
    {
        return hasBounds(cell);
    }
}