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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * This contains the proper information for this layer.
 * A menu will have multiple layers.
 * 
 * @author GOD
 */
public abstract class Layer 
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
    
    //current selected option, default 0
    private int index = 0;
    
    //options assigned to this layer
    private LinkedHashMap<Object, Option> options;
    
    //time mechanism for this layer
    private Timer timer;
    
    //window in which layer will be drawn
    private Rectangle location;
    
    //window of the full screen
    private Rectangle screen;
    
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
    
    //if this layer has options a title will be rendered
    private String title;
    
    //does this layer have background sound to play
    private Audio sound;

    //option container border color
    private Color OPTION_BORDER_COLOR = Color.WHITE; 
    
    //text color for the options
    private static final Color OPTION_TEXT_COLOR = Color.BLACK;
    
    //option container background color
    private static final Color OPTION_BACKGROUND_COLOR = Color.BLUE;
    
    //the area all options will be drawn within
    private Rectangle optionContainerArea;
    
    //store the background of the container
    private BufferedImage optionContainerImage;
    
    //y-coordinate to start drawing the options
    private int startOptionsY;
    
    //the original composite that determines transparency when rendering our Layer
    private Composite original;
    
    public Layer(Type type, Rectangle screen)
    {
        this.screen = screen;
        
        options = new LinkedHashMap<>();
        
        this.type = type;
        
        this.setHighlighted();
        
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
    
    /**
     * Once the layer finishes, do we pause this Layer
     * @param pause 
     */
    protected void setPause(final boolean pause)
    {
        this.pause = pause;
    }
    
    /**
     * Do we force the user to watch this Layer until finish
     * @param force 
     */
    protected void setForce(final boolean force)
    {
        this.force = force;
    }
    
    /**
     * Reset Timer
     */
    public void reset()
    {
        if (timer != null)
            timer.reset();
    }
    
    protected void setTimer(final Timer timer)
    {
        this.timer = timer;
    }
    
    /**
     * Does this Layer contain a sound we should be playing
     * @param sound 
     */
    protected void setSound(final Audio sound)
    {
        this.sound = sound;
    }
    
    /**
     * Does this layer contain a background image
     * @param image 
     */
    protected void setImage(final Image image)
    {
        this.image = image;
    }
    
    /**
     * After this layer is complete is 
     * there another layer we need to move to.
     * @param nextLayerKey 
     */
    protected void setNextLayerKey(final Object nextLayerKey)
    {
        this.nextLayerKey = nextLayerKey;
    }
    
    public Object getNextLayerKey()
    {
        return this.nextLayerKey;
    }
    
    protected void setTitle(final String title)
    {
        this.title = title;
    }
    
    private void setVisibility(final float visibility)
    {
        this.visibility = visibility;
    }
    
    /**
     * Add option to Layer
     * 
     * @param key Unique identifier for the Option parameter
     * @param option The option we want to add to the Layer
     */
    protected void add(Object key, Option option)
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
            
            if (tmp.hasBoundary(location))
            {
                tmp.setHighlighted(true);
                
                this.index = i;
            }
            else
            {
                tmp.setHighlighted(false);
            }
        }
    }
    
    public Option getOption(final Point location)
    {
        for (int i=0; i < options.size(); i++)
        {
            Option tmp = getOption(getKey(i));
            
            if (tmp.hasBoundary(location))
                return tmp;
        }
        
        return null;
    }
    
    /**
     * Does this Layer contain options
     * @return boolean
     */
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
    private Object getKey(final int index)
    {
        if (!hasOptions())
            return null;
        
        return options.keySet().toArray()[index];
    }
    
    /**
     * Update the layer. 
     * 
     * @param menu Our Menu that contains this layer
     * @param mouse Mouse Input
     * @param keyboard Keyboard Input
     * @param screen Window Layer is contained within
     * @param timeDeduction Time to deduct from timer
     */
    public void update(final Menu menu, final Mouse mouse, final Keyboard keyboard, final Rectangle screen, final long time) 
    {
        //make sure we aren't forced to view this layer
        if (!getForce())
        {
            //check for input to skip to the next layer
            if (keyboard.isKeyPressed() || mouse.isMousePressed())
            {
                //if we have no options and we have the next layer
                if (getNextLayerKey() != null && !hasOptions())
                {
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    Option option;

                    //if the mouse was pressed
                    if (mouse.isMousePressed())
                    {
                        option = getOption(mouse.getLocation());

                        //does the option exist
                        if (option != null)
                        {
                            if (mouse.isMousePressed())
                            {   
                                //move to next option selection in this option or possibly change layer
                                if (option.getKeyLayer()== null)
                                {
                                    option.next();
                                }
                                else
                                {
                                    menu.setLayer(option.getKeyLayer());
                                    
                                    //since we are possibly re-selecting a layer reset the time
                                    menu.resetLayer();
                                }
                            }
                        }
                    }

                    if (keyboard.hasKeyPressed(KeyEvent.VK_ENTER) && hasOptions())
                    {
                        option = getOption();

                        if (option.getKeyLayer() == null)
                        {
                            option.next();
                        }
                        else
                        {
                            menu.setLayer(option.getKeyLayer());
                            
                            //since we are possibly re-selecting a layer reset the time
                            menu.resetLayer();
                        }
                    }

                    if (keyboard.hasKeyPressed(KeyEvent.VK_UP))
                        index--;
                    
                    if (keyboard.hasKeyPressed(KeyEvent.VK_DOWN))
                        index++;

                    if (index < 0)
                        index = options.size() - 1;
                    if (index >= options.size())
                        index = 0;
                    
                    setHighlighted();
                }

                keyboard.reset();
                mouse.reset();
            }
            else
            {
                if (mouse.hasMouseMoved() && hasOptions())
                {
                    //highlight the current Option
                    setIndex(mouse.getLocation());
                }
            }
        }
        
        float tmpPercentComplete = 1;
        
        if (timer != null)
            tmpPercentComplete = timer.getProgress();
        
        if (tmpPercentComplete > 1)
            tmpPercentComplete = 1;
        if (tmpPercentComplete < 0)
            tmpPercentComplete = 0;
        
        switch (type)
        {
            case FADE_IN:
                setVisibility(tmpPercentComplete);
                break;

            case FADE_OUT:
                setVisibility(1 - tmpPercentComplete);
                break;

            case SCROLL_HORIZONTAL_EAST:
            case SCROLL_HORIZONTAL_EAST_REPEAT:
                location.x = screen.x - (int)((1 - tmpPercentComplete) * screen.width);
                break;

            case SCROLL_HORIZONTAL_WEST:
            case SCROLL_HORIZONTAL_WEST_REPEAT:
                location.x = screen.x + screen.width - (int)(tmpPercentComplete * screen.width);
                break;

            case SCROLL_VERTICAL_NORTH:
            case SCROLL_VERTICAL_NORTH_REPEAT:
                location.y = screen.y + screen.height - (int)(tmpPercentComplete * screen.height);
                break;

            case SCROLL_VERTICAL_SOUTH:
            case SCROLL_VERTICAL_SOUTH_REPEAT:
                location.y = screen.y - (int)((1 - tmpPercentComplete) * screen.height);
                break;
                
            case CURTAIN_CLOSE_HORIZONTAL:
            case CURTAIN_OPEN_HORIZONTAL:
            case CURTAIN_CLOSE_VERTICAL:
            case CURTAIN_OPEN_VERTICAL:
                this.percentComplete = tmpPercentComplete;
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
            timer.update(time);

            //if time has passed we will reset time or start next layer
            if (timer.hasTimePassed()) 
            {
                if (timer.getReset() > -1 && !getPause())
                {
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    timer.reset();
                }
            }
        }
    }
    
    private void setHighlighted()
    {
        for (int i=0; i < options.size(); i++)
        {
            if (getKey(index) == getKey(i))
            {
                getOption(getKey(i)).setHighlighted(true);
            }
            else
            {
                getOption(getKey(i)).setHighlighted(false);
            }
        }
    }
    
    public Graphics render(Graphics2D g2d, Rectangle screen) throws Exception 
    {
        if (original == null)
            original = g2d.getComposite();
        
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
        
        //do we have options for this Layer
        if (hasOptions())
        {
            drawOptionContainer(g2d);
            
            int count = 0;
            
            //draw each option in this Layer
            for (Option option : options.values())
            {
                //if the boundary is not set yet we need to set it before drawing option
                if (option.getBoundary() == null)
                    option.setBoundary(new Rectangle(optionContainerArea.x, startOptionsY + (g2d.getFontMetrics().getHeight() * count), optionContainerArea.width, g2d.getFontMetrics().getHeight()));
                
                option.render(g2d, OPTION_BORDER_COLOR, OPTION_TEXT_COLOR);
                
                count++;
            }
        }
        
        //set the original composite back
        g2d.setComposite(original);
        
        int width, height;
        
        //draw curtain(s) here
        switch (type)
        {
            case CURTAIN_CLOSE_HORIZONTAL:
                width = (int)( (screen.width/2) * this.percentComplete);
                g2d.setColor(Color.BLACK);
                g2d.fillRect(screen.x, screen.y, width, screen.height);
                g2d.fillRect(screen.x + screen.width - width, screen.y, width, screen.height);
                break;
                
            case CURTAIN_OPEN_HORIZONTAL:
                width = (int)( (screen.width/2) * this.percentComplete);
                g2d.setColor(Color.BLACK);
                g2d.fillRect(screen.x, screen.y, (screen.width/2) - width, screen.height);
                g2d.fillRect(screen.x + (screen.width/2) + width, screen.y, (screen.width/2), screen.height);
                break;
                
            case CURTAIN_CLOSE_VERTICAL:
                height = (int)( (screen.width/2) * this.percentComplete);
                g2d.setColor(Color.BLACK);
                g2d.fillRect(screen.x, screen.y, screen.width, height);
                g2d.fillRect(screen.x, screen.y + screen.height - height, screen.width, height);
                break;
                
            case CURTAIN_OPEN_VERTICAL:
                height = (int)( (screen.width/2) * this.percentComplete);
                g2d.setColor(Color.BLACK);
                g2d.fillRect(screen.x, screen.y, screen.width, (screen.height/2) - height);
                g2d.fillRect(screen.x, screen.y + (screen.height/2) + height, screen.width, (screen.height/2));
                break;
        }
        
        return (Graphics)g2d;
    }
    
    /**
     * Draws the background for the options (if they exist) including the title as well.
     * 
     * @param g2d Graphics2D we want to render to
     * @return Graphics 
     */
    private Graphics drawOptionContainer(Graphics2D g2d)
    {
        //if we haven't created our option container background
        if (optionContainerImage == null)
        {
            //center of screen
            int middleX = screen.x + (screen.width  / 2);
            int middleY = screen.y + (screen.height / 2);

            //option container will cover 85% of window
            double coverWindowRatio = .85;

            //the width and height of this container will be 85% of its parent
            int menuAreaW = (int)(screen.width  * coverWindowRatio);
            int menuAreaH = (int)(screen.height * coverWindowRatio);

            //store coordinates for option container
            optionContainerArea = new Rectangle(middleX - (menuAreaW/2), middleY - (menuAreaH/2), menuAreaW, menuAreaH);

            //create transparent image
            optionContainerImage = new BufferedImage(optionContainerArea.width, optionContainerArea.height, BufferedImage.TYPE_INT_ARGB);
            
            //get our graphics object for this image that we will draw to
            Graphics2D tmpG2D = optionContainerImage.createGraphics();

            // the width of the rounded corners will be 15% of the container dimensions
            final int arcWidth  = (int)(optionContainerImage.getWidth()  * .15);
            final int arcHeight = (int)(optionContainerImage.getHeight() * .15);
            
            //set stroke so outline drawn is thick
            tmpG2D.setStroke(new BasicStroke(8.0f));

            //if back ground color exists
            if (OPTION_BACKGROUND_COLOR != null)
            {
                //fill background of our container with specified color
                tmpG2D.setColor(OPTION_BACKGROUND_COLOR);
                tmpG2D.fillRoundRect(0, 0, optionContainerImage.getWidth(), optionContainerImage.getHeight(), arcWidth, arcHeight);
            }

            //draw outline around container
            tmpG2D.setColor(OPTION_BORDER_COLOR);
            tmpG2D.drawRoundRect(0, 0, optionContainerImage.getWidth() - 1, optionContainerImage.getHeight() - 1, arcWidth, arcHeight);

            //if the title exists
            if (title != null && title.length() > 0)
            {
                //the appropriate font
                float fontSize = Menu.getFontSize(title, optionContainerImage.getWidth(), g2d);
                
                //now that the appropriate font size has been found set it
                tmpG2D.setFont(g2d.getFont().deriveFont(Font.BOLD, fontSize));
                
                //middle x coordinate of this image
                middleX = (optionContainerImage.getWidth() / 2);
                
                //get the pixel width of the text so we can calculate the appropriate x coordinate
                final int textWidth = tmpG2D.getFontMetrics().stringWidth(title);

                //draw the title in its appropriate place
                tmpG2D.drawString(title, middleX - (textWidth/2), tmpG2D.getFontMetrics().getHeight());
            }
            
            //store the starting y position of the options
            startOptionsY = optionContainerArea.y + (int)(tmpG2D.getFontMetrics().getHeight() * 1.25);
        }
        else
        {
            //the image has already been created so we just need to draw it
            g2d.drawImage(optionContainerImage, optionContainerArea.x, optionContainerArea.y, null);
        }
        
        return g2d;
    }
    
    /**
     * Free up resources
     */
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
        
        if (optionContainerImage != null)
            optionContainerImage.flush();
        
        optionContainerImage = null;
    }
}