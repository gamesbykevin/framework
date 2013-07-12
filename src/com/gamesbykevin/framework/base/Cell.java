package com.gamesbykevin.framework.base;

public class Cell 
{
    private int col = 0, row = 0, minCol = 0, maxCol = 0, minRow = 0, maxRow = 0;
    
    public Cell(int col, int row)
    {
        this.col = col;
        this.row = row;
    }
    
    public Cell()
    {
        this(0,0);
    }
    
    public Cell(Cell cell)
    {   //set all given cell variables
        this(cell.getCol(), cell.getRow());
        this.setBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
    public void setBounds(Cell cell)
    {
        setBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
    public void setBounds(int minCol, int maxCol, int minRow, int maxRow)
    {
        this.minCol = minCol;
        this.maxCol = maxCol;
        this.minRow = minRow;
        this.maxRow = maxRow;
    }
    
    public boolean equals(Cell cell)
    {
        return equals(cell.getCol(), cell.getRow());
    }
    
    public boolean equals(int col1, int row1)
    {   //we just check if the column and row are equal, ignore boundars check
        return (col1 == col && row1 == row);
    }
    
    public boolean hasBounds()
    {
        return hasBounds(minCol, maxCol, minRow, maxRow);
    }
    
    public boolean hasBounds(Cell cell)
    {   //checks the boundaires with the given cell
        return hasBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
    public boolean hasBounds(int minCol, int maxCol, int minRow, int maxRow)
    {
        return (getCol() >= minCol && getCol() <= maxCol && getRow() >= minRow && getRow() <= maxRow);
    }
    
    public void setCol(Cell cell)
    {
        setCol(cell.getCol());
    }
    
    public void setCol(int col)
    {
        this.col = col;
    }
    
    public int getCol()
    {
        return this.col;
    }
    
    public void decreaseCol()
    {
        this.col--;
    }
    
    public void increaseCol()
    {
        this.col++;
    }
    
    public void setRow(Cell cell)
    {
        setRow(cell.getRow());
    }
    
    public void setRow(int row)
    {
        this.row = row;
    }
    
    public int getRow()
    {
        return this.row;
    }
    
    public void decreaseRow()
    {
        this.row--;
    }
    
    public void increaseRow()
    {
        this.row++;
    }
    
    public int getMinCol()
    {
        return minCol;
    }
    
    public int getMaxCol()
    {
        return maxCol;
    }
    
    public int getMinRow()
    {
        return minRow;
    }
    
    public int getMaxRow()
    {
        return maxRow;
    }
}