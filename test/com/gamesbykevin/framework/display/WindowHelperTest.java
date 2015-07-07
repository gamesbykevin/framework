package com.gamesbykevin.framework.display;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Window Helper Unit Test
 * @author GOD
 */
public class WindowHelperTest 
{
    private WindowHelper helper;
    
    @BeforeClass
    public static void setUpClass() 
    {
        
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
        
    }
    
    @Test
    public void getWindowsTest() 
    {
        final int cols = 2;
        final int rows = 3;
        final int width = 500;
        final int height = 600;
        final Rectangle container = new Rectangle(0, 0, width, height);
        
        Rectangle[][] windows = WindowHelper.getWindows(container, rows, cols);
        
        assertNotNull(windows);
        
        assertTrue(windows.length > 0);
        assertTrue(windows[0].length > 0);
        assertTrue(windows.length == rows);
        assertTrue(windows[0].length == cols);
        
        final int expectedWidth = (width / cols);
        final int expectedHeight = (height / rows);
        
        int y = container.y;
        
        for (int row = 0; row < windows.length; row++)
        {
            
            int x = container.x;
            
            for (int col = 0; col < windows[0].length; col++)
            {
                assertTrue(windows[row][col].x == x);
                assertTrue(windows[row][col].y == y);
                assertTrue(windows[row][col].width == expectedWidth);
                assertTrue(windows[row][col].height == expectedHeight);
                
                x += expectedWidth;
            }
            
            y += expectedHeight;
        }
        
    }
}