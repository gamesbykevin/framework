package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.resources.AudioResource;

import java.awt.*;
import java.util.*;

public class Option 
{
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    private LinkedHashMap selections = new LinkedHashMap(); //all possible selections for this option
    private Object nextLayerKey = null;                     //does the option when selected change the Layer
    private int index = 0;                                  //what is the current selection displayed
    private Rectangle location;                             //where is this option located
    private String title = "";                              //title of the options
    
    public Option(Object nextLayerKey)
    {
        this.nextLayerKey = nextLayerKey;
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
    
    public void add(String description, AudioResource sound) 
    {
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
    
    public Object getNextLayerKey()
    {   //returns which is the next layer to navigate to
        return this.nextLayerKey;
    }
    
    public boolean hasOptionSelection()
    {   //are there option selections for this Option
        return (selections.size() > 0);
    }
    
    public void next()
    {   //move to the next Selection in this option
        getOptionSelection().stop();    //stop audio of current selection if applicable
        
        index++;    //change index of current selection
        
        if (index >= selections.size()) //make sure index does not go out of bounds
            index = 0;
        
        getOptionSelection().play();    //play audio of current selection if applicable
    }
    
    private OptionSelection getOptionSelection()
    {   //gets the current OptionSelection
        return (OptionSelection)selections.get(getKey());
    }
    
    private Object getKey()
    {   //gets the key of the current index in our hashmap;
        return selections.keySet().toArray()[index];
    }
    
    public Graphics draw(Graphics g, Rectangle drawArea, boolean highlighted, Color menuColor1, Color menuColor2)
    {
        if (getLocation() == null)
            setLocation(drawArea);
        
        String phrase = title + getOptionSelection().getDescription();
        
        int textWidth = g.getFontMetrics().stringWidth(phrase);
        
        if (textWidth > drawArea.width) //if words expand the width then draw on multiple lines
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
    
    public void dispose()
    {
        index = 0;
        
        if (selections != null)
        {
            while(index < selections.size())
            {
                getOptionSelection().dispose();
                index++;
            }
        }
        
        selections = null;
    }
}