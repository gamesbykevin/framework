package com.gamesbykevin.framework.input;

import java.util.ArrayList;
import java.util.List;

public class Keyboard 
{
    private boolean keyTyped, keyReleased, keyPressed;
    
    private List<Integer> keysPressed, keysReleased;
    private List<Character> keysTyped;
    
    public Keyboard()
    {
        keysPressed  = new ArrayList<>();
        keysReleased = new ArrayList<>();
        keysTyped    = new ArrayList<>();
        
        this.resetAllKeyEvents();
    }
    
    public void dispose()
    {
        keysPressed.clear();
        keysPressed = null;

        keysReleased.clear();
        keysReleased = null;

        keysTyped.clear();
        keysTyped = null;
    }
    
    public void setKeyTyped(final char keyChar)
    {
        keyTyped = true;
        
        if (keysTyped.indexOf(keyChar) < 0 && keysTyped.size() > 0)
            keysTyped.add(keyChar);
    }
    
    public void removeKeyTyped(final char keyChar)
    {
        final int index = keysTyped.indexOf(keyChar);
        
        if (index > -1 && keysTyped.size() > 0)
            keysTyped.remove(index);
    }
    
    public boolean isKeyTyped()
    {
        return this.keyTyped;
    }
    
    public boolean hasKeyTyped(final char keyChar)
    {
        return (keyTyped && keysTyped.indexOf(keyChar) > -1);
    }
    
    public void setKeyReleased(final int keyReleasedCode)
    {
        keyReleased = true;
        
        if (keysReleased.indexOf(keyReleasedCode) < 0)
            keysReleased.add(keyReleasedCode);
        
        final int index = keysPressed.indexOf(keyReleasedCode);
        
        if (index > -1 && keysPressed.size() > 0)
            keysPressed.remove(index);
    }
    
    public void removeKeyReleased(final int keyReleasedCode)
    {
        final int index = keysReleased.indexOf(keyReleasedCode);
        
        if (index > -1 && keysReleased.size() > 0)
            keysReleased.remove(index);
    }
    
    public boolean isKeyReleased()
    {
        return keyReleased;
    }
    
    public boolean hasKeyReleased(final int keyReleasedCode)
    {
        return (keyReleased && keysReleased.indexOf(keyReleasedCode) > -1);
    }

    public void setKeyPressed(final int keyPressedCode)
    {
        keyPressed = true;
        
        if (keysPressed.indexOf(keyPressedCode) < 0)
            keysPressed.add(keyPressedCode);
        
        final int index = keysReleased.indexOf(keyPressedCode);
        
        if (index > -1 && keysReleased.size() > 0)
            keysReleased.remove(index);
    }
    
    public void removeKeyPressed(final int keyPressedCode)
    {
        final int index = keysPressed.indexOf(keyPressedCode);
        
        if (index > -1&& keysPressed.size() > 0)
            keysPressed.remove(index);
    }
    
    public boolean isKeyPressed()
    {
        return keyPressed;
    }
    
    public boolean hasKeyPressed(final int keyPressedCode)
    {
        return (keyPressed && keysPressed.indexOf(keyPressedCode) > -1);
    }
    
    public void resetKeyReleasedEvent()
    {
        keyReleased = false;
        keysReleased.clear();
    }
    
    public void resetKeyPressedEvent()
    {
        keyPressed = false;
        keysPressed.clear();
    }
    
    public void resetKeyTypedEvent()
    {
        keyTyped = false;
        keysTyped.clear();
    }
    
    public void resetAllKeyEvents()
    {
        resetKeyPressedEvent();
        resetKeyReleasedEvent();
        resetKeyTypedEvent();
    }
}