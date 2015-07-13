package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Audio unit test
 * @author GOD
 */
public class AudioTest 
{
    public static final String LOCATION_1 = "resources/test.wav";
    public static final String LOCATION_2 = "resources/test.mp3";
    
    private Audio audio1, audio2;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Audio audio;
        audio = new Audio(AudioTest.class, LOCATION_1);
        audio = new Audio(AudioTest.class, LOCATION_2);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Audio audio;
        audio = new Audio(AudioTest.class, LOCATION_1);
        audio.dispose();
        audio = null;
        
        audio = new Audio(AudioTest.class, LOCATION_2);
        audio.dispose();
        audio = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        audio1 = new Audio(AudioTest.class, LOCATION_1);
        audio2 = new Audio(AudioTest.class, LOCATION_2);
    }
    
    @After
    public void tearDown() throws Exception
    {
        audio1.stopSound();
        audio1.dispose();
        audio1 = null;
        
        audio2.stopSound();
        audio2.dispose();
        audio2 = null;
    }
    
    @Test
    public void playTest() throws Exception
    {
        audio1.play(true);
        audio2.play(true);
    }
    
    @Test
    public void stopSoundTest() throws Exception
    {
        audio1.stopSound();
        audio2.stopSound();
    }
}