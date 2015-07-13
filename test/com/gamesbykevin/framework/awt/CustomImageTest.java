package com.gamesbykevin.framework.awt;

import com.gamesbykevin.framework.awt.CustomImage;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for CustomImage
 * @author GOD
 */
public class CustomImageTest 
{
    public static final BufferedImage TEST_IMAGE = new BufferedImage(2000,2000, BufferedImage.TYPE_INT_ARGB);
    
    private class MyImage extends CustomImage
    {
        protected MyImage(final int width, final int height)
        {
            super(width, height);
        }
        
        @Override
        public void render()
        {
            
        }
    }
    
    private MyImage image;
    
    @BeforeClass
    public static void setUpClass() 
    {
        Color color = new Color(0, 0, 0, 0);
        
        //keep the transparent color this value
        assertTrue(CustomImage.TRANSPARENT_COLOR.getRGB() == color.getRGB());
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp()
    {
        final int width = 100;
        final int height = 75;
        
        image = new MyImage(width, height);
        
        //assume the dimensions are set
        assertTrue(image.getWidth() == width);
        assertTrue(image.getHeight() == height);
        
        //assume object is not null
        assertNotNull(image.getGraphics2D());
        
        //assume object is not null
        assertNotNull(image.getBufferedImage());
        
        //when the image is first created the background color will be transparent
        assertTrue(image.getGraphics2D().getBackground().getRGB() == CustomImage.TRANSPARENT_COLOR.getRGB());
    }
    
    @After
    public void tearDown()
    {
        image.dispose();
        
        //assume object is null
        assertNull(image.getGraphics2D());
        
        //assume object is null
        assertNull(image.getBufferedImage());
        
        image = null;
    }
    
    @Test
    public void hello()
    {
        
    }
}