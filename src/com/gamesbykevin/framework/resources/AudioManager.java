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
    
    //list of resources
    private HashMap<Object, Audio> resources;
    
    //the name of the node where the resources are
    private static final String NODE_NAME = "sound";
    
    /**
     * Create new Audio manager that will contain a collection of audio files
     * @param xmlConfigurationLocation The location of the xml configuration file
     * @throws Exception
     */
    public AudioManager(final String xmlConfigurationLocation) throws Exception
    {
        this(xmlConfigurationLocation, NODE_NAME);
    }
    
    /**
     * Create new Audio manager that will contain a collection of audio files
     * @param xmlConfigurationLocation The location of the xml configuration file
     * @param nodeName The node in the xml file containing the location of the audio files
     * @throws Exception 
     */
    public AudioManager(final String xmlConfigurationLocation, final String nodeName) throws Exception
    {
        //call to parent constructor
        super(xmlConfigurationLocation, nodeName);
        
        //create a new list that will contain the resources
        this.resources = new HashMap<>();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        for (Audio audio : resources.values())
        {
            if (audio != null)
            {
                audio.dispose();
                audio = null;
            }
        }
        
        this.resources.clear();
        this.resources = null;
    }
    
    /**
     * Load the next resource
     * @param source Class where resource is located
     * @throws Exception 
     */
    @Override
    public void update(final Class source) throws Exception
    {
        //if there are no resource locations load them from xml file
        if (getLocations().isEmpty())
        {
            //load configuration file
            loadXmlConfiguration(source);
        }
        
        //check each file location
        for (Object key : getLocations().keySet())
        {
            //check if current resource is loaded
            if (get(key) == null)
            {
                try
                {
                    //load resource
                    this.resources.put(key, new Audio(source, getLocation(key)));
                
                    //increase progress
                    super.increase();
                }
                catch(Exception e)
                {
                    //notify which resource file has issues loading
                    throw new Exception("Error loading resource path = \"" + getLocation(key) + "\"");
                }
                
                //we are only loading one resource at a time so exit
                return;
            }
        }
        
        //since all resources have finished mark progress as complete
        super.finish();
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
        for(Object key: resources.keySet())
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
     * Play the audio and loop if parameter is true.<br>
     * If audio is not enabled nothing will happen.
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
        return resources.get(key.toString());
    }
}