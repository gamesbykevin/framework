package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.Audio;
import com.gamesbykevin.framework.resources.Disposable;
/**
 * Each menu layer can contain a number of Options and each Option can have a number of option selections
 * @author GOD
 */
public final class Selection implements Disposable
{
    //play sound when specific selection is selected
    private Audio sound;    
    
    //text to display for selection
    private String description;
    
    public Selection(String description, Audio sound)
    {
        this.description = description;
        this.sound = sound;
    }
 
    /**
     * Get the text that is displayed for this option selection
     * @return String
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * stops sound associated to selection if sound is playing
     */
    public void stopSound()
    {   
        if (this.sound != null)
        {
            this.sound.stopSound();
        }
    }
    
    /**
     * Play sound associated to this option selection
     */
    public void play()
    {   
        play(false);
    }
    
    /**
     * Play sound associated to this option selection
     * Loop the sound as well if specified.
     * 
     * @param loop
     */
    public void play(boolean loop)
    {   
        if (this.sound != null)
        {
            this.sound.play(loop);
        }
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        if (sound != null)
            sound.stopSound();
        
        sound = null;
        
        description = null;
    }
}