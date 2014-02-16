package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.Disposable;
/**
 * Each menu layer can contain a number of Options and each Option can have a number of option selections
 * @author GOD
 */
public final class Selection implements Disposable
{
    //text to display for selection
    private String description;
    
    //the value of the selection
    private String value;
    
    public Selection(final String value, final String description)
    {
        this.value = value;
        this.description = description;
    }
 
    /**
     * Get the value of the selection
     * @return 
     */
    public String getValue()
    {
        return this.value;
    }
    
    /**
     * Get the text that is displayed for this option selection
     * @return String
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        description = null;
        value = null;
    }
}