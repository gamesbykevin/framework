package com.gamesbykevin.framework.base;

import com.gamesbykevin.framework.resources.Disposable;

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
public final class Animation implements Disposable
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
    
    public Animation()
    {
        locations = new ArrayList<>();
        delays = new ArrayList<>();
    }
    
    /**
     * Use this constructor for convenience if you only have one frame in your animation
     * @param location
     * @param delay 
     */
    public Animation(final Rectangle location, final long delay)
    {
        //call default constructor
        this();
        
        //add animation frame
        add(location, delay);
    }
    
    @Override
    public void dispose()
    {
        locations.clear();
        locations = null;
    
        delays.clear();
        delays = null;
    }
    
    /**
     * Add animation frame, then call reset
     * @param x Start x coordinate of our animation frame
     * @param y Start y coordinate of our animation frame
     * @param width Width of animation frame
     * @param height Height of animation frame
     * @param delay Duration of frame
     */
    public void add(final int x, final int y, final int width, final int height, final long delay)
    {
        add(new Rectangle(x, y, width, height), delay);
    }
    
    /**
     * Add animation frame, then call reset
     * @param location Location of animation frame
     * @param delay Duration of frame
     */
    public void add(final Rectangle location, final long delay)
    {
        locations.add(location);
        delays.add(delay);
        
        reset();
    }
    
    /**
     * Reset animation back to the beginning.<br><br>
     * Also finished and started will be set to false.
     */
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
    public void update(final long delayPerFrame)
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
    
    private void setDelay(final long currentDelay)
    {
        this.currentDelay = currentDelay;
    }
    
    /**
     * Get the coordinates of the current Animation index
     * @return Rectangle
     */
    public Rectangle getLocation()
    {
        return getLocation(this.index);
    }
    
    /**
     * Get the coordinates of the specified Animation index
     * @param index
     * @return Rectangle
     */
    public Rectangle getLocation(final int index)
    {
        return locations.get(index);
    }
    
    /**
     * Is this animation looping
     * @return True if looping, false otherwise
     */
    public boolean hasLoop()
    {
        return loop;
    }
    
    /**
     * Set the animation to loop once finished
     * @param loop True if we want to loop, false otherwise
     */
    public void setLoop(final boolean loop)
    {
        this.loop = loop;
    }
    
    private int getIndex()
    {
        return this.index;
    }
    
    private void setIndex(final int index)
    {
        this.index = index;
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    private void setStarted(final boolean started)
    {
        this.started = started;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }
    
    public void setFinished(final boolean finished)
    {
        this.finished = finished;
    }
}