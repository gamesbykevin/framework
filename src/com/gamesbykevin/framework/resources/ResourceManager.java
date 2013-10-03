package com.gamesbykevin.framework.resources;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * This class will contain a list of resources of a specific type (audio, image etc..)
 * 
 * @author GOD
 */
public abstract class ResourceManager extends Progress implements IResourceManager
{
    //list of file paths
    private final HashMap<Object, String> locations;
    
    /**
     * 
     * @param locationFormat The file path directory format for each resource. Example "audio/menu/sound/{0}.wav"
     * @param keys List of unique identifiers for access to each individual resource
     */
    public ResourceManager(final String locationFormat, final Object[] keys)
    {
        //set the progress goal to the length of the array
        super(keys.length);
        
        //create new list of locations for the resources
        this.locations = new HashMap<>();
        
        for (int i=0; i < keys.length; i++)
        {
            //add each file path to the list for each resource
            this.locations.put(keys[i], MessageFormat.format(locationFormat, i));
        }
    }
    
    /**
     * Get all of the file resource locations
     * @return HashMap containing the unique key and file path
     */
    protected HashMap getLocations()
    {
        return this.locations;
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        this.locations.clear();
    }
    
    @Override
    public boolean isLoading()
    {
        //if the progress isn't complete we are still loading
        return (!super.isComplete());
    }
    
    /**
     * Display the Progress of loading the Resources
     * @param graphics
     * @param screen
     * @return Graphics
     */
    @Override
    public void render(final Graphics graphics, final Rectangle screen)
    {
        super.render(graphics, screen);
    }
}