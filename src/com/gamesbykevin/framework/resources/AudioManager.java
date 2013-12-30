package com.gamesbykevin.framework.resources;

import java.util.HashMap;

/**
 * This class will contain a collection of audio files
 * @author GOD
 */
public class AudioManager extends ResourceManager implements IResourceManager
{
    //is audio enabled
    private boolean enabled = true;
    
    //all resources will be contained in this hash map
    private final HashMap<Object, Audio> resources;
    
    public AudioManager(final String locationFormat, final Object[] keys)
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
            resources.get(key).dispose();
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
            if (get(key) == null)
            {
                try
                {
                    //load resource
                    resources.put(key, new Audio(source, tmp.get(key)));

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
    
    /**
     * Set audio enabled. If the audio is not enabled when play() is called the audio will not play
     * @param enabled 
     */
    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }
    
    /**
     * Is audio enabled
     * @return boolean
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }
    
    /**
     * Stop all sound from playing
     */
    public void stopAll()
    {
        for (Object key : resources.keySet().toArray())
        {
            stop(key);
        }
    }
    
    /**
     * Stop sound from playing
     * @param key The specific sound we want to stop
     */
    public void stop(Object key)
    {
        get(key).stopSound();
    }
    
    /**
     * Play the audio once with no looping
     * @param key 
     */
    public void play(final Object key)
    {
        play(key, false);
    }
    
    /**
     * Play the audio and loop if parameter is true
     * @param key The unique key used to identify which audio to play
     * @param loop Do we want to loop the audio
     */
    public void play(final Object key, final boolean loop)
    {
        try
        {
            if (isEnabled() && get(key) != null)
            {
                get(key).play(loop);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Audio get(final Object key)
    {
        return resources.get(key);
    }
}