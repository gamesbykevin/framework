package com.gamesbykevin.framework.base;

public class Cell 
{
    //the location of this Cell
    private int col = 0, row = 0;
    
    //the Boundaries of this Cell
    private int minCol = 0, maxCol = 0, minRow = 0, maxRow = 0;
    
    /**
     * Construct a new Cell instance with a col, row value of 0
     */
    public Cell()
    {
        this(0,0);
    }
    
    /**
     * Construct a new Cell instance with the given 
     * col, row parameters.
     * @param col
     * @param row 
     */
    public Cell(final int col, final int row)
    {
        this.col = col;
        this.row = row;
    }
    
    /**
     * Construct a new Cell instance using the parameter Cell
     * to assign this new Cell's col, row
     * 
     * @param cell 
     */
    public Cell(final Cell cell)
    {
        this(cell.getCol(), cell.getRow());
    }
    
    /**
     * Set the boundary based on the values from the parameter Cell
     * 
     * @param cell
     */
    public void setBounds(final Cell cell)
    {
        setBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
    /**
     * Set the boundary with the given parameters
     * 
     * @param minCol The minimum column
     * @param maxCol The maximum column
     * @param minRow The minimum row
     * @param maxRow The maximum row
     */
    public void setBounds(final int minCol, final int maxCol, final int minRow, final int maxRow)
    {
        this.minCol = minCol;
        this.maxCol = maxCol;
        this.minRow = minRow;
        this.maxRow = maxRow;
    }
    
    /**
     * Does the cell equal the given col, row. 
     * Does not check if the boundaries are equal.
     * @param cell
     * @return boolean
     */
    public boolean equals(final Cell cell)
    {
        return equals(cell.getCol(), cell.getRow());
    }
    
    /**
     * Does the cell equal the given col, row. 
     * Does not check if the boundaries are equal.
     * @param colTest
     * @param rowTest
     * @return boolean
     */
    public boolean equals(final int col, final int row)
    {
        return (col == this.col && row == this.row);
    }
    
    /**
     * Is the cell location within the boundary specified.
     * @return boolean
     */
    public boolean hasBounds()
    {
        return hasBounds(minCol, maxCol, minRow, maxRow);
    }
    
    /**
     * Is the Cell within the current boundary set.
     * 
     * @param minCol Minimum value allowed for the Column
     * @param maxCol Maximum value allowed for the Column
     * @param minRow Minimum value allowed for the Row
     * @param maxRow Maximum value allowed for the Row
     * @return boolean
     */
    public boolean hasBounds(final int minCol, final int maxCol, final int minRow, final int maxRow)
    {
        return (getCol() >= minCol && getCol() <= maxCol && getRow() >= minRow && getRow() <= maxRow);
    }
    
    public void setCol(final Cell cell)
    {
        setCol(cell.getCol());
    }
    
    public void setCol(final int col)
    {
        this.col = col;
    }
    
    public int getCol()
    {
        return this.col;
    }
    
    /**
     * Decrease the column by 1
     */
    public void decreaseCol()
    {
        this.col--;
    }
    
    /**
     * Increase the column by 1
     */
    public void increaseCol()
    {
        this.col++;
    }
    
    public void setRow(final Cell cell)
    {
        setRow(cell.getRow());
    }
    
    public void setRow(final int row)
    {
        this.row = row;
    }
    
    public int getRow()
    {
        return this.row;
    }
    
    /**
     * Decrease the row by 1
     */
    public void decreaseRow()
    {
        this.row--;
    }
    
    /**
     * Increase the row by 1
     */
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