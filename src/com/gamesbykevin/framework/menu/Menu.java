package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.Keyboard;
import com.gamesbykevin.framework.input.Mouse;
import com.gamesbykevin.framework.resources.Audio;
import com.gamesbykevin.framework.resources.Disposable;
import com.gamesbykevin.framework.resources.Sound;
import com.gamesbykevin.framework.util.Timer;
import com.gamesbykevin.framework.util.Timers;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Menu implements Disposable, Sound
{
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    private LinkedHashMap<Object, Layer> layers;
    
    //screen menu will be drawn within
    private Rectangle screen;
    
    //default font size
    public static final float MAX_FONT_SIZE = 48.0f;
    
    //the curent key so we know what the current layer is
    private String current;
    
    //the key that indicates the last layer of this menu
    private String finish;
    
    //is the audio enabled
    private boolean enabled = true;
    
    /**
     * Create a new Menu that is to be displayed within Rectangle screen
     * @param screen Rectangle that is the container for the menu
     * @param fileLocation Where our xml file is
     * @source The class where the resources are found at
     */
    public Menu(final Rectangle screen, final String fileLocation, final Class source) throws Exception
    {
        this.screen = screen;
        
        this.layers = new LinkedHashMap<>();
        
        //create the layers/options from xml file
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        //document object
        Document doc = null;
        
        try
        {
            //load and parse xml file
            doc = dBuilder.parse(source.getResourceAsStream(fileLocation));
        }
        catch (Exception e)
        {
            //display custom message
            System.out.println("Error loading xml: " + fileLocation);

            //print stack trace
            e.printStackTrace();
        }

        //optional, but recommended
        doc.getDocumentElement().normalize();
        
        //get the layers in our xml
        NodeList layerNodeList = doc.getElementsByTagName("layer");
        
        //our layer object
        Layer layer;
        
        //our option object
        Option option;
        
        for (int temp = 0; temp < layerNodeList.getLength(); temp++) 
        {
            Node layerNode = layerNodeList.item(temp);

            if (layerNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element layerElement = (Element)layerNode;

            //the layer configuration
            String transition = "", title = "", layerId, nextLayerId; 
            boolean force = false, pause = false;
            long duration = 0;
            float ratio = 1;
            float borderStroke = 10.0f;
            
            //layer configurations for the resources
            String backgroundImageLocation, backgroundMusicLocation, optionSoundLocation;
            Image backgroundImage = null;
            Audio backgroundMusic = null, optionSound = null;
            
            //what is the unique name of the Layer
            layerId = layerElement.getAttribute("id");
            
            //what is the next Layer
            nextLayerId = layerElement.getAttribute("next");
            
            if (layerElement.getElementsByTagName("transition").getLength() > 0)
                transition = layerElement.getElementsByTagName("transition").item(0).getTextContent();
            
            if (layerElement.getElementsByTagName("force").getLength() > 0)
                force = Boolean.parseBoolean(layerElement.getElementsByTagName("force").item(0).getTextContent());
            
            if (layerElement.getElementsByTagName("pause").getLength() > 0)
                pause = Boolean.parseBoolean(layerElement.getElementsByTagName("pause").item(0).getTextContent());
            
            if (layerElement.getElementsByTagName("duration").getLength() > 0 && layerElement.getElementsByTagName("duration").item(0).getTextContent().length() > 0)
                duration = Long.parseLong(layerElement.getElementsByTagName("duration").item(0).getTextContent());
            
            if (layerElement.getElementsByTagName("image").getLength() > 0)
            {
                backgroundImageLocation = layerElement.getElementsByTagName("image").item(0).getTextContent();
                
                if (backgroundImageLocation.trim().length() > 0)
                {
                    try
                    {
                        backgroundImage = new ImageIcon(source.getResource(backgroundImageLocation)).getImage();
                    }
                    catch (Exception e)
                    {
                        //display custom message
                        System.out.println("Error loading image: " + backgroundImageLocation);
                        
                        //print stack trace
                        e.printStackTrace();
                    }
                }
            }
            
            if (layerElement.getElementsByTagName("backgroundMusicLocation").getLength() > 0)
            {
                backgroundMusicLocation = layerElement.getElementsByTagName("backgroundMusicLocation").item(0).getTextContent();
                
                if (backgroundMusicLocation.trim().length() > 0)
                {
                    try
                    {
                        backgroundMusic = new Audio(source, backgroundMusicLocation);
                    }
                    catch (Exception e)
                    {
                        //display custom message
                        System.out.println("Error loading audio: " + backgroundMusicLocation);
                        
                        //print stack trace
                        e.printStackTrace();
                    }
                }
            }

            if (layerElement.getElementsByTagName("optionSoundLocation").getLength() > 0)
            {
                optionSoundLocation = layerElement.getElementsByTagName("optionSoundLocation").item(0).getTextContent();
                
                if (optionSoundLocation.trim().length() > 0)
                {
                    try
                    {
                        optionSound = new Audio(source, optionSoundLocation);
                    }
                    catch (Exception e)
                    {
                        //display custom message
                        System.out.println("Error loading audio: " + optionSoundLocation);
                        
                        //print stack trace
                        e.printStackTrace();
                    }
                }
            }
            
            //the layer can only have a title if there are options
            if (layerElement.getElementsByTagName("title").getLength() > 0)
                title = layerElement.getElementsByTagName("title").item(0).getTextContent();

            //the option container will only exist if there are options
            if (layerElement.getElementsByTagName("optionContainerRatio").getLength() > 0)
                ratio = Float.parseFloat(layerElement.getElementsByTagName("optionContainerRatio").item(0).getTextContent());

            //the option container will only exist if there are options
            if (layerElement.getElementsByTagName("optionBorderThickness").getLength() > 0)
                borderStroke = Float.parseFloat(layerElement.getElementsByTagName("optionBorderThickness").item(0).getTextContent());
            
            //determine the transisition type
            Layer.Type transitionType = Layer.Type.NONE;
            
            //make sure we have transition before we attempt to match
            if (transition.trim().length() > 0)
            {
                //figure out which transition 
                for (Layer.Type type : Layer.Type.values())
                {
                    if (type.toString().equalsIgnoreCase(transition))
                    {
                        transitionType = type;
                        break;
                    }
                }
            }
            
            //create new layer
            layer = new Layer(transitionType, screen);
            
            //do we force the user to view the layer
            layer.setForce(force);
            
            //do we pause the layer
            layer.setPause(pause);
            
            //set the next layer we go to
            if (nextLayerId.trim().length() > 0)
                layer.setNextLayer(nextLayerId);
        
            //set the timer
            layer.setTimer(new Timer(Timers.toNanoSeconds(duration)));
            
            //set the option container ratio
            layer.setOptionContainerRatio(ratio);
            
            //set how thick the border line will be
            layer.setOptionContainerBorderThickness(borderStroke);
            
            //set the tile
            layer.setTitle(title);
            
            //set the background image
            layer.setImage(backgroundImage);
            
            //set the background music
            layer.setSound(backgroundMusic);
            
            //set the option change sound
            layer.setOptionSound(optionSound);
            
            //do we have options
            if (layerElement.getElementsByTagName("options").getLength() > 0)
            {
                //list of all options
                NodeList optionsList = layerElement.getChildNodes();

                //check all of the options
                for (int i=0; i < optionsList.getLength(); i++)
                {
                    Node optionNode = optionsList.item(i);

                    if (optionNode.getNodeType() != Node.ELEMENT_NODE)
                        continue;

                    Element optionElement = (Element)optionNode;

                    //if the name attribute does not exist skip this node
                    if (optionElement.getAttribute("name").trim().length() < 1)
                        continue;

                    //the display name of the option
                    String optionTitle = optionElement.getAttribute("name");

                    //the unique id of the option
                    String optionId = optionElement.getAttribute("id");

                    //the next layer we are supposed to go to
                    String next = optionElement.getAttribute("next");
                    
                    //list of option selections
                    NodeList optionSelectionList = optionElement.getChildNodes();

                    if (next != null && next.trim().length() > 0)
                    {
                        //create option with the next layer set
                        option = new Option((Object)next);
                        
                        //in this case there will only be 1 selection
                        option.add(optionTitle, optionTitle);
                    }
                    else
                    {
                        option = new Option(optionTitle);
                        
                        //check all of the option selections
                        for (int x=0; x < optionSelectionList.getLength(); x++)
                        {
                            Node optionSelectionNode = optionSelectionList.item(x);

                            if (optionSelectionNode.getNodeType() != Node.ELEMENT_NODE)
                                continue;

                            Element optionSelectionElement = (Element)optionSelectionNode;

                            String value = optionSelectionElement.getAttribute("value");
                            String desc = optionSelectionElement.getTextContent();

                            option.add(value, desc);
                        }
                    }
                    
                    //if option already exists throw exception
                    if (layer.getOption(optionId) != null)
                    {
                        throw new Exception("Each option needs to have a unique id. Option Id : (" + optionId + ")");
                    }
                    else
                    {
                        //add option to the layer
                        layer.add(optionId, option);
                    }
                }
            }
            
            //check for duplicate ids
            if (hasLayer(layerId))
            {
                throw new Exception("Each layer needs to have a unique id. Layer Id : (" + layerId + ")");
            }
            else
            {
                //add the layer to the menu
                add(layerId, layer);
            }
        }
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        //loop through every key available
        for (Object key : layers.keySet().toArray())
        {
            try
            {
                //recycle layer elements
                getLayer(key).dispose();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            layers.put(key, null);
        }
        
        layers.clear();
        layers = null;
        
        current = null;
        finish = null;
        screen = null;
    }
    
    @Override
    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
        
        //set the sound enabled/disabled for the layers as well
        for (int i = 0; i < layers.size(); i++)
        {
            try
            {
                getLayer(layers.keySet().toArray()[i]).setEnabled(this.enabled);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }
    
    /**
     * Add the Layer to this menu with the assigned key
     * 
     * @param key
     * @param Layer 
     */
    protected final void add(final String key, final Layer layer)
    {
        layers.put(key, layer);
    }
    
    /**
     * Check if the parameter key is the current Layer
     * @param key
     * @return boolean
     */
    protected boolean hasCurrent(final Object key)
    {
        return getCurrent().equalsIgnoreCase(key.toString());
    }
    
    private String getCurrent()
    {
        return this.current;
    }
    
    /**
     * Reset the timer of the current Layer
     */
    public void reset() throws Exception
    {
        getLayer().reset();
    }
    
    /**
     * Set the last layer so we will know when the menu is finished
     * @param key Unique identifier for the specific layer
     */
    //lets us know which layer is the last
    public void setFinish(final Object key)
    {
        this.finish = key.toString();
    }
    
    private String getFinish()
    {
        return this.finish;
    }
    
    /**
     * Sets the current Layer and resets the Timer for that Layer
     * @param key The Layer we want to be the current
     * @throws Exception If the Layer is not found using the parameter key an Exception will be thrown
     */
    public void setLayer(final Object key) throws Exception
    {
        //make sure the current layer exists before we check if the audio exists
        if (this.current != null)
        {
            //if the current layer has background audio, stop it before going to the next layer
            if (getLayer(this.current).getSound() != null)
                getLayer(this.current).getSound().stopSound();
        }
        
        //store the next layer
        this.current = key.toString();
        
        //is audio enabled
        if (isEnabled())
        {
            //if the new layer has background audio, play it on infinite loop
            if (getLayer(this.current).getSound() != null)
                getLayer(this.current).getSound().play(true);
        }
        
        this.reset();
    }
    
    /**
     * Does the Layer exist
     * @param key The unique identifier to get the Layer
     * @return true if the Layer exists, false otherwise
     */
    protected final boolean hasLayer(final Object key)
    {
        return (layers.get(key) != null);
    }
    
    /**
     * Get the Layer with the specified key.
     * @param key The unique identifier to get the Layer
     * @return Layer The desired layer
     * @throws Exception If the Layer is not found with the specified key an Exception will be thrown
     */
    public Layer getLayer(final String key) throws Exception
    {
        if (!hasLayer(key))
        {
            throw new Exception("Layer not found with key = " + key.toString());
        }
        else
        {
            return layers.get(key.toString());
        }
    }
    
    /**
     * Get the Layer
     * @param key The key identifying the layer we want
     * @return The specified layer in the menu
     * @throws Exception 
     */
    public Layer getLayer(final Object key) throws Exception
    {
        return getLayer(key.toString());
    }
    
    /**
     * Get the current layer
     * @return The current layer in the menu
     * @throws Exception Will be thrown if layer is not found
     */
    public Layer getLayer() throws Exception
    {   
        return getLayer(getKey());
    }
    
    /**
     * Gets the current key
     * @return Object
     */
    public String getKey()
    {   
        return current.toString();
    }
    
    /**
     * Does the specified option exist in the specified layer
     * @param layer
     * @param option
     * @return true if exists, false otherwise
     */
    public boolean hasOption(final Object layer, final Object option) throws Exception
    {
        return (getOption(layer, option) != null);
    }
    
    /**
     * Get the option.
     * @param layer The layer where we want to check
     * @param option The id of the option we want
     * @return The option found at the specified layer. If not found null is returned
     * @throws Exception 
     */
    public Option getOption(final Object layer, final Object option) throws Exception
    {
        return getLayer(layer).getOption(option);
    }
    
    /**
     * This will get the index of a specific selection for a specific option for a specific layer
     * @param layer  
     * @param option 
     * @return int
     */
    public int getOptionSelectionIndex(final Object layer, final Object option) throws Exception
    {   
        return getLayer(layer).getOption(option).getIndex();
    }
    
    /**
     * This will set the selection of all Options with the assigned Option key
     * 
     * @param option The option key 
     * @param index The value we want set
     */
    public void setOptionSelectionIndex(final Object option, final int index) throws Exception
    {
        for (Object key : layers.keySet().toArray())
        {
            //if Option exists for the current Layer
            if (getLayer(key).getOption(option) != null)
            {
                //set the value
                getLayer(key).getOption(option).setIndex(index);
            }
        }
    }
    
    //if no layers have been added the menu is not setup
    /**
     * Has this Menu been setup yet?
     * If this Menu has at least 1 Layer it has been setup.
     * 
     * @return boolean
     */
    public boolean isSetup()
    {   
        return (!layers.isEmpty());
    }
    
    /**
     * If the current Layer is the Layer marked as the last
     * @return boolean
     */
    public boolean hasFinished()
    {
        return getCurrent().equalsIgnoreCase(getFinish());
    }
    
    /**
     * Update the current Layer in this Menu
     * @param mouse Object containing any mouse input/events
     * @param keyboard Object containing any keyboard input/events
     * @param time The time deduction per each update
     */
    public void update(final Mouse mouse, final Keyboard keyboard, final long time) throws Exception
    {
        //if the menu has finished we will not update the current layer
        if (hasFinished())
            return;
        
        getLayer().update(this, mouse, keyboard, screen, time);
    }
    
    /**
     * Get the appropriate font size with the given text and limited container width.
     * Use this method if you have a container and you want the text to fit inside 
     * the width of the container.
     * 
     * @param text The text we want to fit inside the container
     * @param containerWidth The max width for the text to fit
     * @param graphics Graphics
     * @return float the appropriate font size
     */
    public static float getFontSize(final String text, final int containerWidth, final Graphics graphics)
    {
        //store the temporary font while adjustments are made
        Font tmp = graphics.getFont();
        
        //fontSize for the title text
        float fontSize;
        
        //get width of text at the max font size
        final int textWidth = graphics.getFontMetrics(graphics.getFont().deriveFont(MAX_FONT_SIZE)).stringWidth(text);

        //if the text width using the max font size is bigger than the container width
        if (textWidth >= containerWidth)
        {
            //font size will be a fraction of max based on the ratio of the two widths
            fontSize = (float)(((double)containerWidth / (double)textWidth) * MAX_FONT_SIZE);
            
            //convert float to int, example 28.6764 will be 28
            final int testSize = (int)fontSize;
            
            //convert int to float, example 28 will be 28.0
            fontSize = (float)testSize;
            
            //decrease the font size by 1 to ensure it will fit within the container
            fontSize--;
        }
        else
        {
            fontSize = MAX_FONT_SIZE;
        }
        
        //set the font back to the original
        graphics.setFont(tmp);
        
        return fontSize;
    }
    
    /**
     * Draw the Menu as long as it is not finished
     * @param graphics
     * @return Graphics 
     * @throws Exception 
     */
    public void render(Graphics graphics)
    {
        //if the menu has finished we will not draw it
        if (hasFinished())
            return;
        
        try
        {
            getLayer().render((Graphics2D)graphics, screen);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}