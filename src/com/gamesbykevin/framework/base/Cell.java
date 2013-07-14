package com.gamesbykevin.framework.base;

public class Cell 
{
    private int col = 0, row = 0, minCol = 0, maxCol = 0, minRow = 0, maxRow = 0;
    
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
     * Construct a new Cell instance with a col, row value of 0
     */
    public Cell()
    {
        this(0,0);
    }
    
    /**
     * Construct a new Cell instance setting the
     * col, row and boundaries according to the cell parameter.
     * @param cell 
     */
    public Cell(final Cell cell)
    {
        this(cell.getCol(), cell.getRow());
        this.setBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
    /**
     * Set the boundaries based on the values from the parameter Cell
     * @param cell Cell containing the boundaries we want to set
     */
    public void setBounds(final Cell cell)
    {
        setBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
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
    public boolean equals(final int colTest, final int rowTest)
    {
        return (colTest == col && rowTest == row);
    }
    
    /**
     * Is the cell location within the boundary specified.
     * If the boundary is not specified the minCol, maxCol, minRow, maxRow will all have a value of 0.
     * @return boolean True if the cell col, row is within the boundary
     */
    public boolean hasBounds()
    {
        return hasBounds(minCol, maxCol, minRow, maxRow);
    }
    
    public boolean hasBounds(final Cell cell)
    {
        return hasBounds(cell.getMinCol(), cell.getMaxCol(), cell.getMinRow(), cell.getMaxRow());
    }
    
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
     * Decrease the column by 1 column.
     * Does not alter the boundaries
     * set in this cell.
     */
    public void decreaseCol()
    {
        this.col--;
    }
    
    /**
     * Increase the column by 1 column.
     * Does not alter the boundaries
     * set in this cell.
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
     * Decrease the row by 1 row.
     * Does not alter the boundaries
     * set in this cell.
     */
    public void decreaseRow()
    {
        this.row--;
    }
    
    /**
     * Increase the row by 1 row.
     * Does not alter the boundaries
     * set in this cell.
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