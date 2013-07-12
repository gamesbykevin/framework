package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.AudioResource;

import java.util.*;
import java.awt.*;

public class OptionSelection 
{   //each Menu Layer can contain a number of Options
    private AudioResource sound;    //play sound when specific selection is selected
    private String description;     //text to display for selection
    
    public OptionSelection(String description, AudioResource sound)
    {
        this.description = description;
        this.sound = sound;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public void stop()
    {   //stops sound associated to selection
        if (this.sound != null)
        {
            this.sound.stop();
        }
    }
    
    public void play()
    {   
        play(false);
    }
    
    public void play(boolean loop)
    {   //plays sound associated to selection
        if (this.sound != null)
        {
            this.sound.play(loop);
        }
    }
    
    public void dispose()
    {
        if (sound != null)
            sound.stop();
        
        sound = null;
        
        description = null;
    }
}
