package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.Audio;
import com.gamesbykevin.framework.resources.Disposable;

import java.awt.*;
import java.util.*;

public final class Option implements Disposable
{
    //all possible selections for this option, LinkedHashMap retains order when selections are added
    private LinkedHashMap<Object, Selection> selections;
    
    //this option "when selected" determines the next layer if value exists
    private Object keyLayer = null;
    
    //what is the current selection displayed
    private int index = 0;
    
    //where is this option located so we can detect if the mouse is located within
    private Rectangle boundary;
    
    //title of the options
    private String title = "";
    
    //actual font size, default to 0
    private float fontSize = 0.0f;
    
    //the x, y coordinates to draw the option selection
    private int drawX, drawY;
    
    //is this option the current one selected
    private boolean highlight = false;
    
    //the description of the current option selection
    private String description;
    
    //the font for our options
    private Font font;
    
    /**
     * Create new Option with the next layer set by parameter key
     * Note: since this object will determine the next layer
     * there will only be 1 option selection for this option.
     * 
     * @param keyLayer 
     */
    public Option(Object keyLayer)
    {
        this("");
        
        this.keyLayer = keyLayer;
    }
    
    /**
     * Set the title for this option. When the option is rendered the 
     * title will be displayed next to the option selection. Example if 
     * title is "Sound: " then the option will render "Sound: On" "Sound: off"
     * depending on the selection.
     * 
     * @param title 
     */
    public Option(String title)
    {
        this.title = title;
        
        this.selections = new LinkedHashMap<>(); 
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        if (selections != null)
        {
            for (Object key : selections.keySet().toArray())
            {
                getSelection(key).dispose();
                selections.put(key, null);
            }
            
            selections.clear();
            selections = null;
        }
        
        keyLayer = null;
        boundary = null;
        title = null;
    }
    
    /**
     * Set this option highlighted
     * @param highlight 
     */
    public void setHighlighted(final boolean highlight)
    {
        this.highlight = highlight;
    }
    
    /**
     * Is this option currently highlighted
     * @return boolean
     */
    public boolean hasHighlight()
    {
        return this.highlight;
    }
    
    /**
     * Set the container this option will be contained within
     * 
     * @param location 
     */
    public void setBoundary(final Rectangle boundary)
    {
        this.boundary = boundary;
    }
    
    /**
     * Get the boundary that this option is contained within.
     * @return Rectangle
     */
    public Rectangle getBoundary()
    {
        return this.boundary;
    }
    
    /**
     * Check if the point is inside the boundary.
     * This is so we can determine if the mouse
     * is inside this Option
     * 
     * @param location
     * @return boolean
     */
    /**
     * Is the location inside the boundary.<br>
     * This is to determine if the mouse is inside the option.
     * @param location Location of the mouse
     * @param offsetX additional x pixels to add to the mouse location
     * @param offsetY additional y pixels to add to the mouse location
     * @return true if the point is inside the option boundary, false otherwise
     */
    public boolean hasBoundary(final Point location, final int offsetX, final int offsetY)
    {
        if (boundary == null)
            return false;
        
        return boundary.contains(location.x - offsetX, location.y - offsetY);
    }
    
    /**
     * Add option selection to this option.
     * 
     * @param value The value of the selection
     * @param description Text to display
     */
    public void add(final String value, final String description)
    {
        //as the hash map grows the key will change
        int key = selections.size();
        
        //create new selection and add to hash map
        selections.put(key, new Selection(value, description));
        
        //if there is no description, set one by default
        if (getDescription() == null)
            setDescription();
    }
    
    /**
     * Sets the option selection according to int index
     * 
     * @param index the selection you like
     */
    public void setIndex(final int index)
    {
        this.index = index;
        
        if (this.index >= selections.size())
            this.index = 0;
        if (this.index < 0)
            this.index = 0;
        
        //just in case the index changed, set the new description
        setDescription();
    }
    
    /**
     * Get the current index of the option selected
     * @return int
     */
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Get the key for the next layer.
     * If the key is not specified null 
     * is returned.
     * 
     * @return Object
     */
    public Object getKeyLayer()
    {
        return this.keyLayer;
    }
    
    /**
     * Checks if there are option selections for this Option
     * @return boolean
     */
    public boolean hasSelection()
    {
        return (selections.size() > 0);
    }
    
    /**
     * Move to the next selection in this option
     */
    public void next()
    {
        //increment the index to move to the next selection
        setIndex(getIndex() + 1);
        
        //reset font size so we can ensure the next selection fits inside the container
        fontSize = 0.0f;
    }
    
    /**
     * Get the current option selection
     * @return OptionSelection 
     */
    private Selection getSelection()
    {
        return getSelection(getKey());
    }
    
    /**
     * Get the option selection based on the key parameter
     * @param key
     * @return OptionSelection
     */
    private Selection getSelection(Object key)
    {
        return (Selection)selections.get(key);
    }
    
    /**
     * Get the unique key for the current index in the hash map
     * @return 
     */
    private Object getKey()
    {
        return selections.keySet().toArray()[index];
    }
    
    /**
     * Set the description that will be displayed.<br>
     * This will concatenate the title (if exists) and the 
     * description of the current option selection.
     */
    private void setDescription()
    {
        this.description = this.title + getSelection().getDescription();
    }
    
    /**
     * Get the Text to draw for the current option selection.
     * @return String
     */
    private String getDescription()
    {
        return this.description;
    }
    
    /**
     * Draw current option selection
     * 
     * @param graphics Graphics object we will write to
     * @param color1 The background color if this Option is highlighted, if not then it is the text color
     * @param color2 The text color if this Option is highlighted
     * @throws Exception
     */
    public void render(final Graphics graphics, final Color color1, final Color color2)
    {
        try
        {
            if (getBoundary() == null)
                throw new Exception("The boundary needs to be set before this option can be drawn");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        //if the font size isn't set
        if (fontSize == 0)
        {
            fontSize = Menu.getFontSize(getDescription(), getBoundary().width, graphics);
            
            //set the x, y coordinates where our description will be drawn
            drawX = getBoundary().x + (int)(getBoundary().width * .03);
            drawY = getBoundary().y + graphics.getFontMetrics().getHeight();
            
            //get the desired font according to font size calculated
            graphics.getFont().deriveFont(fontSize);
        }

        if (hasHighlight())
        {
            graphics.setColor(color1);
            graphics.fillRect(getBoundary().x, getBoundary().y, getBoundary().width, getBoundary().height);
            
            graphics.setColor(color2);
            graphics.drawString(getDescription(), drawX, drawY);
        }
        else
        {
            graphics.setColor(color1);
            graphics.drawString(getDescription(), drawX, drawY);
        }
    }
}