package com.gamesbykevin.framework.input;

import java.util.ArrayList;
import java.util.List;

public class Keyboard 
{
    private List<Integer> keysPressed, keysReleased;
    private List<Character> keysTyped;
    
    public Keyboard()
    {
        keysPressed  = new ArrayList<>();
        keysReleased = new ArrayList<>();
        keysTyped    = new ArrayList<>();
    }
    
    /**
     * Free up resources
     */
    public void dispose()
    {
        keysPressed.clear();
        keysPressed = null;

        keysReleased.clear();
        keysReleased = null;

        keysTyped.clear();
        keysTyped = null;
    }
    
    /**
     * Add the specified keyChar typed
     * @param keyChar 
     */
    public void addKeyTyped(final char keyChar)
    {
        if (keysTyped.indexOf(keyChar) < 0)
            keysTyped.add(keyChar);
    }
    
    /**
     * Remove the specified key typed
     * @param keyChar 
     */
    public void removeKeyTyped(final char keyChar)
    {
        final int index = keysTyped.indexOf(keyChar);
        
        if (index > -1 && !keysTyped.isEmpty())
            keysTyped.remove(index);
    }
    
    /**
     * Have there been keys typed
     * @return boolean
     */
    public boolean isKeyTyped()
    {
        return (!keysTyped.isEmpty());
    }
    
    /**
     * Checks if the keyTyped flag is true and if the 
     * keyChar is in the keysTyped List.
     * 
     * @param keyChar
     * @return boolean
     */
    public boolean hasKeyTyped(final char keyChar)
    {
        return keysTyped.indexOf(keyChar) > -1;
    }
    
    /**
     * Add keyCode to List
     * 
     * @param keyCode 
     */
    public void addKeyReleased(final int keyCode)
    {
        if (keysReleased.indexOf(keyCode) < 0)
            keysReleased.add(keyCode);
    }
    
    /**
     * Remove keyCode from List
     * 
     * @param keyCode 
     */
    public void removeKeyReleased(final int keyCode)
    {
        final int index = keysReleased.indexOf(keyCode);
        
        if (index > -1 && !keysReleased.isEmpty())
            keysReleased.remove(index);
    }
    
    /**
     * Check if any keys have been released
     * 
     * @return boolean
     */
    public boolean isKeyReleased()
    {
        return (!keysReleased.isEmpty());
    }
    
    /**
     * Does keyCode exist in list
     * @param keyCode
     * @return boolean
     */
    public boolean hasKeyReleased(final int keyCode)
    {
        return keysReleased.indexOf(keyCode) > -1;
    }

    /**
     * Add keyCode to List
     * @param keyCode 
     */
    public void addKeyPressed(final int keyCode)
    {
        if (keysPressed.indexOf(keyCode) < 0)
            keysPressed.add(keyCode);
    }
    
    /**
     * Removes the keyCode from the List
     * @param keyCode 
     */
    public void removeKeyPressed(final int keyCode)
    {
        final int index = keysPressed.indexOf(keyCode);
        
        if (index > -1 && !keysPressed.isEmpty())
            keysPressed.remove(index);
    }
    
    /**
     * Has any key been pressed
     * @return boolean
     */
    public boolean isKeyPressed()
    {
        return (!keysPressed.isEmpty());
    }
    
    /**
     * Checks if keyPressed flag is set to true and if parameter
     * keyCode exists in the List
     * @param keyCode
     * @return boolean
     */
    public boolean hasKeyPressed(final int keyCode)
    {
        return (keysPressed.indexOf(keyCode) > -1);
    }
    
    /**
     * Reset all key released events
     */
    public void resetKeyReleased()
    {
        keysReleased.clear();
    }
    
    /**
     * Reset all key pressed events
     */
    public void resetKeyPressed()
    {
        keysPressed.clear();
    }
    
    /**
     * Reset all key typed events
     */
    public void resetKeyTyped()
    {
        keysTyped.clear();
    }
    
    /**
     * Reset all key pressed, released, typed events
     */
    public void reset()
    {
        resetKeyPressed();
        resetKeyReleased();
        resetKeyTyped();
    }
}