package com.gamesbykevin.framework.base;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will do the following <br>
 * 1) contain all image locations of a single animation<br>
 * 2) the duration to display each animation<br>
 * 3) whether or not to loop the animation<br>
 * 4) tell is if the animation has started or finished<br>
 * @author GOD
 */
public final class SpriteSheetAnimation 
{
    //where each image is located on the sprite sheet
    private List<Rectangle> locations;
    
    //how long do we stay in each frame before moving to the next (nanoseconds)
    private List<Long> delays;
    
    //do we loop the animation once finished
    private boolean loop;
    
    //has the animation finished
    private boolean finished = false; 
    
    //has the animation started
    private boolean started = false;
    
    private static final int INDEX_START = 0;
    
    //current location of array
    private int index = INDEX_START;
    
    //how much longer until we can move to the next frame in animation (nanoseconds)
    private long currentDelay = 0;
    
    public SpriteSheetAnimation()
    {
        locations = new ArrayList<>();
        delays = new ArrayList<>();
    }
    
    /**
     * Adds the location and delay to the animation, and then calls reset
     * @param location Location of animation frame
     * @param delay Delay to display animation frame
     */
    public void add(Rectangle location, long delay)
    {
        locations.add(location);
        delays.add(delay);
        
        reset();
    }
    
    public void reset()
    {
        setIndex(INDEX_START);
        setDelay(getDelayMax());
        setFinished(false);
        setStarted(false);
    }
    
    /**
     * Update the current animation by deducting time
     * @param delayPerFrame
     */
    public void update(long delayPerFrame)
    {
        setDelay(getDelay() - delayPerFrame);
        
        setStarted(true);
        
        if (getDelay() <= 0)
        {
            setIndex(getIndex() + 1);
            
            if (getIndex() > getSize() - 1)
            {
                if (loop)
                {
                    setIndex(INDEX_START);
                }
                else
                {
                    setIndex(getSize() - 1);
                    setFinished(true);
                }
            }
            
            setDelay(getDelayMax());
        }
    }
    
    private int getSize()
    {
        return locations.size();
    }
    
    /**
     * Get the start time for the current index
     * @return 
     */
    public long getDelayMax()
    {   
        return delays.get(getIndex());
    }
    
    /**
     * Returns the current delay until moving to the next animation
     * @return 
     */
    private long getDelay()
    {
        return this.currentDelay;
    }
    
    private void setDelay(long currentDelay)
    {
        this.currentDelay = currentDelay;
    }
    
    public Rectangle getLocation()
    {
        return getLocation(this.index);
    }
    
    public Rectangle getLocation(int index)
    {
        return locations.get(index);
    }
    
    public boolean hasLoop()
    {
        return loop;
    }
    
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }
    
    private int getIndex()
    {
        return this.index;
    }
    
    private void setIndex(int index)
    {
        this.index = index;
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    private void setStarted(boolean started)
    {
        this.started = started;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }
    
    private void setFinished(boolean finished)
    {
        this.finished = finished;
    }
}