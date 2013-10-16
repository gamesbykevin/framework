package com.gamesbykevin.framework.base;

public class Cell 
{
    //the location of this Cell
    private double col = 0, row = 0;
    
    //the Boundaries of this Cell
    private double minCol = 0, maxCol = 0, minRow = 0, maxRow = 0;
    
    /**
     * Construct a new Cell instance with a column, row value of 0
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
    public Cell(final double col, final double row)
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
        
        //also copy boundaries as well
        setBounds(cell);
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
    public void setBounds(final double minCol, final double maxCol, final double minRow, final double maxRow)
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
     * Does the cell equal the given col, row. <br>
     * Does not check if the boundaries are equal.
     * @param col
     * @param row
     * @return boolean True if the column and row are equal, false otherwise
     */
    public boolean equals(final double col, final double row)
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
    public boolean hasBounds(final double minCol, final double maxCol, final double minRow, final double maxRow)
    {
        return (getCol() >= minCol && getCol() <= maxCol && getRow() >= minRow && getRow() <= maxRow);
    }
    
    /**
     * Set the column based on the column value in Cell
     * @param cell 
     */
    public void setCol(final Cell cell)
    {
        setCol(cell.getCol());
    }
    
    /**
     * Set the column
     * @param col 
     */
    public void setCol(final double col)
    {
        this.col = col;
    }
    
    public double getCol()
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
    
    public void setRow(final double row)
    {
        this.row = row;
    }
    
    public double getRow()
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
    
    public double getMinCol()
    {
        return minCol;
    }
    
    public double getMaxCol()
    {
        return maxCol;
    }
    
    public double getMinRow()
    {
        return minRow;
    }
    
    public double getMaxRow()
    {
        return maxRow;
    }
}