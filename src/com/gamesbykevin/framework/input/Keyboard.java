package com.gamesbykevin.framework.input;

import java.util.ArrayList;

public class Keyboard 
{
    private boolean keyTyped, keyReleased, keyPressed;
    
    private ArrayList keysPressed = new ArrayList(), keysReleased = new ArrayList(), keysTyped = new ArrayList();
    
    public Keyboard()
    {
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
    
    public void setKeyTyped(char keyChar)
    {
        this.keyTyped = true;
        
        if (this.keysTyped.indexOf(keyChar) < 0)
            this.keysTyped.add(keyChar);
    }
    
    public void removeKeyTyped(char keyChar)
    {
        int index = this.keysTyped.indexOf(keyChar);
        if (index > -1)
            this.keysTyped.remove(index);
    }
    
    public boolean isKeyTyped()
    {
        return this.keyTyped;
    }
    
    public boolean hasKeyTyped(char keyChar)
    {
        return (this.keyTyped && this.keysTyped.indexOf(keyChar) > -1);
    }
    
    public void setKeyReleased(int keyReleasedCode)
    {
        this.keyReleased = true;
        
        if (this.keysReleased.indexOf(keyReleasedCode) < 0)
            this.keysReleased.add(keyReleasedCode);
        
        int index = this.keysPressed.indexOf(keyReleasedCode);
        if (index > -1)
            this.keysPressed.remove(index);
    }
    
    public void removeKeyReleased(int keyReleasedCode)
    {
        int index = this.keysReleased.indexOf(keyReleasedCode);
        if (index > -1)
            this.keysReleased.remove(index);
    }
    
    public boolean isKeyReleased()
    {
        return this.keyReleased;
    }
    
    public boolean hasKeyReleased(int keyReleasedCode)
    {
        return (this.keyReleased && this.keysReleased.indexOf(keyReleasedCode) > -1);
    }

    public void setKeyPressed(int keyPressedCode)
    {
        this.keyPressed = true;
        
        if (this.keysPressed.indexOf(keyPressedCode) < 0)
            this.keysPressed.add(keyPressedCode);
        
        int index = this.keysReleased.indexOf(keyPressedCode);
        if (index > -1)
            this.keysReleased.remove(index);
    }
    
    public void removeKeyPressed(int keyPressedCode)
    {
        int index = this.keysPressed.indexOf(keyPressedCode);
        if (index > -1)
            this.keysPressed.remove(index);
    }
    
    public boolean isKeyPressed()
    {
        return this.keyPressed;
    }
    
    public boolean hasKeyPressed(int keyPressedCode)
    {
        return (this.keyPressed && this.keysPressed.indexOf(keyPressedCode) > -1);
    }
    
    public void resetKeyReleasedEvent()
    {
        this.keyReleased = false;
        this.keysReleased.clear();
    }
    
    public void resetKeyPressedEvent()
    {
        this.keyPressed = false;
        this.keysPressed.clear();
    }
    
    public void resetKeyTypedEvent()
    {
        this.keyTyped = false;
        this.keysTyped.clear();
    }
    
    public void resetAllKeyEvents()
    {
        this.resetKeyPressedEvent();
        this.resetKeyReleasedEvent();
        this.resetKeyTypedEvent();
    }
}