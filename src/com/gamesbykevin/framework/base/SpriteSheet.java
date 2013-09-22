package com.gamesbykevin.framework.base;

import java.awt.Rectangle;
import java.util.HashMap;

/**
 * This class contains a List of animations
 * @author GOD
 */
public final class SpriteSheet 
{
    //pause animations
    private boolean pause = false;
    
    //all of our animations will be contained here
    private HashMap<Object, SpriteSheetAnimation> animations;
    
    private Object current;
    
    //delay per frame (nanoseconds)
    private long delayPerFrame = -1; 
    
    public SpriteSheet(final SpriteSheet spriteSheet)
    {
        this();
        setDelay(spriteSheet.getDelay());
    }
    
    public SpriteSheet(final long delayPerFrame)
    {
        this();
        setDelay(delayPerFrame);
    }
    
    public SpriteSheet()
    {
        animations = new HashMap<>();
    }
    
    /**
     * Has the current animation finished
     * @return boolean
     */
    public boolean hasFinished()
    {
        return getSpriteSheetAnimation().hasFinished();
    }
    
    /**
     * Has the current animation started
     * @return boolean
     */
    public boolean hasStarted()
    {
        return getSpriteSheetAnimation().hasStarted();
    }
    
    /**
     * Returns the location of the current animation
     * @return Rectangle location of the current animation
     */
    public Rectangle getLocation()
    {
        return getSpriteSheetAnimation().getLocation();
    }
    
    /**
     * Resets current animation
     */
    public void reset()
    {
        getSpriteSheetAnimation().reset();
    }
    
    /**
     * Checks to see if the animation exists
     * @return boolean
     */
    public boolean hasAnimation(final Object current)
    {
        return (animations.get(current) != null);
    }
    
    /**
     * Return true if any animations exist
     * @return boolean
     */
    public boolean hasAnimations()
    {
        return (animations.size() > 0);
    }
    
    /**
     * Sets the current animation with the assigned unique key
     * @param current Object that uniquely identifies the animation
     */
    public void setCurrent(final Object current)
    {
        this.current = current;
    }
    
    /**
     * Gets the current animation unique key
     * @return Object the key used to access the current animation
     */
    public Object getCurrent()
    {
        return this.current;
    }
    
    /**
     * Checks if a delay has been setup.<br><br>
     * The delay has been setup if the value is greater than or equal to 0.
     * @return boolean
     */
    public boolean hasDelay()
    {
        return (getDelay() >= 0);
    }
    
    /**
     * Returns the delay per each update
     * @return long
     */
    public long getDelay()
    {
        return this.delayPerFrame;
    }
    
    /**
     * gets the duration of the current frame in the current animation
     * @return 
     */
    public long getDelayMax()
    {
        return getSpriteSheetAnimation().getDelayMax();
    }
    
    /**
     * Set the delay per each update
     * @param delayPerFrame long
     */
    public void setDelay(final long delayPerFrame)
    {
        this.delayPerFrame = delayPerFrame;
    }
    
    /**
     * Pause all animations
     * @param pause Boolean 
     */
    public void setPause(final boolean pause)
    {
        this.pause = pause;
    }
    
    public boolean isPaused()
    {
        return this.pause;
    }
    
    /**
     * Add animation to HashMap marking the key as the identifier
     * @param animation Sprite Animation
     * @param key Unique identifier for this animation
     */
    public void add(final SpriteSheetAnimation animation, final Object key)
    {
        animations.put(key, animation);
    }
    
    /**
     * Update the current animation in this sprite sheet
     */
    public void update() throws Exception
    {
        if (isPaused())
            return;
        
        //make sure a delay is set even if it is 0
        if (!hasDelay())
            throw new Exception("Delay is not set for this SpriteSheet");
        
        getSpriteSheetAnimation().update(getDelay());
    }
    
    /**
     * Get current animation
     * @return SpriteSheetAnimation
     */
    private SpriteSheetAnimation getSpriteSheetAnimation()
    {
        return getSpriteSheetAnimation(current);
    }
    
    /**
     * Get the sprite sheet animation
     * @param key Unique identifier used to get the appropriate animation
     * @return 
     */
    public SpriteSheetAnimation getSpriteSheetAnimation(Object key)
    {
        return animations.get(key);
    }
}