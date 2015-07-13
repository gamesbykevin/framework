package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Text Manager unit test
 * @author GOD
 */
public class TextManagerTest 
{
    private TextManager manager;
    
    public static final String LOCATION = "resources/text.xml";
    
    public enum Key
    {
        Test1, Test2, Test3
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        TextManager manager = new TextManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(TextManagerTest.class);
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        TextManager manager = new TextManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(TextManagerTest.class);
        }
        
        manager.dispose();
    }
    
    @Before
    public void setUp() throws Exception
    {
        manager = new TextManager(LOCATION);
    }
    
    @After
    public void tearDown() throws Exception
    {
        manager.dispose();
    }
    
    @Test
    public void updateTest() throws Exception
    {
        while (!manager.isComplete())
        {
            manager.update(TextManagerTest.class);
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
            manager.update(TextManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            assertNotNull(manager.get(key));
        }
    }
}