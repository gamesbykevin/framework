package com.gamesbykevin.framework.input;

import java.awt.Point;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Mouse unit test
 * @author GOD
 */
public class MouseTest 
{
    private Mouse mouse;
    
    private static final JPanel PANEL = new JPanel();
    
    private static final Point DEFAULT_LOCATION = new Point(100,100);
    
    private static final int BUTTON_0 = 0;
    
    //default mouse events
    private static final MouseEvent MOUSE_PRESSED  = new MouseEvent(PANEL, 0, System.currentTimeMillis(), MouseEvent.BUTTON1, DEFAULT_LOCATION.x, DEFAULT_LOCATION.y, 1, false);
    private static final MouseEvent MOUSE_RELEASED = new MouseEvent(PANEL, 1, System.currentTimeMillis(), MouseEvent.BUTTON2, DEFAULT_LOCATION.x, DEFAULT_LOCATION.y, 1, false);
    private static final MouseEvent MOUSE_CLICKED  = new MouseEvent(PANEL, 2, System.currentTimeMillis(), MouseEvent.BUTTON3, DEFAULT_LOCATION.x, DEFAULT_LOCATION.y, 1, false);
    
    @BeforeClass
    public static void setUpClass() 
    {
        Mouse mouse = new Mouse();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Mouse mouse = new Mouse();
        mouse.dispose();
        mouse = null;
    }
    
    @Before
    public void setUp() 
    {
        mouse = new Mouse();
        assertNotNull(mouse);
    }
    
    @After
    public void tearDown() 
    {
        mouse = null;
        assertNull(mouse);
    }
    
    @Test
    public void disposeTest() 
    {
        mouse.dispose();
        assertNull(mouse.getLocation());
    }
    
    @Test
    public void setMouseMovedTest()
    {
        mouse.reset();
        mouse.setMouseMoved(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertNotNull(mouse.getLocation());
    }
    
    @Test
    public void hasMouseMovedTest()
    {
        mouse.reset();
        assertFalse(mouse.hasMouseMoved());
        mouse.setMouseMoved(DEFAULT_LOCATION);
        assertTrue(mouse.hasMouseMoved());
    }
    
    @Test
    public void setMousePressedTest()
    {
        mouse.reset();
        mouse.setMousePressed(MOUSE_PRESSED);
        assertTrue(mouse.isMousePressed());
    }
    
    @Test
    public void isMousePressedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMousePressed());
        mouse.setMousePressed(MOUSE_PRESSED);
        assertTrue(mouse.isMousePressed());
    }

    @Test
    public void setMouseDraggedTest()
    {
        mouse.reset();
        mouse.setMouseDragged(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertTrue(mouse.isMouseDragged());
    }
    
    @Test
    public void isMouseDraggedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMouseDragged());
        mouse.setMouseDragged(DEFAULT_LOCATION);
        assertTrue(mouse.isMouseDragged());
    }
    
    @Test
    public void setMouseExitedTest()
    {
        mouse.reset();
        assertFalse(mouse.hasMouseExited());
        mouse.setMouseExited(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertTrue(mouse.hasMouseExited());
    }
    
    @Test
    public void hasMouseExitedTest()
    {
        mouse.reset();
        assertFalse(mouse.hasMouseExited());
        mouse.setMouseExited(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertTrue(mouse.hasMouseExited());
    }
    
    @Test
    public void setMouseEnteredTest()
    {
        mouse.reset();
        assertFalse(mouse.hasMouseEntered());
        mouse.setMouseEntered(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertTrue(mouse.hasMouseEntered());
    }
    
    @Test
    public void hasMouseEnteredTest()
    {
        mouse.reset();
        assertFalse(mouse.hasMouseEntered());
        mouse.setMouseEntered(DEFAULT_LOCATION);
        assertTrue(mouse.hasMouseEntered());
    }
    
    @Test
    public void setMouseReleasedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMouseReleased());
        mouse.setMouseReleased(MOUSE_RELEASED);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        assertTrue(mouse.isMouseReleased());
        assertFalse(mouse.isMouseDragged());
        assertTrue(mouse.getButton() == BUTTON_0);
    }
    
    @Test
    public void isMouseReleasedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMouseReleased());
        mouse.setMouseReleased(MOUSE_RELEASED);
        assertTrue(mouse.isMouseReleased());
    }
    
    @Test
    public void setMouseClickedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMouseClicked());
        mouse.setMouseClicked(MOUSE_CLICKED);
        assertTrue(mouse.isMouseClicked());
        assertTrue(mouse.getButton() == BUTTON_0);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
        
        
    }
    
    @Test
    public void isMouseClickedTest()
    {
        mouse.reset();
        assertFalse(mouse.isMouseClicked());
        mouse.setMouseClicked(MOUSE_CLICKED);
        assertTrue(mouse.isMouseClicked());
    }
    
    @Test
    public void getLocationTest()
    {
        assertTrue(mouse.getLocation().x == 0);
        assertTrue(mouse.getLocation().y == 0);
        mouse.setMouseDragged(DEFAULT_LOCATION);
        assertTrue(mouse.getLocation().x == DEFAULT_LOCATION.x);
        assertTrue(mouse.getLocation().y == DEFAULT_LOCATION.y);
    }
    
    @Test
    public void getButtonTest()
    {
        mouse.reset();
        assertTrue(mouse.getButton() == -1);
        mouse.setMouseClicked(MOUSE_CLICKED);
        assertTrue(mouse.getButton() == MOUSE_CLICKED.getButton());
        mouse.setMousePressed(MOUSE_PRESSED);
        assertTrue(mouse.getButton() == MOUSE_PRESSED.getButton());
        mouse.setMouseReleased(MOUSE_RELEASED);
        assertTrue(mouse.getButton() == MOUSE_RELEASED.getButton());
    }
    
    @Test
    public void hitLeftButtonTest()
    {
        mouse.reset();
        assertTrue(mouse.getButton() == -1);
        mouse.setMousePressed(MOUSE_PRESSED);
        assertFalse(mouse.hitLeftButton());
        assertFalse(mouse.hitMiddleButton());
        assertFalse(mouse.hitRightButton());
    }
    
    @Test
    public void hitMiddleButtonTest()
    {
        mouse.reset();
        assertTrue(mouse.getButton() == -1);
        mouse.setMouseReleased(MOUSE_RELEASED);
        assertFalse(mouse.hitLeftButton());
        assertFalse(mouse.hitMiddleButton());
        assertFalse(mouse.hitRightButton());
    }
    
    @Test
    public void hitRightButtonTest()
    {
        mouse.reset();
        assertTrue(mouse.getButton() == -1);
        mouse.setMouseReleased(MOUSE_CLICKED);
        assertFalse(mouse.hitLeftButton());
        assertFalse(mouse.hitMiddleButton());
        assertFalse(mouse.hitRightButton());
    }
    
    public void resetTest()
    {
        mouse.reset();
        assertTrue(mouse.getButton() == -1);
        assertFalse(mouse.hitLeftButton());
        assertFalse(mouse.hitMiddleButton());
        assertFalse(mouse.hitRightButton());
        assertFalse(mouse.isMouseClicked());
        assertFalse(mouse.isMouseDragged());
        assertFalse(mouse.hasMouseEntered());
        assertFalse(mouse.hasMouseExited());
        assertFalse(mouse.hasMouseMoved());
        assertFalse(mouse.isMousePressed());
        assertFalse(mouse.isMouseReleased());
    }
}