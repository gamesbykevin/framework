package com.gamesbykevin.framework.base;


import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Animation tests
 * @author GOD
 */
public class AnimationTest 
{
    private Animation animation;
    
    @BeforeClass
    public static void setUpClass() 
    {
        final int x = 0;
        final double z = 0;
        final long delay = 0;
        
        Animation animation;
        animation = new Animation(new Rectangle(x,x,x,x), delay);
        animation = new Animation(x,x,x,x,delay);
        animation = new Animation(z,z,z,z,delay);
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
        animation.dispose();
        animation = null;
        
        //assume animation is null
        assertNull(animation);
    }
    
    @Test
    public void hasLoopTest() 
    {
        //create animation
        animation = new Animation(0,0,0,0,0);
        assertFalse(animation.hasLoop());
        
        animation.setLoop(true);
        assertTrue(animation.hasLoop());
        
        animation.setLoop(false);
        assertFalse(animation.hasLoop());
    }
    
    @Test
    public void hasStartedTest() 
    {
        //create animation
        animation = new Animation(0,0,0,0,0);
        
        //assume false
        assertFalse(animation.hasStarted());
        
        //call update
        animation.update(0);
        
        //assume true
        assertTrue(animation.hasStarted());
    }
    
    @Test
    public void hasFinishedTest()
    {
        //create animation
        animation = new Animation(0,0,0,0,0);

        //assume false
        assertFalse(animation.hasFinished());
        
        //set true
        animation.setFinished(true);
        
        //assume true
        assertTrue(animation.hasFinished());
        
        //reset
        animation.reset();
        
        //assume false
        assertFalse(animation.hasFinished());
    }
    
    @Test
    public void addTest()
    {
        final long delay = 0;
        final int x = 10, y = 25, w = 100, h = 75;
        
        //create animation
        animation = new Animation(null,delay);
        
        //assume value is equal
        assertTrue(animation.getDelayMax() == delay);
        
        //assume null
        assertNull(animation.getLocation());
        
        //add animation
        animation.add(x, y, w, h, delay);
        
        //assume not null
        assertNotNull(animation.getLocation(1));
        
        //create animation
        animation = new Animation(x, y, w, h, delay);
        
        //check the values
        assertTrue(animation.getLocation(0).x == x);
        assertTrue(animation.getLocation(0).y == y);
        assertTrue(animation.getLocation(0).width == w);
        assertTrue(animation.getLocation(0).height == h);
        
        //add null animation
        animation.add(null, delay);
        
        //assume null
        assertNull(animation.getLocation(1));
    }
}