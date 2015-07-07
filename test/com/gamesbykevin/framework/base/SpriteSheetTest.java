package com.gamesbykevin.framework.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sprite Sheet unit test
 * @author GOD
 */
public class SpriteSheetTest 
{
    private SpriteSheet sheet, tmp;
    
    //tmp animation key
    private static final String KEY = "KEY";
    private static final String KEY1 = "KEY1";
    
    private int x = 10, y = 5, w = 50, h = 15;
    private long delay = 100L;
        
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        int x = 10, y = 5, w = 50, h = 15;
        long delay = 100L;
        
        SpriteSheet tmp = new SpriteSheet();
        tmp.add(x, y, w, h, delay, KEY);
        tmp.setCurrent(KEY);
        
        SpriteSheet sheet = new SpriteSheet();
        assertNotNull(sheet);
        sheet = new SpriteSheet(tmp);
        assertNotNull(sheet);
        sheet = new SpriteSheet(100L);
        assertNotNull(sheet);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        SpriteSheet sheet = new SpriteSheet();
        sheet.dispose();
        sheet = null;
        assertNull(sheet);
    }
    
    @Before
    public void setUp() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        assertNull(sheet.getCurrent());
        sheet.setCurrent(KEY);
        assertNotNull(sheet);
        assertNotNull(sheet.getCurrent());
    }
    
    @After
    public void tearDown() 
    {
        sheet = new SpriteSheet();
        sheet.dispose();
        sheet = null;
        assertNull(sheet);
    }
    
    @Test
    public void disposeTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        assertNotNull(sheet.getCurrent());
        sheet.dispose();
        assertNull(sheet.getCurrent());
        assertFalse(sheet.hasAnimation(KEY));
        assertFalse(sheet.hasAnimations());
    }
    
    @Test
    public void hasFinishedTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.add(x + 35, y - 10, w + 100, h - 63, delay, KEY1);
        sheet.setCurrent(KEY);
        
        assertFalse(sheet.hasFinished());
        sheet.update(delay);
        assertTrue(sheet.hasFinished());
    }
    
    @Test
    public void hasStartedTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.add(x + 35, y - 10, w + 100, h - 63, delay, KEY1);
        
        sheet.setCurrent(KEY);
        assertFalse(sheet.hasStarted());
        sheet.update(delay);
        assertTrue(sheet.hasStarted());
        
        sheet.setCurrent(KEY1);
        assertFalse(sheet.hasStarted());
        sheet.update(delay);
        assertTrue(sheet.hasStarted());
    }
    
    @Test
    public void hasLoopTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        assertFalse(sheet.hasLoop());
        
        Animation a = new Animation(x + 35, y - 10, w + 100, h - 63, delay);
        a.setLoop(true);
        sheet.add(a, KEY1);
        sheet.setCurrent(KEY1);
        assertTrue(sheet.hasLoop());
        
        a.setLoop(false);
        sheet.add(a, KEY1);
        sheet.setCurrent(KEY1);
        assertFalse(sheet.hasLoop());
    }
    
    @Test
    public void getLocationTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        assertTrue(sheet.getLocation().x == x);
        assertTrue(sheet.getLocation().y == y);
        assertTrue(sheet.getLocation().width == w);
        assertTrue(sheet.getLocation().height == h);
    }
    
    @Test
    public void resetTest() throws Exception
    {
        sheet.reset();
    }
    
    @Test
    public void hasAnimationTest()
    {
        sheet = new SpriteSheet();
        
        assertFalse(sheet.hasAnimation(KEY));
        sheet.add(x, y, w, h, delay, KEY);
        assertTrue(sheet.hasAnimation(KEY));
        
        assertFalse(sheet.hasAnimation(KEY1));
        sheet.add(x, y, w, h, delay, KEY1);
        assertTrue(sheet.hasAnimation(KEY1));
    }
    
    @Test
    public void hasAnimationsTest()
    {
        sheet = new SpriteSheet();
        assertFalse(sheet.hasAnimations());
        sheet.add(x, y, w, h, delay, KEY);
        assertTrue(sheet.hasAnimations());
    }
    
    @Test
    public void setCurrentTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
    }
    
    @Test
    public void getCurrentTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        
        assertNull(sheet.getCurrent());
        sheet.setCurrent(KEY);
        assertNotNull(sheet.getCurrent());
        assertEquals(sheet.getCurrent(), KEY);
    }
    
    @Test
    public void hasDelayTest()
    {
        sheet = new SpriteSheet();
        assertFalse(sheet.hasDelay());
        sheet = new SpriteSheet(1);
        assertTrue(sheet.hasDelay());

        assertFalse(sheet.hasDelay(-100));
        assertTrue(sheet.hasDelay(1000));
    }
    
    @Test
    public void getDelayTest()
    {
        sheet = new SpriteSheet();
        assertTrue(sheet.getDelay() == -1);
        
        sheet = new SpriteSheet(100);
        assertTrue(sheet.getDelay() == 100);
    }
    
    @Test
    public void getDelayMaxTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        
        assertTrue(sheet.getDelayMax() == delay);
    }
    
    @Test
    public void setDelayTest()
    {
        sheet = new SpriteSheet();
        assertFalse(sheet.getDelay() == delay);
        sheet.setDelay(delay);
        assertTrue(sheet.getDelay() == delay);
    }
    
    @Test
    public void setPauseTest()
    {
        sheet = new SpriteSheet();
        sheet.setPause(true);
        sheet.setPause(false);
    }
    
    @Test
    public void isPausedTest()
    {
        sheet = new SpriteSheet();
        assertFalse(sheet.isPaused());
        sheet.setPause(true);
        assertTrue(sheet.isPaused());
        sheet.setPause(false);
        assertFalse(sheet.isPaused());
    }
    
    @Test
    public void addTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        assertNotNull(sheet.getSpriteSheetAnimation(KEY));
        
        Animation a = new Animation(x + 35, y - 10, w + 100, h - 63, delay);
        sheet.add(a, KEY1);
        assertNotNull(sheet.getSpriteSheetAnimation(KEY1));
    }
    
    @Test
    public void removeTest() 
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.add(x, y, w, h, delay, KEY1);
        
        assertTrue(sheet.hasAnimation(KEY));
        assertTrue(sheet.hasAnimation(KEY1));
        
        sheet.remove(KEY);
        assertFalse(sheet.hasAnimation(KEY));
        assertTrue(sheet.hasAnimation(KEY1));
        
        sheet.remove(KEY1);
        assertFalse(sheet.hasAnimation(KEY));
        assertFalse(sheet.hasAnimation(KEY1));
    }
    
    @Test
    public void updateTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        sheet.update(delay);
        
        sheet = new SpriteSheet(delay);
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        sheet.update();
    }
    
    @Test
    public void getSpriteSheetAnimationTest() throws Exception
    {
        sheet = new SpriteSheet();
        sheet.add(x, y, w, h, delay, KEY);
        sheet.setCurrent(KEY);
        assertNotNull(sheet.getSpriteSheetAnimation());
        assertNotNull(sheet.getSpriteSheetAnimation(KEY));
        
        assertNull(sheet.getSpriteSheetAnimation(KEY1));
        sheet.add(x, y, w, h, delay, KEY1);
        assertNotNull(sheet.getSpriteSheetAnimation(KEY1));
    }
}