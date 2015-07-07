package com.gamesbykevin.framework.resources;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class will contain a list of resources of a specific type (audio, image, etc..)
 * @author GOD
 */
public abstract class ResourceManager implements IResourceManager
{
    //our object used to track progress and display to the user
    private Progress progress;
    
    //the location of the xml file and the node name we want to load
    private final String xmlConfigurationLocation, nodeName;
    
    //list of the file locations
    private HashMap<Object, String> locations;
    
    protected ResourceManager(final String xmlConfigurationLocation, final String nodeName) throws Exception
    {
        this.xmlConfigurationLocation = xmlConfigurationLocation;
        this.nodeName = nodeName;
        
        //create new list of locations for the resources
        this.locations = new HashMap<>();
        
        //create new progress tracker
        this.progress = new Progress(1);
    }
    
    /**
     * Populate the resource list with the information contained in the xml file
     * @param source The Class where xml file is located
     * @throws Exception 
     */
    protected void loadXmlConfiguration(final Class source) throws Exception
    {
        //objects used to read xml file
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        //create, load, and parse xml file
        Document doc = dBuilder.parse(source.getResourceAsStream(this.xmlConfigurationLocation));

        //recommended
        doc.getDocumentElement().normalize();
        
        //get a list of the images in the xml file
        NodeList nodeList = doc.getElementsByTagName(nodeName);
        
        for (int temp = 0; temp < nodeList.getLength(); temp++) 
        {
            Node node = nodeList.item(temp);

            //we only want the element nodes
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            //get element node
            Element element = (Element)node;
            
            //what is the unique id so we can access this resource
            final String objectId = element.getAttribute("id");
            
            //what is the location of the resource
            final String location = element.getTextContent();
            
            //make sure duplicate ids don't exist
            if (getLocation(objectId) != null)
            {
                throw new Exception("Duplicate id has been found id \"" + objectId + "\"");
            }
            
            //add file location of resource to list
            add(objectId, location);
        }
        
        //set the new goal to the current size of the hashmap
        this.progress.changeGoal(locations.size());
    }
    
    /**
     * Check the existing locations against the provided array of objects.<br>
     * If any of the objects in the array are not found in the resource location list an exception will be thrown.
     * @param objects Array of keys used to access the resources
     * @throws Exception will be thrown if any of the array objects do not exist in the resource location list.
     */
    public void verifyLocations(final Object[] objects) throws Exception
    {
        for (Object object : objects)
        {
            if (this.getLocation(object.toString()) == null)
                throw new Exception("Verify failed! Key \"" + object.toString() + "\" missing from xml configuration file.");
        }
    }

    /**
     * Add file resource location to list
     * @param objectId The unique object so we can access the location
     * @param location The file path of the resource
     */
    protected void add(final Object objectId, final String location)
    {
        this.locations.put(objectId, location);
    }
    
    /**
     * Get the file location of the specified resource
     * @param objectId Unique key of the file location we want
     * @return The file location, if the object is not found null is returned
     */
    protected String getLocation(final Object objectId)
    {
        return this.locations.get(objectId);
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
        if (this.locations != null)
        {
            this.locations.clear();
            this.locations = null;
        }
        
        if (this.progress != null)
        {
            this.progress.dispose();
            this.progress = null;
        }
    }
    
    /**
     * Is loading all resources complete?
     * @return true if the progress is complete, false otherwise
     */
    @Override
    public boolean isComplete()
    {
        //if the progress isn't complete we are still loading
        return (progress.isComplete());
    }
    
    /**
     * Increase the progress
     */
    protected void increase()
    {
        this.progress.increase();
    }
    
    /**
     * Mark the progress as finished
     */
    protected void finish()
    {
        this.progress.setComplete();
    }
    
    protected void setProgressDescription(final String description)
    {
        try
        {
            this.progress.setDescription(description);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
        //assign the progress window
        progress.setScreen(screen);
        
        //draw our progress withing the specified window
        progress.render(graphics);
    }
}