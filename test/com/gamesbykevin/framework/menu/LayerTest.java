package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.awt.CustomImageTest;
import com.gamesbykevin.framework.maze.MazeTest;
import com.gamesbykevin.framework.util.Timer;
import com.gamesbykevin.framework.util.Timers;

import com.gamesbykevin.framework.input.KeyboardTest;
import com.gamesbykevin.framework.input.MouseTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Rectangle;

/**
 * Layer unit test
 * @author GOD
 */
public class LayerTest 
{
    public static long TIME = Timers.toNanoSeconds(1000L);
    
    //our layer
    private Layer layer;
    
    private static final String KEY_1 = "TestKey1";
    private static final String KEY_2 = "TestKey2";
    public static final String TITLE_1 = "TestTitle1";
    public static final String TITLE_2 = "TestTitle2";
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Rectangle rectangle = new Rectangle(0, 0, 100, 100);
        
        Layer layer;
        
        for (Layer.Type type : Layer.Type.values())
        {
            layer = new Layer(type, rectangle);
            layer = new Layer(type, 100, 300, 60, 83);
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Layer layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        
        assertNotNull(layer);
        
        layer.dispose();
        layer = null;
        
        assertNull(layer);
    }
    
    @Before
    public void setUp() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
    }
    
    @After
    public void tearDown() 
    {
        assertNotNull(layer);
        
        layer.dispose();
        layer = null;
        
        assertNull(layer);
    }
    
    @Test
    public void setEnabledTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setEnabled(true);
        assertTrue(layer.isEnabled());
        layer.setEnabled(false);
        assertFalse(layer.isEnabled());
    }
    
    @Test
    public void isEnabledTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        assertTrue(layer.isEnabled());
        layer.setEnabled(true);
        assertTrue(layer.isEnabled());
        layer.setEnabled(false);
        assertFalse(layer.isEnabled());
    }
    
    @Test
    public void setForceTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setForce(true);
        layer.setForce(false);
    }
    
    @Test
    public void setPauseTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setPause(true);
        layer.setPause(false);
    }
    
    @Test
    public void resetTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.reset();
    }
    
    @Test
    public void setTimerTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setTimer(null);
        layer.setTimer(new Timer());
        layer.setTimer(new Timer(Timers.toNanoSeconds(1000L)));
        layer.setTimer(new Timer(Timers.toNanoSeconds(1)));
    }
    
    @Test
    public void setSoundTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setSound(null);
    }
    
    @Test
    public void setOptionSoundTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setOptionSound(null);
    }
    
    @Test
    public void setImageTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setImage(null);
        layer.setImage(MazeTest.IMAGE);
    }
    
    @Test
    public void setNextLayerTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setNextLayer(KEY_1);
        layer.setNextLayer(KEY_2);
    }
    
    @Test
    public void getNextLayerKeyTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        assertNull(layer.getNextLayerKey());
        layer.setNextLayer(KEY_1);
        assertEquals(layer.getNextLayerKey(), KEY_1);
        assertNotSame(layer.getNextLayerKey(), KEY_2);
        
        layer.setNextLayer(KEY_2);
        assertNotSame(layer.getNextLayerKey(), KEY_1);
        assertEquals(layer.getNextLayerKey(), KEY_2);
    }
    
    @Test
    public void setTitleTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setTitle(null);
        layer.setTitle(TITLE_1);
    }
    
    @Test
    public void addTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        Option option = null;
        option = new Option(TITLE_1);
        layer.add(KEY_1, option);
        
        option = new Option(TITLE_2);
        layer.add(KEY_2, option);
    }
    
    @Test
    public void setCurrentTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        Option option = null;
        option = new Option(TITLE_1);
        layer.add(KEY_1, option);
        
        option = new Option(TITLE_2);
        layer.add(KEY_2, option);
        
        layer.setCurrent(KEY_2);
        layer.setCurrent(KEY_1);
        layer.setCurrent(KEY_2);
        layer.setCurrent(KEY_1);
        layer.setCurrent(0, 0);
        layer.setCurrent(10000, 1000);
    }
    
    @Test
    public void hasOptionsTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        assertFalse(layer.hasOptions());
        
        Option option = new Option(TITLE_1);
        layer.add(KEY_1, option);
        
        assertTrue(layer.hasOptions());
    }
    
    @Test
    public void getOptionTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        assertNull(layer.getOption(KEY_1));
        assertNull(layer.getOption(KEY_2));
        
        Option option = null;
        option = new Option(TITLE_1);
        layer.add(KEY_1, option);
        
        assertNotNull(layer.getOption(KEY_1));
        assertNull(layer.getOption(KEY_2));
        
        option = new Option(TITLE_2);
        layer.add(KEY_2, option);
        
        assertNotNull(layer.getOption(KEY_1));
        assertNotNull(layer.getOption(KEY_2));
    }
    
    @Test
    public void updateTest() throws Exception
    {
        MenuTest menu = new MenuTest();
        
        layer.update(menu, MouseTest.TEST_MOUSE, KeyboardTest.TEST_KEYBOARD, TIME);
    }
    
    @Test
    public void getSoundTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        assertNull(layer.getSound());
    }
    
    @Test
    public void resetOptionsImageTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.resetOptionsImage();
    }
    
    @Test
    public void setOptionContainerRatioTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setOptionContainerRatio(0.0000000001f);
        layer.setOptionContainerRatio(0.5f);
        layer.setOptionContainerRatio(1f);
    }

    @Test
    public void setOptionContainerBorderThicknessTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.setOptionContainerBorderThickness(.0000000000001f);
        layer.setOptionContainerBorderThickness(.1f);
        layer.setOptionContainerBorderThickness(1f);
        layer.setOptionContainerBorderThickness(5f);
        layer.setOptionContainerBorderThickness(10f);
    }
    
    @Test
    public void renderTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.render(CustomImageTest.TEST_IMAGE.createGraphics(), MenuTest.WINDOW);
    }
    
    @Test
    public void disposeTest() throws Exception
    {
        layer = new Layer(Layer.Type.NONE, 0,0,1,1);
        layer.dispose();
    }
}