package com.gamesbykevin.framework.resources;

import java.util.HashMap;

/**
 * This class will contain a collection of text files
 * @author GOD
 */
public class TextManager extends ResourceManager implements IResourceManager
{
    //all resources will be contained in this hash map
    private final HashMap<Object, Text> resources;
    
    public TextManager(final String locationFormat, final Object[] keys)
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
                try
                {
                    //load resource
                    resources.put(key, new Text(source, tmp.get(key)));

                    //increase progress
                    super.increase();
                }
                catch(Exception e)
                {
                    //notify which resource file has issues loading
                    throw new Exception("Error loading resource path = \"" + tmp.get(key) + "\"");
                }
                
                //we are only loading one resource at a time
                return;
            }
        }
        
        //since we made it through all resources mark progress as complete
        super.setComplete();
    }
    
    public Text get(final Object key)
    {
        return resources.get(key);
    }
}