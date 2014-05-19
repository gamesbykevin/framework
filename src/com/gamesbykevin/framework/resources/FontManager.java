package com.gamesbykevin.framework.resources;

import java.awt.Font;
import java.util.HashMap;

/**
 * This class will contain a collection of audio files
 * @author GOD
 */
public class FontManager extends ResourceManager implements IResourceManager
{
    //the name of the node where the resources are
    private static final String NODE_NAME = "font";
    
    //list of resources
    private HashMap<Object, Font> resources;
    
    /**
     * Create new font manager that will contain a collection of font files
     * @param xmlConfigurationLocation The location of the xml configuration file
     * @throws Exception
     */
    public FontManager(final String xmlConfigurationLocation) throws Exception
    {
        this(xmlConfigurationLocation, NODE_NAME);
    }
    
    public FontManager(final String xmlConfigurationLocation, final String nodeName) throws Exception
    {
        //call to parent constructor
        super(xmlConfigurationLocation, nodeName);
        
        //create new list of resources
        this.resources = new HashMap<>();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        resources.clear();
        resources = null;
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
                    this.resources.put(key, Font.createFont(Font.TRUETYPE_FONT, source.getResource(getLocation(key)).openStream()));
                
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
    
    public Font get(final Object key)
    {
        return resources.get(key.toString());
    }
    
    /**
     * Add/Update Font<br>
     * If the key is found the existing Font will be updated, else it will be added.
     * @param key The unique key to identify the Font
     * @param font The Font object
     */
    public void set(final Object key, final Font font)
    {
        resources.put(key.toString(), font);
    }
}