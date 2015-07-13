package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Font Manager unit test
 * @author GOD
 */
public class FontManagerTest 
{
    public static final String LOCATION = "resources/font.xml";
    
    private FontManager manager;
    
    public enum Key
    {
        Test1, Test2, Test3
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        FontManager manager = new FontManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(FontManagerTest.class);
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        FontManager manager = new FontManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(FontManagerTest.class);
        }
        
        manager.dispose();
    }
    
    @Before
    public void setUp() throws Exception
    {
        manager = new FontManager(LOCATION);
    }
    
    @After
    public void tearDown() throws Exception
    {
        manager.dispose();
        manager = null;
    }
    
    @Test
    public void updateTest() throws Exception
    {
        while (!manager.isComplete())
        {
            manager.update(FontManagerTest.class);
        }
    }
    
    @Test
    public void getTest() throws Exception
    {
        for (Key key : Key.values())
        {
            assertNull(manager.get(key));
        }
        
        while (!manager.isComplete())
        {
            manager.update(FontManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            assertNotNull(manager.get(key));
        }
    }
    
    @Test
    public void setTest() throws Exception
    {
        while (!manager.isComplete())
        {
            manager.update(FontManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            manager.set(key, manager.get(Key.Test3));
        }
        
        for (Key key : Key.values())
        {
            manager.set(key, null);
        }
    }
}