package com.gamesbykevin.framework.resources;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedHashMap;

/**
 * This class will contain a list of resources of a specific type (audio, image etc..)
 * 
 * @author GOD
 */
public class Manager extends Progress
{
    //physical file locations of resource
    private String[] locations;
    
    //array of unique keys used to identify and retrieve a resource
    private Object[] keys;
    
    //all resources of a specific type will be contained in this hash map
    private LinkedHashMap resources = new LinkedHashMap();
    
    //is audio enabled for this Manager
    private boolean audioEnabled = true;
    
    /**
     * What type of resource is this
     * Audio, Font, Image, Text (read-only)
     */
    public enum Type
    {
        Audio, Font, Image, Text
    }
    
    /**
     * AllAtOnce halts the application until all resources are loaded.
     * OnePerFrame only loads one resource per frame thus allowing us to draw the progress bar.
     */
    public enum LoadMethod
    {   
        AllAtOnce, OnePerFrame
    }
    
    private LoadMethod loadMethod;
    private Type type;
    
    public Manager(final LoadMethod loadMethod, final String[] locations, final Object[] keys, final Type type) 
    {
        super(locations.length);
        
        this.type = type;
        this.loadMethod = loadMethod;
        this.locations  = locations;
        this.keys = keys;
    }
    
    public void dispose()
    {
        keys = resources.keySet().toArray();
        
        for (Object key : keys)
        {
            switch(type)
            {
                case Audio:
                    getAudio(key).stopSound();
                    getAudio(key).dispose();
                    break;
                    
                case Image:
                    getImage(key).flush();
                    break;
                    
                case Text:
                    getText(key).dispose();
                    break;
                    
                case Font: 
                    break;
                    
                default:
                    break;
            }
            
            resources.put(key, null);
        }
        
        resources.clear();
        resources = null;
        
        loadMethod = null;
        locations = null;
        keys = null;
        type = null;
    }
    
    private boolean hasKey(final Object key)
    {
        return (resources.get(key) != null);
    }
    
    /**
     * Load the resources in this Manager
     * @param source 
     */
    public void update(final Class source) 
    {
        for (int i=0; i < keys.length; i++)
        {
            //if key does not exist load resource because we haven't already
            if (!hasKey(keys[i]))   
            {
                try
                {
                    switch(type)
                    {
                        case Audio:
                            resources.put(keys[i], new Audio(source, locations[i]));
                            break;
                            
                        case Font:
                            resources.put( keys[i], Font.getFont(source, locations[i]));
                            break;

                        case Image:
                            resources.put(keys[i], Image.getResource(source, locations[i]));
                            break;
                            
                        case Text:
                            resources.put(keys[i], new Text(source, locations[i]));
                            break;
                    }

                    increase();

                    if (loadMethod == LoadMethod.OnePerFrame)
                        return;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                if (i >= keys.length - 1)
                {
                    super.setComplete();
                }
            }
        }
        
        //resources are now loaded so we dont need these objects
        locations = null;
        keys = null;
    }
    
    public void playAudio(final Object key)
    {
        playAudio(key, false);
    }
    
    public void playAudio(final Object key, final boolean loop)
    {
        try
        {
            if (isAudioEnabled() && getAudio(key) != null)
            {
                getAudio(key).play(loop);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void stopAllAudio()
    {
        for (Object key : resources.keySet().toArray())
        {
            getAudio(key).stopSound();
        }
    }
    
    public void setAudioEnabled(final boolean audioEnabled)
    {
        this.audioEnabled = audioEnabled;
    }
    
    public boolean isAudioEnabled()
    {
        return this.audioEnabled;
    }
    
    public Audio getAudio(final Object key)
    {
        return (Audio)getResource(key);
    }
    
    public java.awt.Font getFont(final Object key)
    {
        return (java.awt.Font)getResource(key);
    }
    
    public Text getText(final Object key)
    {
        return (Text)getResource(key);
    }
    
    public java.awt.Image getImage(final Object key)
    {
        return (java.awt.Image)getResource(key);
    }
    
    private Object getResource(final Object key)
    {
        return resources.get(key);
    }
    
    /**
     * Display the Progress of loading the Resources
     * @param graphics
     * @param screen
     * @return Graphics
     */
    @Override
    public Graphics render(final Graphics graphics, final Rectangle screen)
    {
        super.render(graphics, screen);
        
        return graphics;
    }
}