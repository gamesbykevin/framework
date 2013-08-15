package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.Keyboard;
import com.gamesbykevin.framework.input.Mouse;
import java.awt.Graphics;

import java.awt.Rectangle;
import java.util.LinkedHashMap;

public class Menu 
{
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    private LinkedHashMap<Object, Layer> layers;
    
    //screen menu will be drawn within
    private Rectangle screen;
    
    //current layer
    private int index = 0;
    
    //which layer is last
    private int indexFinish = 0;
    
    public Menu(Rectangle screen) 
    {
        this.screen = new Rectangle(screen);
        
        layers = new LinkedHashMap<>();
    }
    
    public void dispose()
    {
        //loop through every key available
        for (Object key : layers.keySet().toArray())
        {
            //recycle layer elements
            getLayer(key).dispose();
            layers.put(key, null);
        }
        
        layers.clear();
        layers = null;
    }
    
    public Rectangle getScreen()
    {
        return screen;
    }
    
    protected void add(Object key, Layer layer)
    {
        layers.put(key, layer);
    }
    
    protected boolean isCurrentLayer(Object key)
    {
        if (layers.keySet().toArray()[index] == key)
        {   //we found layer that matched key and is current
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void resetLayer()
    {   //gets the current layer and resets the timer
        getLayer().reset();
    }
    
    /**
     * Set the last layer so we will know when the menu is finished
     * @param key Unique identifier for the specific layer
     */
    //lets us know which layer is the last
    public void setLayerFinish(Object key)
    {
        for (int i=0; i < layers.size(); i++)
        {
            //we found layer that matched key
            if (layers.keySet().toArray()[i] == key)
            {
                indexFinish = i;
                break;
            }
        }
    }
    
    /**
     * Sets the current Layer
     * @param key 
     */
    public void setLayer(Object key)
    {
        for (int i=0; i < layers.size(); i++)
        {
            if (layers.keySet().toArray()[i] == key)
            {   //we found layer that matched key
                index = i;
                break;
            }
        }
    }
    
    private Layer getLayer(Object key)
    {
        return (Layer)layers.get(key);
    }
    
    //gets the current OptionSelection
    private Layer getLayer()
    {   
        return getLayer(getKey());
    }
    
    //gets the key of the current index in our hashmap;
    public Object getKey()
    {   
        return layers.keySet().toArray()[index];
    }
    
    //this will set the index of a specific selection for a specific option for a specific layer
    public void setOptionSelectionIndex(final Object layerKey, final Object optionKey, final int index) 
    {
        getLayer(layerKey).getOption(optionKey).setIndex(index);
    }
    
    //this will get the index of a specific selection for a specific option for a specific layer
    public int getOptionSelectionIndex(final Object layerKey, final Object optionKey)
    {   
        return getLayer(layerKey).getOption(optionKey).getIndex();
    }
    
    //if no layers have been added the menu is not setup
    public boolean isMenuSetup()
    {   
        return (layers.size() > 0);
    }
    
    public boolean isMenuFinished()
    {   //if we are on last layer then menu is finished
        return (indexFinish == index);
    }
    
    public void update(Mouse mouseInput, Keyboard keyBoardInput, long timeDeduction) 
    {
        if (isMenuFinished())
            return;
        
        getLayer().update(this, mouseInput, keyBoardInput, screen, timeDeduction);
    }
    
    public Graphics render(Graphics g) 
    {
        if (isMenuFinished())
            return g;
        
        getLayer().render(g, screen);
        
        return g;
    }
}