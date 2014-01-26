package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.base.Sprite;
import com.gamesbykevin.framework.resources.Disposable;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 * The point of this class is to provide an overlay screen that can do the following
 * 1. Display a background Image
 * 2. Add an infinite # of Rectangle coordinates for mouse input
 * 3. Determine which element was selected
 * 
 * @author GOD
 */
public abstract class Overlay extends Sprite implements Disposable
{
    //list of boundaries the user can select
    private HashMap<Object, Rectangle> boundaries;
    
    protected Overlay()
    {
        //create our new list
        boundaries = new HashMap<>();
    }
    
    /**
     * Get the rectangle object with the specific key
     * @param key Unique Identifier we need to find the object
     * @return Rectangle of boundary, if not found null is returned
     */
    public Rectangle get(final Object key)
    {
        return boundaries.get(key);
    }
    
    /**
     * Get the identifier at the specified location
     * @param location The x,y we want to see is located
     * @return Unique Identifier of found boundary, if not found null is returned
     */
    public Object get(final Point location)
    {
        for (Object key : boundaries.keySet())
        {
            if (boundaries.get(key) != null && boundaries.get(key).contains(location))
                return key;
        }
        
        return null;
    }
    
    /**
     * Add a boundary to our list
     * @param key Unique Identifier for the boundary object
     * @param boundary Area user can select
     */
    public void add(final Object key, final Rectangle boundary)
    {
        boundaries.put(key, boundary);
    }
    
    /**
     * Add a boundary to our list
     * @param key Unique Identifier for the boundary object
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     */
    public void add(final Object key, final int x, final int y, final int w, final int h)
    {
        add(key, new Rectangle(x, y, w, h));
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        boundaries.clear();
        boundaries = null;
    }
}