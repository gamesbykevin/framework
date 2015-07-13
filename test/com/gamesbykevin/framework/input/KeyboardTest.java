package com.gamesbykevin.framework.input;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Keyboard unit test
 * @author GOD
 */
public class KeyboardTest 
{
    private Keyboard keyboard;
    
    public static Keyboard TEST_KEYBOARD = new Keyboard();
    
    private static final JPanel PANEL = new JPanel();
    
    //default key events
    private static final KeyEvent KEY_PRESSED = new KeyEvent(PANEL, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
    private static final KeyEvent KEY_RELEASED = new KeyEvent(PANEL, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_V,'V');
    
    @BeforeClass
    public static void setUpClass() 
    {
        Keyboard keyboard = new Keyboard();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Keyboard keyboard = new Keyboard();
        keyboard.dispose();
        keyboard = null;
    }
    
    @Before
    public void setUp() 
    {
        keyboard = new Keyboard();
    }
    
    @After
    public void tearDown() 
    {
        keyboard = null;
        assertNull(keyboard);
    }
    
    @Test
    public void diposeTest() 
    {
        keyboard.dispose();
    }
    
    @Test
    public void resetTest()
    {
        keyboard.reset();
    }
    
    @Test
    public void addKeyReleasedTest()
    {
        keyboard.reset();
        keyboard.addKeyReleased(KEY_RELEASED.getKeyCode());
        
        assertTrue(keyboard.hasKeyReleased(KEY_RELEASED.getKeyCode()));
        assertFalse(keyboard.hasKeyPressed(KEY_RELEASED.getKeyCode()));
    }
    
    @Test
    public void addKeyPressedTest()
    {
        keyboard.reset();
        keyboard.addKeyPressed(KEY_PRESSED.getKeyCode());
        
        assertTrue(keyboard.hasKeyPressed(KEY_PRESSED.getKeyCode()));
        assertFalse(keyboard.hasKeyReleased(KEY_PRESSED.getKeyCode()));
    }
    
    @Test
    public void removeKeyReleasedTest()
    {
        keyboard.reset();
        keyboard.addKeyReleased(KEY_RELEASED.getKeyCode());
        assertTrue(keyboard.hasKeyReleased(KEY_RELEASED.getKeyCode()));
        keyboard.removeKeyReleased(KEY_RELEASED.getKeyCode());
        assertFalse(keyboard.hasKeyReleased(KEY_RELEASED.getKeyCode()));
    }
    
    @Test
    public void removeKeyPressedTest()
    {
        keyboard.reset();
        keyboard.addKeyPressed(KEY_PRESSED.getKeyCode());
        assertTrue(keyboard.hasKeyPressed(KEY_PRESSED.getKeyCode()));
        keyboard.removeKeyPressed(KEY_PRESSED.getKeyCode());
        assertFalse(keyboard.hasKeyPressed(KEY_PRESSED.getKeyCode()));
    }
    
    @Test
    public void isKeyReleasedTest()
    {
        keyboard.reset();
        
        assertFalse(keyboard.isKeyReleased());
        keyboard.addKeyReleased(KEY_RELEASED.getKeyCode());
        assertTrue(keyboard.isKeyReleased());
    }
    
    @Test
    public void isKeyPressedTest()
    {
        keyboard.reset();
        
        assertFalse(keyboard.isKeyPressed());
        keyboard.addKeyPressed(KEY_PRESSED.getKeyCode());
        assertTrue(keyboard.isKeyPressed());
    }
    
    @Test
    public void hasKeyPressedTest()
    {
        keyboard.reset();
        assertFalse(keyboard.hasKeyPressed(KEY_PRESSED.getKeyCode()));
        keyboard.addKeyPressed(KEY_PRESSED.getKeyCode());
        assertTrue(keyboard.hasKeyPressed(KEY_PRESSED.getKeyCode()));
    }
    
    @Test
    public void hasKeyReleasedTest()
    {
        keyboard.reset();
        assertFalse(keyboard.hasKeyReleased(KEY_RELEASED.getKeyCode()));
        keyboard.addKeyReleased(KEY_RELEASED.getKeyCode());
        assertTrue(keyboard.hasKeyReleased(KEY_RELEASED.getKeyCode()));
    }
    
    @Test
    public void resetKeyReleasedTest()
    {
        keyboard.reset();
        assertFalse(keyboard.isKeyReleased());
        keyboard.addKeyReleased(KEY_RELEASED.getKeyCode());
        assertTrue(keyboard.isKeyReleased());
        keyboard.resetKeyReleased();
        assertFalse(keyboard.isKeyReleased());
    }
    
    @Test
    public void resetKeyPressedTest()
    {
        keyboard.reset();
        assertFalse(keyboard.isKeyPressed());
        keyboard.addKeyPressed(KEY_PRESSED.getKeyCode());
        assertTrue(keyboard.isKeyPressed());
        keyboard.resetKeyPressed();
        assertFalse(keyboard.isKeyPressed());
    }
}