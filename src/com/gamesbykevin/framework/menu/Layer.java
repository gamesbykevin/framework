package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.*;
import com.gamesbykevin.framework.resources.*;
import com.gamesbykevin.framework.util.*;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;

/**
 * This contains the proper information for this layer.
 * A menu will have multiple layers.
 * 
 * @author GOD
 */
public class Layer 
{  
    public enum Type
    {
        NONE, 
        FADE_IN, 
        FADE_OUT, 
        SCROLL_HORIZONTAL_EAST, 
        SCROLL_HORIZONTAL_WEST, 
        SCROLL_VERTICAL_NORTH, 
        SCROLL_VERTICAL_SOUTH, 
        CURTAIN_CLOSE_HORIZONTAL, 
        CURTAIN_CLOSE_VERTICAL, 
        CURTAIN_OPEN_HORIZONTAL, 
        CURTAIN_OPEN_VERTICAL, 
        SCROLL_HORIZONTAL_EAST_REPEAT, 
        SCROLL_HORIZONTAL_WEST_REPEAT, 
        SCROLL_VERTICAL_NORTH_REPEAT, 
        SCROLL_VERTICAL_SOUTH_REPEAT 
    }
    
    //the transition for this layer
    private Type type;
    
    //which option is the current one selected
    private int index = 0;
    
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    //options assigned to this layer
    private LinkedHashMap<Object, Option> options;
    
    //time mechanism for this layer
    private Timer timer;
    
    //window in which layer will be drawn
    private Rectangle location;
    
    //transparency of layer
    private float visibility = 1;
    
    //some Layers will need to track progress
    private float percentComplete = 0;
    
    //pause layer after time mechanism complete
    private boolean pause = false;
    
    //force user to view layer
    private boolean force = false;
    
    //does this Layer have an image as the background
    private Image image;
    
    //after layer complete which is next
    private Object nextLayerKey;
    
    //if this layer has options a title will need to be set
    private String title;
    
    //does this layer have background sound to play
    private Audio sound;

    //border color
    private Color menuColor1 = Color.WHITE; 
    
    //text color
    private Color menuColor2 = Color.BLACK;
    
    //menu background color
    private Color menuColor3 = Color.BLUE;
    
    public Layer(Type type, Rectangle screen)
    {
        options = new LinkedHashMap<>();
        
        this.type = type;
        
        switch (type)
        {
            case FADE_IN:
                visibility = 0;
                location = new Rectangle(screen);
                break;
                
            case SCROLL_HORIZONTAL_EAST:
            case SCROLL_HORIZONTAL_EAST_REPEAT:
                location = new Rectangle(screen.x - screen.width, screen.y, screen.width, screen.height);
                break;
                
            case SCROLL_VERTICAL_NORTH:
            case SCROLL_VERTICAL_NORTH_REPEAT:
                location = new Rectangle(screen.x, screen.y + screen.height, screen.width, screen.height);
                break;
                
            case SCROLL_VERTICAL_SOUTH:
            case SCROLL_VERTICAL_SOUTH_REPEAT:
                location = new Rectangle(screen.x, screen.y - screen.height, screen.width, screen.height);
                break;
                
            case NONE:
            case FADE_OUT:
            case SCROLL_HORIZONTAL_WEST:
            case SCROLL_HORIZONTAL_WEST_REPEAT:
            case CURTAIN_CLOSE_HORIZONTAL:
            case CURTAIN_OPEN_HORIZONTAL:
            case CURTAIN_CLOSE_VERTICAL:
            case CURTAIN_OPEN_VERTICAL:
                location = new Rectangle(screen);
                break;
        }
    }
    
    private boolean getPause()
    {
        return this.pause;
    }
    
    private boolean getForce()
    {
        return this.force;
    }
    
    public void setPause(boolean pause)
    {
        this.pause = pause;
    }
    
    public void setForce(boolean force)
    {
        this.force = force;
    }
    
    public void reset()
    {
        resetTimer();
    }
    
    private void resetTimer()
    {
        if (timer != null)
            timer.resetRemaining();
    }
    
    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }
    
    public void setSound(Audio sound)
    {
        this.sound = sound;
    }
    
    public void setImage(Image image)
    {
        this.image = image;
    }
    
    public void setNextLayerKey(Object nextLayerKey)
    {
        this.nextLayerKey = nextLayerKey;
    }
    
    public Object getNextLayerKey()
    {
        return this.nextLayerKey;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public void setVisibility(float visibility)
    {
        this.visibility = visibility;
    }
    
    //adds Option to hashMap
    public void add(Object key, Option option)
    {
        options.put(key, option);
    }
    
    /**
     * If the mouse intersects the option set as current option
     * @param location 
     */
    public void setIndex(final Point location)
    {
        for (int i=0; i < options.size(); i++)
        {
            Option tmp = getOption(getKey(i));
            
            if (tmp.hasLocation(location))
            {
                this.index = i;
                break;
            }
        }
    }
    
    public Option getOption(Point mouseLocation)
    {
        for (int i=0; i < options.size(); i++)
        {
            Option tmp = getOption(getKey(i));
            
            if (tmp.hasLocation(mouseLocation))
                return tmp;
        }
        
        return null;
    }
    
    public boolean hasOptions()
    {
        return (options.size() > 0);
    }
    
    public Option getOption(Object key)
    {
        return (Option)options.get(key);
    }
    
    /**
     * Get the current option
     * @return 
     */
    public Option getOption()
    {
        return getOption(getKey());
    }
    
    /**
     * Gets the key of the current index in our hash map
     * @return Object
     */
    private Object getKey()
    {
        return getKey(this.index);
    }
    
    /**
     * Gets the key of a specific index in our hash map
     * 
     * @param index
     * @return Object
     */
    private Object getKey(int index)
    {
        if (!hasOptions())
            return null;
        
        return options.keySet().toArray()[index];
    }
    
    public void update(Menu menu, Mouse mi, Keyboard ki, Rectangle screen, long timeDeduction) 
    {
        //make sure we aren't forced to view this layer
        if (!getForce())
        {
            //check for input to skip to the next layer
            if (ki.isKeyPressed() || mi.isMousePressed())
            {
                //if we have no options and we have the next layer set
                if (getNextLayerKey() != null && !hasOptions())
                {
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    Option option;

                    //if the mouse was pressed or the mouse was moved
                    if (mi.isMousePressed() || mi.hasMouseMoved())
                    {
                        option = getOption(mi.getLocation());

                        //does the option exist
                        if (option != null)
                        {
                            if (mi.isMousePressed())
                            {   
                                //move to next option selection in this option or possibly change layer
                                if (option.getNextLayerKey() == null)
                                {
                                    option.next();
                                }
                                else
                                {
                                    menu.setLayer(option.getNextLayerKey());
                                    
                                    //since we are possibly re-selecting a layer reset the time
                                    menu.resetLayer();
                                }
                            }
                        }
                    }

                    if (ki.hasKeyPressed(KeyEvent.VK_ENTER) && hasOptions())
                    {
                        option = getOption();

                        if (option.getNextLayerKey() == null)
                        {
                            option.next();
                        }
                        else
                        {
                            menu.setLayer(option.getNextLayerKey());
                            
                            //since we are possibly re-selecting a layer reset the time
                            menu.resetLayer();
                        }
                    }

                    if (ki.hasKeyPressed(KeyEvent.VK_UP))
                        index--;

                    if (ki.hasKeyPressed(KeyEvent.VK_DOWN))
                        index++;

                    if (index < 0)
                        index = options.size() - 1;
                    if (index >= options.size())
                        index = 0;
                }

                ki.reset();
                mi.reset();
            }
            else
            {
                if (mi.hasMouseMoved() && hasOptions())
                {   
                    //highlight the current Option
                    setIndex(mi.getLocation());
                }
            }
        }
        
        float percentComplete = 1;
        
        if (timer != null)
            percentComplete = timer.getProgress();
        
        if (percentComplete > 1)
            percentComplete = 1;
        if (percentComplete < 0)
            percentComplete = 0;
        
        switch (type)
        {
            case FADE_IN:
                setVisibility(percentComplete);
                break;

            case FADE_OUT:
                setVisibility(1 - percentComplete);
                break;

            case SCROLL_HORIZONTAL_EAST:
            case SCROLL_HORIZONTAL_EAST_REPEAT:
                location.x = screen.x - (int)((1 - percentComplete) * screen.width);
                break;

            case SCROLL_HORIZONTAL_WEST:
            case SCROLL_HORIZONTAL_WEST_REPEAT:
                location.x = screen.x + screen.width - (int)(percentComplete * screen.width);
                break;

            case SCROLL_VERTICAL_NORTH:
            case SCROLL_VERTICAL_NORTH_REPEAT:
                location.y = screen.y + screen.height - (int)(percentComplete * screen.height);
                break;

            case SCROLL_VERTICAL_SOUTH:
            case SCROLL_VERTICAL_SOUTH_REPEAT:
                location.y = screen.y - (int)((1 - percentComplete) * screen.height);
                break;
                
            case CURTAIN_CLOSE_HORIZONTAL:
            case CURTAIN_OPEN_HORIZONTAL:
            case CURTAIN_CLOSE_VERTICAL:
            case CURTAIN_OPEN_VERTICAL:
                this.percentComplete = percentComplete;
                break;
        }
        
        //make sure timer is setup here
        switch(type)
        {   
            case SCROLL_HORIZONTAL_EAST_REPEAT:
            case SCROLL_HORIZONTAL_WEST_REPEAT:
            case SCROLL_VERTICAL_NORTH_REPEAT:
            case SCROLL_VERTICAL_SOUTH_REPEAT:
                break;
        }
        
        if (timer != null)
        {
            timer.update(timeDeduction);

            //if time has passed we will reset time or start next layer
            if (timer.hasTimePassed()) 
            {   
                if (timer.getReset() > -1 && !getPause())
                {
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    timer.resetRemaining();
                }
            }
        }
    }
    
    public Graphics render(Graphics g, Rectangle screen) 
    {
        Graphics2D g2d = (Graphics2D)g;
        Composite originalComposite = g2d.getComposite();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, visibility));
        
        if (image != null)
        {
            g2d.drawImage(image, location.x, location.y, location.width, location.height, null);
            
            switch(type)
            {
                case SCROLL_HORIZONTAL_EAST_REPEAT:
                case SCROLL_HORIZONTAL_WEST_REPEAT:
                    g2d.drawImage(image, location.x - location.width, location.y, location.width, location.height, null);
                    g2d.drawImage(image, location.x + location.width, location.y, location.width, location.height, null);
                    break;
                
                case SCROLL_VERTICAL_NORTH_REPEAT:
                case SCROLL_VERTICAL_SOUTH_REPEAT:
                    g2d.drawImage(image, location.x, location.y - location.height, location.width, location.height, null);
                    g2d.drawImage(image, location.x, location.y + location.height, location.width, location.height, null);
                    break;
            }
        }
        
        //make sure there are options first
        if (hasOptions())
        {
            int middleX = screen.x + (screen.width  / 2);
            int middleY = screen.y + (screen.height / 2);

            double coverWindowRatio = .85;

            int menuAreaW = (int)(screen.width  * coverWindowRatio);
            int menuAreaH = (int)(screen.height * coverWindowRatio);

            Rectangle menuArea = new Rectangle(middleX - (menuAreaW/2), middleY - (menuAreaH/2), menuAreaW, menuAreaH);

            double optionAreaSpaceRatio = .20;

            int optionAreaY = (int)(menuArea.y + (menuArea.height * optionAreaSpaceRatio));
            int optionAreaH = (int)(menuArea.height - ((menuArea.height * optionAreaSpaceRatio) * 2));
            Rectangle optionArea = new Rectangle(menuArea.x, optionAreaY, menuArea.width, optionAreaH);

            int maxFontSize = 48;
            int minFontSize = 8;

            int eachOptionHeight = (int)((double)optionArea.height / (double)options.size());

            Font cachedFont = g2d.getFont();
            Stroke cachedStroke = g2d.getStroke();

            for (int fontSize = maxFontSize; fontSize >= minFontSize; fontSize--)
            {
                g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, fontSize));

                if (g2d.getFontMetrics().getHeight() < eachOptionHeight)
                    break;
            }

            Font adjustedFont = g2d.getFont();

            int menuBorderW = (int)(menuArea.width  * .1);
            int menuBorderH = (int)(menuArea.height * .1);

            BasicStroke stroke = new BasicStroke(8.0f);
            g2d.setStroke(stroke);

            if (menuColor3 != null)
            {
                g2d.setColor(menuColor3);
                g2d.fillRoundRect(menuArea.x, menuArea.y, menuArea.width, menuArea.height, menuBorderW, menuBorderH);
            }

            g2d.setColor(menuColor1);
            g2d.drawRoundRect(menuArea.x, menuArea.y, menuArea.width, menuArea.height, menuBorderW, menuBorderH);

            if (title != null && title.length() > 0)
            {
                int titleHeightArea = optionArea.y - menuArea.y;
                int titleWidth = g2d.getFontMetrics().stringWidth(title);

                if (titleHeightArea > g2d.getFontMetrics().getHeight())
                    titleHeightArea = g2d.getFontMetrics().getHeight();

                g2d.drawString(title, middleX - (titleWidth/2), menuArea.y + titleHeightArea);
            }

            for (int i=0; i < options.size(); i++)
            {
                g2d.setFont(adjustedFont);
                Rectangle r = new Rectangle(optionArea.x, optionArea.y + (i * eachOptionHeight), optionArea.width, eachOptionHeight);
                
                getOption(getKey(i)).draw(g, r, (i == index), menuColor1, menuColor2);
            }

            //return original stoke and font when done
            g2d.setStroke(cachedStroke);
            g2d.setFont(cachedFont);
            
            cachedStroke = null;
            cachedFont = null;
        }
        
        g2d.setComposite(originalComposite);
        
        originalComposite = null;
        
        g2d.setColor(Color.BLACK);
        
        switch (type)   //draw curtain here
        {
            case CURTAIN_CLOSE_HORIZONTAL:
                int fillX1 = (int)( (screen.width/2) * this.percentComplete);
                g2d.fillRect(screen.x, screen.y, fillX1, screen.height);
                g2d.fillRect(screen.x + screen.width - fillX1, screen.y, fillX1, screen.height);
                break;
                
            case CURTAIN_OPEN_HORIZONTAL:
                int fillX2 = (int)( (screen.width/2) * this.percentComplete);
                g2d.fillRect(screen.x, screen.y, (screen.width/2) - fillX2, screen.height);
                g2d.fillRect(screen.x + (screen.width/2) + fillX2, screen.y, (screen.width/2), screen.height);
                break;
                
            case CURTAIN_CLOSE_VERTICAL:
                int fillY1 = (int)( (screen.width/2) * this.percentComplete);
                g2d.fillRect(screen.x, screen.y, screen.width, fillY1);
                g2d.fillRect(screen.x, screen.y + screen.height - fillY1, screen.width, fillY1);
                break;
                
            case CURTAIN_OPEN_VERTICAL:
                int fillY2 = (int)( (screen.width/2) * this.percentComplete);
                g2d.fillRect(screen.x, screen.y, screen.width, (screen.height/2) - fillY2);
                g2d.fillRect(screen.x, screen.y + (screen.height/2) + fillY2, screen.width, (screen.height/2));
                break;
        }
        
        return (Graphics)g2d;
    }
    
    public void dispose()
    {
        if (sound != null)
            sound.stopSound();
        
        sound = null;
        
        if (image != null)
            image.flush();
        
        image = null;
        
        if (options != null)
        {
            for (Object key : options.keySet().toArray())
            {
                getOption(key).dispose();
                options.put(key, null);
            }
            
            options.clear();
        }
        
        options = null;
        
        location = null;
    }
}