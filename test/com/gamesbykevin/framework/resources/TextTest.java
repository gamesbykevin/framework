package com.gamesbykevin.framework.resources;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Text unit test
 * @author GOD
 */
public class TextTest 
{
    public static final String LOCATION = "resources/test1.txt";
    
    private Text text;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Text text = new Text(TextTest.class, LOCATION);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Text text = new Text(TextTest.class, LOCATION);
        text.dispose();
    }
    
    @Before
    public void setUp() throws Exception
    {
        text = new Text(TextTest.class, LOCATION);
    }
    
    @After
    public void tearDown() throws Exception
    {
        text.dispose();
    }
    
    @Test
    public void getLinesTest() throws Exception
    {
        assertFalse(text.getLines().isEmpty());
    }
    
    @Test
    public void getLineTest() throws Exception
    {
        final int lines = text.getLines().size();
        
        for (int line = 0; line < lines; line++)
        {
            assertNotNull(text.getLine(line));
        }
    }
}