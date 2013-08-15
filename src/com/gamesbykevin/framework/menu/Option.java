package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.Audio;

import java.awt.*;
import java.util.*;

public class Option 
{
    //all possible selections for this option, LinkedHashMap retains order when selections are added
    private LinkedHashMap<Object, OptionSelection> selections;
    
    //does this option "when selected" determine the next layer
    private Object nextLayerKey = null;
    
    //what is the current selection displayed
    private int index = 0;
    
    //where is this option located
    private Rectangle location;
    
    //title of the options
    private String title = "";
    
    public Option(Object nextLayerKey)
    {
        this.nextLayerKey = nextLayerKey;
        
        selections = new LinkedHashMap<>(); 
    }
    
    public Option(String title)
    {
        this.title = title;
    }
    
    public Option()
    {
        
    }
            
    public void setLocation(Rectangle location)
    {
        this.location = location;
    }
    
    public Rectangle getLocation()
    {
        return this.location;
    }
    
    public boolean hasLocation(Point mouseLocation)
    {   //check if mouse hit this option
        if (location == null)
            return false;
        
        return location.contains(mouseLocation);
    }
    
    public void add(String description, Audio sound) 
    {
        if (selections == null)
            selections = new LinkedHashMap<>(); 
        
        int key = selections.size();
        
        OptionSelection optionSelection = new OptionSelection(description, sound);
        
        selections.put(key, optionSelection);
    }
    
    /**
     * Sets the option selection according to int index
     * 
     * @param index the selection you like
     */
    public void setIndex(final int index)
    {
        this.index = index;
        
        if (this.index >= selections.size())
            this.index = 0;
        if (this.index < 0)
            this.index = 0;
    }
    
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Get the next layer for the menu to navigate to
     * @return Object
     */
    public Object getNextLayerKey()
    {
        return this.nextLayerKey;
    }
    
    /**
     * Checks if there are option selections for this Option
     * @return boolean
     */
    public boolean hasOptionSelection()
    {
        return (selections.size() > 0);
    }
    
    /**
     * Move to the next selection in this option
     */
    public void next()
    {
        //stop audio of current selection if applicable
        getOptionSelection().stopSound();
        
        //change index of current selection
        index++;
        
        //make sure index does not go out of bounds
        if (index >= selections.size()) 
            index = 0;
        
        //play audio of current selection if exists
        getOptionSelection().play();
    }
    
    /**
     * Get the current option selection
     * @return OptionSelection 
     */
    private OptionSelection getOptionSelection()
    {
        return getpOptionSelection(getKey());
    }
    
    /**
     * Get the option selection based on the key parameter
     * @param key
     * @return OptionSelection
     */
    private OptionSelection getpOptionSelection(Object key)
    {
        return (OptionSelection)selections.get(key);
    }
    
    /**
     * Get the unique key for the current index in the hash map
     * @return 
     */
    private Object getKey()
    {
        return selections.keySet().toArray()[index];
    }
    
    public Graphics draw(Graphics g, Rectangle drawArea, boolean highlighted, Color menuColor1, Color menuColor2)
    {
        if (getLocation() == null)
            setLocation(drawArea);
        
        String phrase = title + getOptionSelection().getDescription();
        
        int textWidth = g.getFontMetrics().stringWidth(phrase);
        
        //if words expand the width then draw on multiple lines
        if (textWidth > drawArea.width)
        {
            String[] eachWord = phrase.split(" ");
            String currentSentence = "";
            
            int startY = drawArea.y + g.getFontMetrics().getHeight();
            
            int maxFontSize = 48;
            int minFontSize = 8;

            for (int fontSize = maxFontSize; fontSize >= minFontSize; fontSize--)
            {
                g.setFont(g.getFont().deriveFont(Font.BOLD, fontSize));

                startY = drawArea.y + g.getFontMetrics().getHeight();
                
                for (int i=0; i < eachWord.length; i++)
                {
                    if (g.getFontMetrics().stringWidth(currentSentence) + 1 + g.getFontMetrics().stringWidth(eachWord[i]) >= drawArea.width)
                    {
                        startY += g.getFontMetrics().getHeight();
                        currentSentence = eachWord[i];
                    }
                    else
                    {
                        if (currentSentence.length() < 1)
                            currentSentence = eachWord[i];
                        else
                            currentSentence = currentSentence + " " + eachWord[i];
                    }
                }
            
                if (startY < drawArea.y + drawArea.height)
                    break;
            }
        
            startY = drawArea.y + g.getFontMetrics().getHeight();
            currentSentence = title;
            
            for (int i=0; i < eachWord.length; i++)
            {
                if (g.getFontMetrics().stringWidth(currentSentence) + 1 + g.getFontMetrics().stringWidth(eachWord[i]) >= drawArea.width)
                {
                    g.drawString(currentSentence, drawArea.x, startY);
                    startY += g.getFontMetrics().getHeight();
                    currentSentence = eachWord[i];
                    
                    if (i == eachWord.length - 1)
                        g.drawString(currentSentence, drawArea.x, startY);
                }
                else
                {
                    if (currentSentence.length() < 1)
                        currentSentence = eachWord[i];
                    else
                        currentSentence = currentSentence + " " + eachWord[i];
                    
                    if (i == eachWord.length - 1)
                        g.drawString(currentSentence, drawArea.x, startY);
                }
            }
        }
        else
        {
            int drawX = drawArea.x + (int)(drawArea.width * .03);
            int drawY = drawArea.y + (drawArea.height / 2) + (g.getFontMetrics().getHeight() / 2);

            if (highlighted)
            {
                g.setColor(menuColor1);
                g.fillRect(drawArea.x, drawArea.y, drawArea.width, drawArea.height);
                g.setColor(menuColor2);
                g.drawString(phrase, drawX, drawY);
            }
            else
            {
                g.setColor(menuColor1);
                g.drawString(phrase, drawX, drawY);
            }
        }
        
        return g;
    }
    
    /**
     * Free up resources
     */
    public void dispose()
    {
        if (selections != null)
        {
            for (Object key : selections.keySet().toArray())
            {
                getpOptionSelection(key).dispose();
                selections.put(key, null);
            }
            
            selections.clear();
            selections = null;
        }
        
        nextLayerKey = null;
        location = null;
        title = null;
    }
}