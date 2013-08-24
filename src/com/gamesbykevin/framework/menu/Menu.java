package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.Keyboard;
import com.gamesbykevin.framework.input.Mouse;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.util.LinkedHashMap;

public class Menu 
{
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    private LinkedHashMap<Object, Layer> layers;
    
    //screen menu will be drawn within
    private Rectangle screen;
    
    //default font size
    public static final float MAX_FONT_SIZE = 48.0f;
    
    //the curent key so we know what the current layer is
    private Object current;
    
    //the key that indicates the last layer of this menu
    private Object finish;
    
    /**
     * Create a new Menu that is to be displayed within Rectangle screen
     * @param screen Rectangle that is the container for the menu
     */
    public Menu(final Rectangle screen) 
    {
        this.screen = screen;
        
        this.layers = new LinkedHashMap<>();
    }
    
    /**
     * Free up resources
     */
    public void dispose()
    {
        //loop through every key available
        for (Object key : layers.keySet().toArray())
        {
            //recycle layer elements
            getLayer(key).dispose();
            layers.put(key, null);
        }
        
        layers.clear();
        layers = null;
        
        current = null;
        finish = null;
        screen = null;
    }
    
    /**
     * Add the Layer to this menu with the assigned key
     * 
     * @param key
     * @param Layer 
     */
    protected void add(Object key, Layer layer)
    {
        layers.put(key, layer);
    }
    
    /**
     * Check if the parameter key is the current Layer
     * @param key
     * @return boolean
     */
    protected boolean hasCurrent(final Object key)
    {
        return (this.current == key);
    }
    
    private Object getCurrent()
    {
        return this.current;
    }
    
    /**
     * Reset the timer of the current Layer
     */
    public void reset()
    {
        getLayer().reset();
    }
    
    /**
     * Set the last layer so we will know when the menu is finished
     * @param key Unique identifier for the specific layer
     */
    //lets us know which layer is the last
    public void setFinish(final Object key)
    {
        this.finish = key;
    }
    
    private Object getFinish()
    {
        return this.finish;
    }
    
    /**
     * Sets the current Layer and resets the Timer for that Layer
     * @param current 
     */
    public void setLayer(final Object current)
    {
        this.current = current;
        
        this.reset();
    }
    
    private Layer getLayer(final Object key)
    {
        return layers.get(key);
    }
    
    //gets the current Layer
    private Layer getLayer()
    {   
        return getLayer(getKey());
    }
    
    /**
     * Gets the current key
     * @return Object
     */
    public Object getKey()
    {   
        return current;
    }
    
    //this will get the index of a specific selection for a specific option for a specific layer
    /**
     * 
     * @param layer  The Layer key
     * @param option The option key 
     * @return 
     */
    public int getOptionSelectionIndex(final Object layer, final Object option)
    {   
        return getLayer(layer).getOption(option).getIndex();
    }
    
    //if no layers have been added the menu is not setup
    /**
     * Has this Menu been setup yet?
     * If this Menu has at least 1 Layer it has been setup.
     * 
     * @return boolean
     */
    public boolean isSetup()
    {   
        return (!layers.isEmpty());
    }
    
    /**
     * If the current Layer is the Layer marked as the last
     * @return boolean
     */
    public boolean hasFinished()
    {
        return (getCurrent() == getFinish());
    }
    
    /**
     * Update the current Layer in this Menu
     * @param mouse Object containing any mouse input/events
     * @param keyboard Object containing any keyboard input/events
     * @param time The time deduction per each update
     */
    public void update(final Mouse mouse, final Keyboard keyboard, final long time) 
    {
        //if the menu has finished we will not update the current layer
        if (hasFinished())
            return;
        
        getLayer().update(this, mouse, keyboard, screen, time);
    }
    
    /**
     * Get the appropriate font size with the given text and limited container width.
     * Use this method if you have a container and you want the text to fit inside 
     * the width of the container.
     * 
     * @param text The text we want to fit inside the container
     * @param containerWidth The max width for the text to fit
     * @param graphics Graphics
     * @return float the appropriate font size
     */
    public static float getFontSize(final String text, final int containerWidth, final Graphics graphics)
    {
        //store the temporary font while adjustments are made
        Font tmp = graphics.getFont();
        
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
        
        //set the font back to the original
        graphics.setFont(tmp);
        
        return fontSize;
    }
    
    /**
     * Draw the Menu as long as it is not finished
     * @param graphics
     * @return Graphics 
     * @throws Exception 
     */
    public Graphics render(Graphics graphics) throws Exception
    {
        //if the menu has finished we will not draw it
        if (hasFinished())
            return graphics;
        
        getLayer().render((Graphics2D)graphics, screen);
        
        return graphics;
    }
}