package com.gamesbykevin.framework.resources;

import java.awt.*;
import java.util.LinkedHashMap;

public class Resources extends Progress
{
    private String[] locations;  //physical file location of resource
    private Object[] keys;       //array of unique keys used to identify and retrieve a resource
    
    private LinkedHashMap resources = new LinkedHashMap();
    
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
     * AllAtOnce pauses the game until all resources are loaded, OnePerFrame only loads one resource per frame
     */
    public enum LoadMethod
    {   
        AllAtOnce, OnePerFrame
    }
    
    private LoadMethod loadMethod;
    private Type type;
    
    public Resources(final LoadMethod loadMethod, final String[] locations, final Object[] keys, final Type type) 
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
                    getAudio(key).stop();
                    getAudio(key).dispose();
                    break;
                    
                case Image:
                    getImage(key).flush();
                    break;
                    
                case Text:
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
    
    public void loadResources(final Class source) 
    {
        for (int i=0; i < keys.length; i++)
        {
            if (!hasKey(keys[i]))   //if key does not exist load resource
            {
                try
                {
                    switch(type)
                    {
                        case Audio:
                            resources.put(keys[i], new AudioResource(source, locations[i]));
                            break;

                        case Font:
                            resources.put( keys[i], Font.createFont(Font.TRUETYPE_FONT, source.getResource(locations[i]).openStream()) );
                            break;

                        case Image:
                            resources.put(keys[i], ImageResource.getImageResource(source, locations[i]));
                            break;
                            
                        case Text:
                            resources.put(keys[i], new TextResource(source, locations[i]));
                            break;
                    }

                    increaseProgress();

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
                if (i == keys.length - 1)
                {
                    setComplete();
                }
            }
        }
        
        //resources are now loaded so we dont need these objects
        locations = null;
        keys = null;
    }
    
    public int getCount()
    {
        return resources.size();
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
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void stopAllAudio()
    {
        keys = resources.keySet().toArray();
        
        for (Object key : keys)
        {
            getAudio(key).stop();
        }
        
        keys = null;
    }
    
    public void setAudioEnabled(final boolean audioEnabled)
    {
        this.audioEnabled = audioEnabled;
    }
    
    public boolean isAudioEnabled()
    {
        return this.audioEnabled;
    }
    
    public AudioResource getAudio(final Object key)
    {
        return (AudioResource)getResource(key);
    }
    
    public Font getFont(final Object key)
    {
        return (Font)getResource(key);
    }
    
    public TextResource getTextResource(final Object key)
    {
        return (TextResource)getResource(key);
    }
    
    public Image getImage(final Object key)
    {
        return (Image)getResource(key);
    }
    
    private Object getResource(final Object key)
    {
        return resources.get(key);
    }
}