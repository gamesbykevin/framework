package com.gamesbykevin.framework.resources;

import com.gamesbykevin.framework.awt.CustomImageTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Rectangle;

/**
 * Progress unit test
 * @author GOD
 */
public class ProgressTest 
{
    private Progress progress;
    
    @BeforeClass
    public static void setUpClass() 
    {
        Progress progress = new Progress(100);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Progress progress = new Progress(100);
        progress.dispose();
    }
    
    @Before
    public void setUp() 
    {
        progress = new Progress(100);
    }
    
    @After
    public void tearDown() 
    {
        progress.dispose();
        progress = null;
    }
    
    @Test
    public void setScreenTest() 
    {
        final int x = 10;
        final int y = 25;
        final int w = 200;
        final int h = 200;
        
        progress.setScreen(x, y, w, h);
        
        progress.setScreen(new Rectangle(x, y, w, h));
    }
    
    @Test
    public void getScreenTest() 
    {
        assertNull(progress.getScreen());
        
        final int x = 10;
        final int y = 25;
        final int w = 200;
        final int h = 200;
        
        progress.setScreen(x, y, w, h);
        
        assertTrue(progress.getScreen().x == x);
        assertTrue(progress.getScreen().y == y);
        assertTrue(progress.getScreen().width == w);
        assertTrue(progress.getScreen().height == h);
        
        final int x1 = 10;
        final int y1 = 25;
        final int w1 = 200;
        final int h1 = 200;
        
        progress.setScreen(new Rectangle(x1, y1, w1, h1));
        
        assertTrue(progress.getScreen().x == x1);
        assertTrue(progress.getScreen().y == y1);
        assertTrue(progress.getScreen().width == w1);
        assertTrue(progress.getScreen().height == h1);
    }
    
    @Test
    public void setDescriptionTest() 
    {
        progress.setDescription("Test");
        progress.setDescription(null);
    }
    
    @Test
    public void getDescriptionTest() 
    {
        final String test = "test";
        
        assertNull(progress.getDescription());
        progress.setDescription(test);
        assertEquals(progress.getDescription(), test);
        progress.setDescription(null);
        assertNull(progress.getDescription());
    }
    
    @Test
    public void getGoalTest()
    {
        int goal = 94;
        
        assertFalse(progress.getGoal() == goal);
        
        progress = new Progress(goal);
        
        assertTrue(progress.getGoal() == goal);
    }
    
    @Test
    public void changeGoalTest()
    {
        int goal = 94;
        
        assertFalse(progress.getGoal() == goal);
        
        progress.changeGoal(goal);
        
        assertTrue(progress.getGoal() == goal);
    }
    
    @Test
    public void increaseTest()
    {
        assertTrue(progress.getCount() == 0);
        progress.increase();
        assertFalse(progress.getCount() == 0);
        assertTrue(progress.getCount() == 1);
    }
    
    @Test
    public void setCountTest()
    {
        assertTrue(progress.getCount() == 0);
        
        int count = 12393;
        
        progress.setCount(count);
        
        assertTrue(progress.getCount() == count);
    }
    
    @Test
    public void setCompleteTest()
    {
        int goal = 100;
        int count = 50;
        
        progress = new Progress(goal);
        
        assertFalse(progress.getCount() == progress.getGoal());
        
        progress.setCount(count);
        
        progress.setComplete();
        
        assertTrue(progress.getCount() == progress.getGoal());
    }
    
    @Test
    public void isCompleteTest()
    {
        int goal = 100;
        
        progress = new Progress(goal);
        
        assertFalse(progress.isComplete());
        
        progress.setCount(goal);
        
        assertTrue(progress.isComplete());
        
        progress = new Progress(goal);
        assertFalse(progress.isComplete());
        progress.setComplete();
        assertTrue(progress.isComplete());
    }
    
    @Test
    public void getProgressTest()
    {
        int goal = 100;
        int count = 50;
        
        progress = new Progress(goal);
        progress.setCount(count);
        
        assertTrue(progress.getProgress() == 0.5);
        
        progress.setCount(75);
        
        assertTrue(progress.getProgress() == 0.75);
        
        progress.setComplete();
        
        assertTrue(progress.getProgress() == 1.0);
    }
    
    @Test
    public void renderTest()
    {
        progress.render(CustomImageTest.TEST_IMAGE.createGraphics());
    }
}