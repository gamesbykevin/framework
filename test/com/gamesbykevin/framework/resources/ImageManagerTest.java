package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Image Manager unit test
 * @author GOD
 */
public class ImageManagerTest 
{
    private ImageManager manager;
    
    public static final String LOCATION = "resources/image.xml";
    
    public enum Key
    {
        Test1, Test2, Test3, Test4, Test5, 
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        ImageManager manager = new ImageManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(ImageManagerTest.class);
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        ImageManager manager = new ImageManager(LOCATION);
        
        while (!manager.isComplete())
        {
            manager.update(ImageManagerTest.class);
        }
        
        manager.dispose();
    }
    
    @Before
    public void setUp() throws Exception
    {
        manager = new ImageManager(LOCATION);
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
            manager.update(ImageManagerTest.class);
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
            manager.update(ImageManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            assertNotNull(manager.get(key));
        }
    }
}