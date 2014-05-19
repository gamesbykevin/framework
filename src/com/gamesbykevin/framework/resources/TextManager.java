package com.gamesbykevin.framework.resources;

import java.util.HashMap;

/**
 * This class will contain a collection of text files
 * @author GOD
 */
public class TextManager extends ResourceManager implements IResourceManager
{
    //the name of the node where the resources are
    private static final String NODE_NAME = "textFile";
    
    //list of resources
    private HashMap<Object, Text> resources;
    
    /**
     * Create new text manager that will contain a collection of text files
     * @param xmlConfigurationLocation The location of the xml configuration file
     * @throws Exception
     */
    public TextManager(final String xmlConfigurationLocation) throws Exception
    {
        this(xmlConfigurationLocation, NODE_NAME);
    }
    
    public TextManager(final String xmlConfigurationLocation, final String nodeName) throws Exception
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
        
        for (Text text : resources.values())
        {
            if (text != null)
            {
                text.dispose();
                text = null;
            }
        }
        
        resources.clear();
        resources = null;
    }
    
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
                    this.resources.put(key, new Text(source, getLocation(key)));
                
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
    
    public Text get(final Object key)
    {
        return resources.get(key.toString());
    }
}