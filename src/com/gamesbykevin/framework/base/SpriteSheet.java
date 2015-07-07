package com.gamesbykevin.framework.base;

import com.gamesbykevin.framework.resources.Disposable;

import java.awt.Rectangle;
import java.util.HashMap;

/**
 * This class contains a List of animations
 * @author GOD
 */
public final class SpriteSheet implements Disposable
{
    //pause animations
    private boolean pause = false;
    
    //all of our animations will be contained here
    private HashMap<Object, Animation> animations;
    
    //the current animation
    private Object current;
    
    //delay per frame (nanoseconds)
    private long delayPerFrame = -1; 
    
    /**
     * Create a new sprite sheet and copy all the animations
     * @param spriteSheet Another sprite sheet whose attributes/properties we want to copy
     */
    public SpriteSheet(final SpriteSheet spriteSheet) throws Exception
    {
        this();
        
        //set the delay per update
        setDelay(spriteSheet.getDelay());
        
        //add existing animations
        for (Object key : spriteSheet.animations.keySet())
        {
            add(spriteSheet.getSpriteSheetAnimation(key), key);
        }
        
        //also set the current animation
        setCurrent(spriteSheet.getCurrent());
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
    
    @Override
    public void dispose()
    {
        for (Object key : animations.keySet())
        {
            Animation animation = animations.get(key);
            
            if (animation != null)
                animation.dispose();
            
            animation = null;
            
            animations.put(key, animation);
        }
        
        animations.clear();
        animations = null;
    
        current = null;
    }
    
    /**
     * Has the current animation finished
     * @return boolean
     */
    public boolean hasFinished() throws Exception
    {
        return getSpriteSheetAnimation().hasFinished();
    }
    
    /**
     * Has the current animation started
     * @return boolean
     */
    public boolean hasStarted() throws Exception
    {
        return getSpriteSheetAnimation().hasStarted();
    }
    
    /**
     * Is the current animation set to loop
     * @return true if the current animation is set to loop, false otherwise
     */
    public boolean hasLoop() throws Exception
    {
        return getSpriteSheetAnimation().hasLoop();
    }
    
    /**
     * Returns the location of the current animation
     * @return Rectangle location of the current animation
     */
    public Rectangle getLocation() throws Exception
    {
        return getSpriteSheetAnimation().getLocation();
    }
    
    /**
     * Resets current animation
     */
    public void reset() throws Exception
    {
        getSpriteSheetAnimation().reset();
    }
    
    /**
     * Checks to see if the animation exists
     * @return boolean true if the specified animation exists, false otherwise
     */
    public boolean hasAnimation(final Object current)
    {
        return (animations != null && animations.get(current) != null);
    }
    
    /**
     * Return true if any animations exist
     * @return boolean true if at least 1 animation exists, false otherwise
     */
    public boolean hasAnimations()
    {
        return (animations != null && animations.size() > 0);
    }
    
    /**
     * Sets the current animation with the assigned unique key
     * @param key Key to identify the animation
     * @throws Exception If the specified animation does not exist
     */
    public void setCurrent(final Object key) throws Exception
    {
        //if the animation does not exist throw exception because we can't set it
        if (getSpriteSheetAnimation(key) == null)
            throw new Exception("Animation does not exist for: " + key.toString());

        this.current = key;
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
     * @return boolean true if the delay is setup, false otherwise
     */
    public boolean hasDelay()
    {
        return hasDelay(getDelay());
    }
    
    /**
     * Checks if a delay has been setup.<br><br>
     * The delay has been setup if the value is greater than or equal to 0.
     * @param delay Time delay
     * @return boolean true if the delay is setup, false otherwise
     */
    public boolean hasDelay(final long delay)
    {
        return (delay >= 0);
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
     * @return The delay of the current frame of the current animation assigned
     */
    public long getDelayMax() throws Exception
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
     * Add an animation with a single frame
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     * @param delay time delay for frame
     * @param key Unique identifier for this animation
     */
    public void add(final int x, final int y, final int w, final int h, final long delay, final Object key)
    {
        add(new Animation(x, y, w, h, delay), key);
    }
    
    /**
     * Add animation to HashMap marking the key as the identifier
     * @param animation Sprite Animation
     * @param key Unique identifier for this animation
     */
    public void add(final Animation animation, final Object key)
    {
        animations.put(key, animation);
    }
    
    /**
     * Remove animation from our sprite sheet
     * @param key 
     */
    public void remove(final Object key)
    {
        animations.remove(key);
    }
    
    /**
     * Update the current assigned animation
     * @param delay Time delay between each game update "Note: must be greater than 0"
     * @throws Exception Will be thrown if the delay is not properly set
     */
    public void update(final long delay) throws Exception
    {
        if (isPaused())
            return;
        
        //make sure a delay is set even if it is 0
        if (!hasDelay(delay))
            throw new Exception("Delay is not set for this SpriteSheet");
        
        //update the animation
        getSpriteSheetAnimation().update(delay);
    }
    
    /**
     * Update the current animation in this sprite sheet
     */
    public void update() throws Exception
    {
        update(getDelay());
    }
    
    /**
     * 
     * @return Animation based on the current animation set
     */
    /**
     * Get the current animation. <br>
     * If animation is not set an exception will be thrown.
     * @return The current animation assigned
     * @throws Exception If the current animation is not assigned
     */
    public Animation getSpriteSheetAnimation() throws Exception
    {
        if (getCurrent() == null)
            throw new Exception("The current animation was not found because the current animation has not been set yet. Call setCurrent() to set the current animation");

        return getSpriteSheetAnimation(getCurrent());
    }
    
    /**
     * Get the sprite sheet animation. If animation is not set exception will occur.
     * @param key Unique identifier used to get the appropriate animation
     * @return Animation that corresponds with parameter key
     * @throws Exception If the specified key is null
     */
    public Animation getSpriteSheetAnimation(Object key) throws Exception
    {
        if (key == null)
            throw new Exception("animation key is null and not set.");

        return animations.get(key);
    }
}