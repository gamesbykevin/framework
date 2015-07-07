package com.gamesbykevin.framework.display;

import javax.swing.JApplet;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Full Screen unit test
 * @author GOD
 */
public class FullScreenTest 
{
    private FullScreen screen;
    
    private static final JApplet applet = new JApplet();
    private static final JPanel panel = new JPanel();
    
    @BeforeClass
    public static void setUpClass() 
    {
        FullScreen screen = new FullScreen();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        FullScreen screen = new FullScreen();
        screen.dispose();
        screen = null;
        assertNull(screen);
    }
    
    @Before
    public void setUp() 
    {
        screen = new FullScreen();
    }
    
    @After
    public void tearDown() 
    {
        screen = new FullScreen();
        screen.dispose();
        screen = null;
        assertNull(screen);
    }
    
    @Test
    public void disposeTest() 
    {
        screen = new FullScreen();
        screen.dispose();
        screen = null;
        assertNull(screen);
    }
    
    @Test
    public void isEnabledTest()
    {
        screen = new FullScreen();
        assertFalse(screen.isEnabled());
        
        screen.switchFullScreen(applet);
        assertTrue(screen.isEnabled());
    }
    
    @Test
    public void switchFullScreenTest()
    {
        screen = new FullScreen();
        screen.switchFullScreen(applet);
        
        screen = new FullScreen();
        screen.switchFullScreen(panel);
    }
}