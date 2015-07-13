package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.awt.CustomImageTest;
import com.gamesbykevin.framework.input.Keyboard;
import com.gamesbykevin.framework.input.KeyboardTest;
import com.gamesbykevin.framework.input.Mouse;
import com.gamesbykevin.framework.input.MouseTest;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Menu unit test
 * @author GOD
 */
public class MenuTest extends Menu
{
    public static final Rectangle WINDOW = new Rectangle(0, 0, 512, 512);
    
    public static final String MENU_XML_CONFIG = "resources/menu.xml";
    
    private MenuTest menu;
    
    /**
     * Identify each option we want to access
     * Spelling should match the options id=? in the .xml file
     */
    public enum OptionKey 
    {
        Sound, FullScreen, Game
    }
    
    /**
     * The layer keys we want to access.
     * Spelling of LayerKey has to match layer id = "" in menu.xml file
     */
    public enum LayerKey 
    {
        Initial, Credits, MainTitle, StaticCredits, Options,
        Controls1, Instructions1, Instructions2, 
        GameStart, OptionsInGame, 
        ExitGameConfirm, ExitGameConfirmed, NoFocus
    }
    
    public MenuTest() throws Exception
    {
        super(WINDOW, MENU_XML_CONFIG, MenuTest.class);
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        assertTrue(Menu.MAX_FONT_SIZE == 48.0f);
        
        MenuTest menu = new MenuTest();
        
        for (LayerKey key : LayerKey.values())
        {
            assertTrue(menu.hasLayer(key.toString()));
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        MenuTest menu = new MenuTest();
        menu.dispose();
        menu = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        menu = new MenuTest();
    }
    
    @After
    public void tearDown() throws Exception
    {
        menu = new MenuTest();
        menu.dispose();
        menu = null;
    }
    
    @Test
    public void disposeTest() throws Exception
    {
        menu = new MenuTest();
        menu.dispose();
        menu = null;
    }
    
    @Test
    public void setEnabledTest() throws Exception
    {
        menu = new MenuTest();
        menu.setEnabled(true);
        menu.setEnabled(false);
    }
    
    @Test
    public void isEnabledTest() throws Exception
    {
        menu = new MenuTest();
        assertTrue(menu.isEnabled());
        menu.setEnabled(true);
        assertTrue(menu.isEnabled());
        menu.setEnabled(false);
        assertFalse(menu.isEnabled());
    }
    
    @Test
    public void addTest() throws Exception
    {
        menu = new MenuTest();
    }
    
    @Test
    public void isCurrentLayerTest() throws Exception
    {
        assertFalse(menu.isCurrentLayer(LayerKey.GameStart));
        menu.setLayer(LayerKey.GameStart);
        assertTrue(menu.isCurrentLayer(LayerKey.GameStart));
    }
    
    @Test
    public void resetTest() throws Exception
    {
        for (LayerKey key : LayerKey.values())
        {
            menu.setLayer(key);
            menu.reset();
        }
    }
    
    @Test
    public void setFinishTest()
    {
        for (LayerKey key : LayerKey.values())
        {
            menu.setFinish(key);
        }
    }
    
    @Test
    public void setLayerTest() throws Exception
    {
        assertFalse(menu.isCurrentLayer(LayerKey.NoFocus));
        menu.setLayer(LayerKey.NoFocus);
        assertTrue(menu.isCurrentLayer(LayerKey.NoFocus));
    }
    
    @Test
    public void hasLayerTest() throws Exception
    {
        for (LayerKey key : LayerKey.values())
        {
            assertTrue(menu.hasLayer(key.toString()));
        }
        
        assertFalse(menu.hasLayer("TSIUNDCI NSI"));
    }
    
    @Test
    public void getLayerTest() throws Exception
    {
        assertNull(menu.getLayer());
        menu.setLayer(LayerKey.Credits);
        assertNotNull(menu.getLayer());
        
        for (LayerKey key : LayerKey.values())
        {
            assertNotNull(menu.getLayer(key));
        }
    }
    
    @Test
    public void getKeyTest() throws Exception
    {
        final String layerKey = LayerKey.MainTitle.toString();
        
        assertNull(menu.getKey());
        menu.setLayer(layerKey);
        assertNotNull(menu.getKey());
        assertEquals(menu.getKey(), layerKey);
    }
    
    @Test
    public void hasOptionTest() throws Exception
    {
        final String layerKey = LayerKey.Options.toString();
        final String optionKey = OptionKey.Game.toString();
        
        assertFalse(menu.hasOption(LayerKey.Credits, optionKey));
        assertTrue(menu.hasOption(layerKey, optionKey));
    }
    
    @Test
    public void getOptionTest() throws Exception
    {
        final String layerKey = LayerKey.Options.toString();
        final String optionKey = OptionKey.Game.toString();
        
        assertNull(menu.getOption(LayerKey.Instructions2, optionKey));
        assertNotNull(menu.getOption(layerKey, optionKey));
    }
    
    @Test
    public void getOptionSelectionIndexTest() throws Exception
    {
        final String layerKey = LayerKey.Options.toString();
        final String optionKey = OptionKey.Game.toString();
        
        assertTrue(menu.getOptionSelectionIndex(layerKey, optionKey) == 0);
    }
    
    @Test
    public void setOptionSelectionIndexTest() throws Exception
    {
        final String layerKey = LayerKey.Options.toString();
        final String optionKey = OptionKey.Game.toString();
        
        menu.setOptionSelectionIndex(optionKey, 1);
        
        assertTrue(menu.getOptionSelectionIndex(layerKey, optionKey) == 1);
        
        menu.setOptionSelectionIndex(optionKey, 2000);
        
        assertTrue(menu.getOptionSelectionIndex(layerKey, optionKey) == 0);
    }
    
    @Test
    public void isSetupTest() throws Exception
    {
        assertTrue(menu.isSetup());
    }
    
    @Test
    public void hasFinishedTest() throws Exception
    {
        assertFalse(menu.hasFinished());
        
        menu.setFinish(LayerKey.GameStart);
        menu.setLayer(LayerKey.GameStart);
        
        assertTrue(menu.hasFinished());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        menu.setFinish(LayerKey.GameStart);
        
        for (LayerKey key : LayerKey.values())
        {
            //we know this is irrevelant
            if (key.equals(LayerKey.ExitGameConfirmed))
                continue;
            
            menu.setLayer(key);
            menu.update(MouseTest.TEST_MOUSE, KeyboardTest.TEST_KEYBOARD, LayerTest.TIME);
        }
    }
    
    @Test
    public void getFontSizeTest()
    {
        Graphics graphics = CustomImageTest.TEST_IMAGE.createGraphics();

        final String text = "TEST TEXT";
        
        final int containerWidth = 1000;
        
        
        //fontSize for the title text
        float fontSize;
        
        //get width of text at the max font size
        final int textWidth = graphics.getFontMetrics(graphics.getFont().deriveFont(MAX_FONT_SIZE)).stringWidth(text);

        //if the text width using the max font size is bigger than the container width
        if (textWidth >= containerWidth)
        {
            //font size will be a fraction of max based on the ratio of the two widths
            fontSize = (float)(((double)containerWidth / (double)textWidth) * MAX_FONT_SIZE);
            
            //convert float to int, example 28.6764 will be 28
            final int testSize = (int)fontSize;
            
            //convert int to float, example 28 will be 28.0
            fontSize = (float)testSize;
            
            //decrease the font size by 1 to ensure it will fit within the container
            fontSize--;
        }
        else
        {
            fontSize = MAX_FONT_SIZE;
        }
        
        assertTrue(fontSize == Menu.getFontSize(text, containerWidth, graphics));
    }
    
    @Test
    public void renderTest() throws Exception
    {
        for (LayerKey key : LayerKey.values())
        {
            menu.setLayer(key);
            menu.render(CustomImageTest.TEST_IMAGE.createGraphics());
        }
    }
}