package com.gamesbykevin.framework.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Cell
 * @author GOD
 */
public class CellTest 
{
    private Cell cell;
    
    @BeforeClass
    public static void setUpClass() 
    {
        //create a new cell
        Cell cell = new Cell();
        
        //assume the default location
        assertTrue(cell.getCol() == 0);
        
        //assume the default location
        assertTrue(cell.getRow() == 0);
        
        final double col = 11.1;
        final double row = 5.7;
        cell = new Cell(col, row);
        
        //assume true
        assertTrue(cell.getCol() == col);
        
        //assume true
        assertTrue(cell.getRow() == row);
        
        final int col1 = 1;
        final int row1 = 5;
        cell = new Cell(col1, row1);
        
        //assume true
        assertTrue(cell.getCol() == col1);
        
        //assume true
        assertTrue(cell.getRow() == row1);
        
        
        Cell tmp = new Cell(cell);
                
        //assume true
        assertTrue(tmp.getCol() == col1);
        
        //assume true
        assertTrue(tmp.getRow() == row1);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() 
    {
        
    }
    
    @After
    public void tearDown() 
    {
        cell = null;
    }
    
    @Test
    public void getColTest() 
    {
        final double col = 73;
        cell = new Cell(col, 0);
        assertTrue(cell.getCol() == col);
    }
    
    @Test
    public void getRowTest() 
    {
        final double row = 11;
        cell = new Cell(0, row);
        assertTrue(cell.getRow() == row);
    }
    
    @Test
    public void getDistanceTest() 
    {
        final int col1 = 10;
        final int row1 = 5;
        
        cell = new Cell(col1, row1);
        
        final int col2 = 5;
        final int row2 = 3;
        
        Cell tmp = new Cell(col2, row2);
        
        //calculate the distance
        final double distance = Math.sqrt(Math.pow(cell.getCol() - tmp.getCol(), 2) + Math.pow(cell.getRow() - tmp.getRow(), 2));
        
        //assume true
        assertTrue(Cell.getDistance(cell, tmp) == distance);
        
        //assume true
        assertTrue(Cell.getDistance(cell.getCol(), cell.getRow(), tmp.getCol(), tmp.getRow()) == distance);
    }
    
    @Test
    public void setBoundsTest() 
    {
        cell = new Cell();
        assertTrue(cell.hasBounds());
        assertFalse(cell.hasBounds(10, 88));
        
        final int minCol = 0, maxCol = 10;
        final int minRow = 0, maxRow = 3;
        Cell tmp = new Cell();
        tmp.setBounds(minCol, maxCol, minRow, maxRow);
        
        //assert true
        assertTrue(tmp.getMinCol() == minCol);
        assertTrue(tmp.getMaxCol() == maxCol);
        assertTrue(tmp.getMinRow() == minRow);
        assertTrue(tmp.getMaxRow() == maxRow);
        
        cell.setCol(7);
        cell.setRow(1);
        assertTrue(tmp.hasBounds(cell));
        
        cell.setCol(11);
        cell.setRow(2);
        assertFalse(tmp.hasBounds(cell));
    }
    
    @Test
    public void decreaseColTest() 
    {
        cell = new Cell(5, 5);
        
        final double col = cell.getCol();
        
        assertFalse(cell.getCol() < col);
        
        cell.decreaseCol();
        
        assertTrue(cell.getCol() < col);
    }
    
    @Test
    public void increaseColTest() 
    {
        cell = new Cell(5, 5);
        
        final double col = cell.getCol();
        
        assertFalse(cell.getCol() > col);
        
        cell.increaseCol();
        
        assertTrue(cell.getCol() > col);
    }
    
    @Test
    public void decreaseRowTest() 
    {
        cell = new Cell(5, 1.1);
        
        final double row = cell.getRow();
        
        assertFalse(cell.getRow() < row);
        
        cell.decreaseRow();
        
        assertTrue(cell.getRow() < row);
    }
    
    @Test
    public void increaseRowTest() 
    {
        cell = new Cell(5, .5);
        
        final double row = cell.getRow();
        
        assertFalse(cell.getRow() > row);
        
        cell.increaseRow();
        
        assertTrue(cell.getRow() > row);
    }
}