package com.gamesbykevin.framework.resources;

import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * This class will contain a collection of image files
 * @author GOD
 */
public class ImageManager extends ResourceManager implements IResourceManager
{
    //the name of the node where the resources are
    private static final String NODE_NAME = "image";
    
    //list of resources
    private HashMap<Object, Image> resources;
    
    /**
     * Create new Image manager that will contain a collection of images
     * @param xmlConfigurationLocation The location of the xml configuration file
     * @throws Exception
     */
    public ImageManager(final String xmlConfigurationLocation) throws Exception
    {
        this(xmlConfigurationLocation, NODE_NAME);
    }

    public ImageManager(final String xmlConfigurationLocation, final String nodeName) throws Exception
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
        
        for (Image image : resources.values())
        {
            if (image != null)
            {
                image.flush();
                image = null;
            }
        }
        
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
                    this.resources.put(key, new ImageIcon(source.getResource(getLocation(key))).getImage());
                
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
    
    public Image get(final Object key)
    {
        return resources.get(key.toString());
    }
}