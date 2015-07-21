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
     * Get the distance between the locations
     * @param goal The location we want
     * @param current The current location we are at
     * @return The distance between the two locations
     */
    public static double getDistance(final Cell goal, final Cell current)
    {
        return getDistance(goal.getCol(), goal.getRow(), current.getCol(), current.getRow());
    }
    
    /**
     * Get the distance between the locations
     * @param column Column 
     * @param row Row 
     * @param cell Cell containing (column, row)
     * @return The distance between the two locations
     */
    public static double getDistance(final double column, final double row, final Cell cell)
    {
        return getDistance(column, row, cell.getCol(), cell.getRow());
    }
    
    /**
     * Get the distance between the 2 locations (column1, row1) and (column2, row2)
     * @param column1 Column 1
     * @param row1 Row 1
     * @param column2 Column 2 
     * @param row2 Row 2
     * @return The distance between the two locations
     */
    public static double getDistance(final double column1, final double row1, final double column2, final double row2)
    {
        return Math.sqrt(Math.pow(column1 - column2, 2) + Math.pow(row1 - row2, 2));
    }
    
    /**
     * Set the boundary based on the values from the parameter Cell
     * 
     * @param cell
     */
    public final void setBounds(final Cell cell)
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
    public final void setBounds(final double minCol, final double maxCol, final double minRow, final double maxRow)
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
     * Is the cell located within the boundary specified?
     * @return boolean true if so, false otherwise
     */
    public boolean hasBounds()
    {
        return hasBounds(getMinCol(), getMaxCol(), getMinRow(), getMaxRow());
    }
    
    /**
     * Is this Cell within the specified min/max boundary?
     * 
     * @param minCol Minimum value allowed for the Column
     * @param maxCol Maximum value allowed for the Column
     * @param minRow Minimum value allowed for the Row
     * @param maxRow Maximum value allowed for the Row
     * @return boolean true if within boundary specified, false otherwise
     */
    public boolean hasBounds(final double minCol, final double maxCol, final double minRow, final double maxRow)
    {
        return (getCol() >= minCol && getCol() <= maxCol && getRow() >= minRow && getRow() <= maxRow);
    }
    
    /**
     * Is the specified location within the cell min/max boundaries
     * @param cell The location
     * @return true if within the boundaries, false otherwise
     */
    public boolean hasBounds(final Cell cell)
    {
        return hasBounds(cell.getCol(), cell.getRow());
    }
    
    /**
     * Is the specified col,row location within the boundary set in this Cell?
     * @param col Column
     * @param row Row
     * @return true if the (column, row) is within the assigned (min,max) values in this object, false otherwise
     */
    public boolean hasBounds(final double col, final double row)
    {
        //if the column is less than the minimum, return false
        if (col < getMinCol())
            return false;
        
        //if the column is greater than the maximum, return false
        if (col > getMaxCol())
            return false;
        
        //if the row is less than the minimum, return false
        if (row < getMinRow())
            return false;
        
        //if the row is greater than the maximum, return false
        if (row > getMaxRow())
            return false;
        
        //we are in bounds, return true
        return true;
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