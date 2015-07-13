package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Audio Manager unit test
 * @author GOD
 */
public class AudioManagerTest 
{
    public static final String LOCATION = "resources/audio.xml";
    
    private AudioManager manager;
    
    public enum Key
    {
        Test1, Test2
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        AudioManager manager = new AudioManager(LOCATION);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        AudioManager manager = new AudioManager(LOCATION);
        
        while(!manager.isComplete())
        {
            manager.update(AudioManagerTest.class);
        }
        
        manager.dispose();
        manager = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        manager = new AudioManager(LOCATION);
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
            manager.update(AudioManagerTest.class);
        }
    }
    
    @Test
    public void setEnabledTest()
    {
        manager.setEnabled(true);
        manager.setEnabled(false);
    }
    
    @Test
    public void isEnabledTest()
    {
        assertTrue(manager.isEnabled());
        manager.setEnabled(false);
        assertFalse(manager.isEnabled());
        manager.setEnabled(true);
        assertTrue(manager.isEnabled());
    }
    
    @Test
    public void stopAllTest()
    {
        manager.stopAll();
    }
    
    @Test
    public void stopTest() throws Exception
    {
        while (!manager.isComplete())
        {
            manager.update(AudioManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            manager.stop(key);
        }
    }
    
    @Test
    public void playTest()
    {
        for (Key key : Key.values())
        {
            manager.play(key, true);
        }
    }
    
    @Test
    public void getTest() throws Exception
    {
        while (!manager.isComplete())
        {
            manager.update(AudioManagerTest.class);
        }
        
        for (Key key : Key.values())
        {
            assertNotNull(manager.get(key));
        }
    }
}