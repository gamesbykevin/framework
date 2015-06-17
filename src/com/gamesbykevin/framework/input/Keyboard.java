package com.gamesbykevin.framework.input;

import java.awt.event.KeyEvent;

public class Keyboard 
{
    //any keys pressed will be true in here
    private boolean[] pressed;
    
    //any keys released will be true in here
    private boolean[] released;
    
    public Keyboard()
    {
        this.pressed    = new boolean[KeyEvent.KEY_LAST];
        this.released   = new boolean[pressed.length];
    }
    
    /**
     * Free up resources
     */
    public void dispose()
    {
        this.pressed    = null;
        this.released   = null;
    }
    
    /**
     * Add key to List
     * @param keyCode The key we want to flag true
     */
    public void addKeyReleased(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //can't do both
            released[keyCode] = true;
            pressed[keyCode] = false;
        }
    }
    
    /**
     * Add key to List
     * @param keyCode The key we want to flag true
     */
    public void addKeyPressed(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //can't do both
            pressed[keyCode] = true;
            released[keyCode] = false;
        }
    }
    
    /**
     * Remove key from List
     * @param keyCode The key we want to flag false
     */
    public void removeKeyReleased(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //unflag
            released[keyCode] = false;
        }
    }
    
    /**
     * Remove key from List
     * @param keyCode The key we want to flag false
     */
    public void removeKeyPressed(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //unflag
            pressed[keyCode] = false;
        }
    }
    
    /**
     * Check if any keys have been released
     * @return boolean true if at least 1 key was released, false otherwise
     */
    public boolean isKeyReleased()
    {
        for (int i=0; i < released.length; i++)
        {
            //a key was released
            if (hasKeyReleased(i))
                return true;
        }
        
        return false;
    }
    
    /**
     * Check if any keys have been pressed
     * @return boolean true if at least 1 key was pressed, false otherwise
     */
    public boolean isKeyPressed()
    {
        for (int i=0; i < pressed.length; i++)
        {
            //a key was pressed
            if (hasKeyPressed(i))
                return true;
        }
        
        return false;
    }
    
    /**
     * Has the specified key been pressed
     * @param key Key we want to check
     * @return boolean true if it has been pressed, false otherwise
     */
    public boolean hasKeyPressed(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //return result
            return pressed[keyCode];
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Has the specified key been released
     * @param key Key we want to check
     * @return boolean true if it has been released, false otherwise
     */
    public boolean hasKeyReleased(final int keyCode)
    {
        //make sure we are within range of array
        if (hasRange(keyCode))
        {
            //return result
            return released[keyCode];
        }
        else
        {
            return false;
        }
    }

    /**
     * Do we have range?
     * @param index Index location we want to check
     * @return true if we are within range of the list, otherwise false
     */
    private boolean hasRange(final int index)
    {
        try
        {
            if (index < 0 || index > pressed.length - 1 || index > released.length - 1)
            {
                //print to command line key not available
                System.out.println("The keyCode value: " + index + " is too large for the keyboard array list and can't be cheked for keyboard events");
                
                //return false
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Reset all key released events
     */
    public void resetKeyReleased()
    {
        for (int i=0; i < released.length; i++)
        {
            released[i] = false;
        }
    }
    
    /**
     * Reset all key pressed events
     */
    public void resetKeyPressed()
    {
        for (int i=0; i < pressed.length; i++)
        {
            pressed[i] = false;
        }
    }
    
    /**
     * Reset all key pressed, released events
     */
    public void reset()
    {
        resetKeyPressed();
        resetKeyReleased();
    }
}