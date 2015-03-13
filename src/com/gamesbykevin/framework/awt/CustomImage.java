package com.gamesbykevin.framework.awt;

import com.gamesbykevin.framework.base.Sprite;
import com.gamesbykevin.framework.resources.Disposable;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class will provide a user friendly way to create an image
 * @author GOD
 */
public abstract class CustomImage extends Sprite implements Disposable
{
    //buffered image to create our custom image
    private BufferedImage bufferedImage;
    
    //graphics to draw the buffered image
    private Graphics2D graphics2d;
    
    //color with 100% transparency so you can't see it
    public static final Color TRANSPARENT_COLOR = new Color(0,0,0,0);
    
    protected CustomImage(final int width, final int height)
    {
        //store the dimensions
        super.setWidth(width);
        super.setHeight(height);
        
        //create buffered image with transparency
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        //get graphics object to draw this image
        this.graphics2d = this.bufferedImage.createGraphics();
        
        //set transparent background by default
        setBackground(TRANSPARENT_COLOR);
    }
    
    /**
     * Assign the font to the graphics object responsible for rendering the image
     * @param font The font we want to use
     */
    public void setFont(final Font font)
    {
        getGraphics2D().setFont(font);
    }
    
    /**
     * Get the graphics object for the buffered image
     * @return Object used to draw image
     */
    public Graphics2D getGraphics2D()
    {
        return this.graphics2d;
    }
    
    /**
     * Assign the background color of the image
     * @param color The color for the background of the image
     */
    public final void setBackground(final Color color)
    {
        getGraphics2D().setBackground(color);
    }
    
    /**
     * Get the buffered image
     * @return The custom image we have created
     */
    public BufferedImage getBufferedImage()
    {
        return this.bufferedImage;
    }
    
    /**
     * Clear the image by filling all pixels with the background color set.<br>
     * A default transparent color will be used if none are set.
     */
    public void clear()
    {
        getGraphics2D().clearRect(0, 0, (int)getWidth(), (int)getHeight());
    }
    
    /**
     * Each child class will need to implement their own method to draw the image
     * @throws Exception if issue during render
     */
    protected abstract void renderImage() throws Exception;
    
    @Override
    public void dispose()
    {
        if (graphics2d != null)
        {
            graphics2d.dispose();
            graphics2d = null;
        }
        
        if (bufferedImage != null)
        {
            bufferedImage.flush();
            bufferedImage = null;
        }
    }
}