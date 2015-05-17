package com.gamesbykevin.framework.resources;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The rules for loading resources
 * @author GOD
 */
public interface IResourceManager extends Disposable
{
    /**
     * Here we will handle loading the resources
     * @param source Class in root directory of jar.
     * 
     * @throws Exception 
     */
    public void update(final Class source) throws Exception;
    
    /**
     * This method will determine if all resources have been loaded into memory
     * @return true if loading is complete, false otherwise
     */
    public boolean isComplete();
    
    /**
     * This is the area where we draw a visual representation of the progress
     * @param graphics Graphics object to write to
     * @param screen The container for rendering the progress bar
     */
    public void render(final Graphics graphics, final Rectangle screen);
}