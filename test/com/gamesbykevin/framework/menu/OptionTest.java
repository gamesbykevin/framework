package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.awt.CustomImageTest;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Option unit test
 * @author GOD
 */
public class OptionTest 
{
    private Option option;
    
    private static final String TITLE_1 = "TestTitle1";
    
    @BeforeClass
    public static void setUpClass() 
    {
        Option option = new Option(TITLE_1);
        
        for (MenuTest.LayerKey key : MenuTest.LayerKey.values())
        {
            option = new Option(key);
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Option option = new Option(TITLE_1);
        option.dispose();
        option = null;
    }
    
    @Before
    public void setUp() 
    {
        option = new Option(TITLE_1);
    }
    
    @After
    public void tearDown() 
    {
        option.dispose();
        option = null;
    }
    
    @Test
    public void disposeTest() 
    {
        option.dispose();
    }
    
    @Test
    public void setHighlightedTest()
    {
        option.setHighlighted(true);
        option.setHighlighted(false);
    }
    
    @Test
    public void hasHighlightTest()
    {
        assertFalse(option.hasHighlight());
        option.setHighlighted(true);
        assertTrue(option.hasHighlight());
        option.setHighlighted(false);
        assertFalse(option.hasHighlight());
    }
    
    @Test
    public void setBoundaryTest()
    {
        final int x = 0;
        final int y = 0;
        final int w = 50;
        final int h = 50;
        
        option.setBoundary(x, y, w, h);
        option.setBoundary(new Rectangle(x, y, w, h));
    }
    
    @Test
    public void getBoundaryTest()
    {
        final int x = 76;
        final int y = 23;
        final int w = 500;
        final int h = 500;
        
        option.setBoundary(x, y, w, h);
        
        assertTrue(option.getBoundary().x == x);
        assertTrue(option.getBoundary().y == y);
        assertTrue(option.getBoundary().width == w);
        assertTrue(option.getBoundary().height == h);
        
        final int x1 = 100;
        final int y1 = 10;
        final int w1 = 76;
        final int h1 = 689;
        
        option.setBoundary(new Rectangle(x1, y1, w1, h1));
        
        assertTrue(option.getBoundary().x == x1);
        assertTrue(option.getBoundary().y == y1);
        assertTrue(option.getBoundary().width == w1);
        assertTrue(option.getBoundary().height == h1);
    }
    
    @Test
    public void hasBoundaryTest()
    {
        assertFalse(option.hasBoundary(0, 0, 0, 0));
        option.setBoundary(null);
        assertFalse(option.hasBoundary(0, 0, 0, 0));
        
        final int testX = 30;
        final int testY = 45;
        final Point testPoint = new Point(testX, testY);
        
        int x = 0;
        int y = 0;
        int w = 150;
        int h = 150;
        
        option.setBoundary(x, y, w, h);
        
        assertTrue(option.hasBoundary(testPoint, 0, 0));
        assertTrue(option.hasBoundary(30, 40, -11, 0));
        
        x = -100;
        y = -30;
        
        option.setBoundary(x, y, w, h);
        assertTrue(option.hasBoundary(testPoint, 0, 0));
        assertFalse(option.hasBoundary(-81, 40, 31, 0));
    }
    
    @Test
    public void addTest()
    {
        for (int i = 0; i < 100; i++)
        {
            option.add("A" + i, "Test Text" + i);
        }
    }
    
    @Test
    public void setIndexTest()
    {
        assertTrue(option.getIndex() == 0);
        
        for (int i = 0; i <= 100; i++)
        {
            option.add("A" + i, "Test Text" + i);
        }
        
        for (int i = 1; i <= 100; i++)
        {
            option.setIndex(i);
            assertTrue(option.getIndex() == i);
        }
        
        for (int i = 101; i <= 200; i++)
        {
            option.setIndex(i);
            assertFalse(option.getIndex() == i);
        }
    }
    
    @Test
    public void getIndexTest()
    {
        assertTrue(option.getIndex() == 0);
        
        for (int i = 0; i <= 100; i++)
        {
            option.add("A" + i, "Test Text" + i);
        }
        
        for (int i = 1; i <= 100; i++)
        {
            option.setIndex(i);
            assertTrue(option.getIndex() == i);
        }
        
        for (int i = 101; i <= 200; i++)
        {
            option.setIndex(i);
            assertFalse(option.getIndex() == i);
        }
    }
    
    @Test
    public void getKeyLayerTest()
    {
        Option option1 = new Option(MenuTest.LayerKey.Controls1);
        assertEquals(option1.getKeyLayer(), MenuTest.LayerKey.Controls1);
    }
    
    @Test
    public void hasSelectionTest()
    {
        assertFalse(option.hasSelection());
        option.add("A", "Test");
        assertTrue(option.hasSelection());
    }
    
    @Test
    public void nextTest()
    {
        assertTrue(option.getIndex() == 0);
        assertFalse(option.getIndex() == 1);
        assertFalse(option.getIndex() == 2);
        
        option.add("A", "Test");
        option.add("A", "Test");
        option.add("A", "Test");
        
        option.next();
        
        assertFalse(option.getIndex() == 0);
        assertTrue(option.getIndex() == 1);
        assertFalse(option.getIndex() == 2);
        
        option.next();
        
        assertFalse(option.getIndex() == 0);
        assertFalse(option.getIndex() == 1);
        assertTrue(option.getIndex() == 2);
        
        option.next();
        
        assertTrue(option.getIndex() == 0);
        assertFalse(option.getIndex() == 1);
        assertFalse(option.getIndex() == 2);
    }
    
    @Test
    public void renderTest() throws Exception
    {
        option.add("A", "Test");
        option.setBoundary(0, 0, 100, 100);
        option.render(CustomImageTest.TEST_IMAGE.createGraphics(), Color.BLACK, Color.YELLOW);
        option.setHighlighted(true);
        option.render(CustomImageTest.TEST_IMAGE.createGraphics(), Color.BLACK, Color.YELLOW);
        option.setHighlighted(false);
        option.render(CustomImageTest.TEST_IMAGE.createGraphics(), Color.BLACK, Color.YELLOW);
    }
}