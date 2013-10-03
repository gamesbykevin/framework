package com.gamesbykevin.framework.resources;

import java.awt.Font;
import java.util.HashMap;

/**
 * This class will contain a collection of audio files
 * @author GOD
 */
public class FontManager extends ResourceManager implements IResourceManager
{
    //all resources will be contained in this hash map
    private final HashMap<Object, Font> resources;
    
    public FontManager(final String locationFormat, final Object[] keys)
    {
        super(locationFormat, keys);
        
        //create empty list of resources
        resources = new HashMap<>();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        for (Object key : resources.keySet())
        {
            resources.put(key, null);
        }
        
        resources.clear();
    }
    
    @Override
    public void update(final Class source) throws Exception
    {
        final HashMap<Object, String> tmp = super.getLocations();
        
        for (Object key : tmp.keySet())
        {
            if (resources.get(key) == null)
            {
                //load resource
                resources.put(key, Font.createFont(Font.TRUETYPE_FONT, source.getResource(tmp.get(key)).openStream()));
                
                //increase progress
                super.increase();
                
                //we are only loading one resource at a time
                return;
            }
        }
        
        //since we made it through all resources mark progress as complete
        super.setComplete();
    }
    
    public Font get(final Object key)
    {
        return resources.get(key);
    }
}