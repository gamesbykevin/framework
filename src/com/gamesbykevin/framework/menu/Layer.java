package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.*;
import com.gamesbykevin.framework.resources.*;
import com.gamesbykevin.framework.util.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;

public class Layer 
{   //each Menu will contain a number of layers
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
    
    private Type type;
    private int index = 0;                              //which option is the current one selected
    //I used LinkedHashMap because it maintains order of items in map unlike HashMap
    private LinkedHashMap options = new LinkedHashMap();//options tied to this layer
    private Timer timer;                                //time mechanism for this layer
    private Rectangle location;                         //location to draw layer
    private float visibility = 1;                       //transparency of layer
    private float percentComplete = 0;                  //some Layer.Type's will need to track progress
    private boolean pause = false;                      //pause layer after time mechanism complete
    private boolean force = false;                      //force user to view layer
    private Image image;                                //does this Layer have an image as the background
    private Object nextLayerKey;                        //after layer complete which is next
    private String title;                               //if this layer has options what is the title
    private AudioResource sound;                        //does this layer have background sound

    private Color menuColor1 = Color.WHITE; //border
    private Color menuColor2 = Color.BLACK; //text
    private Color menuColor3 = Color.BLUE;  //menu background
    
    public Layer(Type type, Rectangle screen)
    {
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
    
    public void setSound(AudioResource sound)
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
    
    public void setIndex(Point mouseLocation)
    {   //if the mouse intersects the option set as current option
        for (int i=0; i < options.size(); i++)
        {
            Option tmp = getOption(getKey(i));
            
            if (tmp.hasLocation(mouseLocation))
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
    
    public Option getOption()
    {   //gets the current Option
        return getOption(getKey());
    }
    
    private Object getKey()
    {   //gets the key of the current index in our hashmap;
        return getKey(this.index);
    }
    
    private Object getKey(int index)
    {   //gets the key of a specific index in our hashmap
        if (!hasOptions())
            return null;
        
        return options.keySet().toArray()[index];
    }
    
    public void manage(Menu menu, Mouse mi, Keyboard ki, Rectangle screen, long timeDeduction) 
    {
        if (!getForce())
        {   //if the user is not forced to watch the current layer check for input to skip to the next layer
            if (ki.isKeyPressed() || mi.isMousePressed())
            {
                if (getNextLayerKey() != null && !hasOptions())
                {   //verify this option is supposed to take us to another layer in the menu and has no Options
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    Option option;

                    if (mi.isMousePressed() || mi.hasMouseMoved())
                    {
                        option = getOption(mi.getLocation());

                        if (option != null)
                        {
                            if (mi.isMousePressed())
                            {   //move to next option selection in this option or possibly change layer
                                if (option.getNextLayerKey() == null)
                                {
                                    option.next();
                                }
                                else
                                {
                                    menu.setLayer(option.getNextLayerKey());
                                    menu.resetLayer();//since we are possibly re-selecting a layer reset the time
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
                            menu.resetLayer();//since we are possibly re-selecting a layer reset the time
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

                ki.resetAllKeyEvents();
                mi.resetMouseEvents();
            }
            else
            {
                if (mi.hasMouseMoved() && hasOptions())
                {   //highlight the current Option
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

            if (timer.hasTimePassed()) 
            {   //if time has passed reset time or start next layer
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
        
        if (hasOptions())
        {   //make sure there are options first
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
        }
        
        g2d.setComposite(originalComposite);
        
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
            sound.stop();
        
        sound = null;
        
        if (image != null)
            image.flush();
        
        image = null;
        
        if (options != null)
        {
            index = 0;

            while(index < options.size())
            {
                getOption().dispose();
                index++;
            }
            
            options.clear();
        }
        
        options = null;
        
        location = null;
    }
}