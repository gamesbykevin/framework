package com.gamesbykevin.framework.menu;

import com.gamesbykevin.framework.input.*;
import com.gamesbykevin.framework.resources.*;
import com.gamesbykevin.framework.resources.Disposable;
import com.gamesbykevin.framework.util.*;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedHashMap;

/**
 * This contains the proper information for this layer.
 * A menu will have multiple layers.
 * 
 * @author GOD
 */
public final class Layer implements Disposable 
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
    
    //the key representing our current option selected
    private String current;
    
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
    private String nextLayerKey;
    
    //if this layer has options a title will be rendered
    private String title;
    
    //does this layer have background sound to play
    private Audio sound;
    
    //the sound effect to play when an option is changed
    private Audio optionSound;
    
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

    //draw all of the current options to the screen
    private BufferedImage optionsImage;
    
    //temporary graphics for writing images
    private Graphics2D tmpGraphics;
    
    //our flag that will help determine if a new image is to be drawn
    private boolean renderNewOptionsImage = true;
    
    //y-coordinate to start drawing the options
    private int startOptionsY;
    
    //the original composite that determines transparency when rendering our Layer
    private Composite original;
    
    //the default option container ratio
    private static final float OPTION_CONTAINER_RATIO_DEFAULT = .85F;
    
    //if the layer contains options how big is the option container
    private float optionContainerRatio = OPTION_CONTAINER_RATIO_DEFAULT;
    
    public Layer(Type type, Rectangle screen)
    {
        this.screen = screen;
        
        this.options = new LinkedHashMap<>();
        
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
     * Reset Timer and Layer progress
     */
    protected void reset()
    {
        if (timer != null)
        {
            //reset timer
            timer.reset();
            
            //reset progress
            setProgress(0);
        }
    }
    
    /**
     * Assign a Timer 
     * @param timer 
     */
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
    
    protected void setOptionSound(final Audio optionSound)
    {
        this.optionSound = optionSound;
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
     * After this layer is complete is there another layer we need to move to.
     * 
     * @param nextLayerKey 
     */
    protected void setNextLayer(final Object nextLayerKey)
    {
        this.nextLayerKey = nextLayerKey.toString();
    }
    
    protected Object getNextLayerKey()
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
     * @throws Exception
     */
    protected void add(Object key, Option option)
    {
        options.put(key, option);
    }
    
    /**
     * If the mouse location is within the boundary of any Option
     * set that as the current Option which will in turn highlight
     * 
     * @param location 
     */
    protected void setCurrent(final Point location)
    {
        for (Object key : options.keySet().toArray())
        {
            //only check if there is a change
            if (key != getCurrent())
            {
                //is the mouse location within the boundary of this option
                if (options.get(key).hasBoundary(location, this.optionContainerArea.x, this.startOptionsY))
                {
                    setCurrent(key);
                    setHighlighted();
                    break;
                }
            }
        }
    }
    
    /**
     * Set the key of the current Option
     * @param current 
     */
    protected void setCurrent(final Object current)
    {
        this.current = current.toString();
    }
    
    /**
     * Does this Layer contain options
     * @return boolean
     */
    protected boolean hasOptions()
    {
        return (!options.isEmpty());
    }
    
    protected Option getOption(Object key)
    {
        return getOption(key.toString());
    }
    
    private Option getOption(final String key)
    {
        return options.get(key);
    }
    
    /**
     * Get the current option
     * @return 
     */
    private Option getOption()
    {
        return getOption(getCurrent());
    }
    
    /**
     * Gets the key of the current index in our hash map
     * @return Object
     */
    private Object getCurrent()
    {
        return this.current;
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
    protected void update(final Menu menu, final Mouse mouse, final Keyboard keyboard, final Rectangle screen, final long time) throws Exception
    {
        //make sure we aren't forced to view this layer
        if (!getForce())
        {
            //if options exist and the current option is not set for this Layer
            if (hasOptions() && getCurrent() == null)
                setHighlighted();
            
            //check for input to skip to the next layer
            if (keyboard.isKeyPressed() || mouse.isMousePressed())
            {
                //if we have the next Layer set
                if (getNextLayerKey() != null)
                {
                    //set the new Layer
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    //if options exist
                    if (hasOptions())
                    {
                        //get the current option
                        Option option = getOption();
                        
                        //if the mouse was pressed
                        if (mouse.isMousePressed())
                        {
                            //does the option exist
                            if (option != null)
                            {
                                //reset keyboard events
                                keyboard.reset();
                                
                                //reset mouse events
                                mouse.reset();
                                
                                //if this option has the next layer set
                                if (option.getKeyLayer() != null)
                                {
                                    //set new Layer
                                    menu.setLayer(option.getKeyLayer());
                                }
                                else
                                {
                                    //move to the next selection
                                    option.next();
                                    
                                    //highlight the new selection
                                    setHighlighted();
                                    
                                    //play sound effect since option changed
                                    optionSound.play(false);
                                }
                            }
                        }

                        if (keyboard.isKeyPressed())
                        {
                            if (keyboard.hasKeyPressed(KeyEvent.VK_ENTER))
                            {
                                //reset keyboard events
                                keyboard.reset();
                                
                                //reset mouse events
                                mouse.reset();
                                
                                //if this option does not contain the next layer
                                if (option.getKeyLayer() == null)
                                {
                                    //change the option to the next selection
                                    option.next();
                                    
                                    //highlight the new selection
                                    setHighlighted();
                                    
                                    //play sound effect since option changed
                                    optionSound.play(false);
                                }
                                else
                                {
                                    //set the new Layer
                                    menu.setLayer(option.getKeyLayer());
                                }
                            }

                            if (keyboard.hasKeyPressed(KeyEvent.VK_UP))
                                setNextOption(true);
                            
                            if (keyboard.hasKeyPressed(KeyEvent.VK_DOWN))
                                setNextOption(false);
                        }
                    }
                }

                //reset keyboard and mouse events
                keyboard.reset();
                mouse.reset();
            }
            else
            {
                if (hasOptions())
                {
                    if (mouse.hasMouseMoved())
                    {
                        //mouse has moved so check for new option highlighted
                        setCurrent(mouse.getLocation());
                        
                        //reset keyboard and mouse events
                        keyboard.reset();
                        mouse.reset();
                    }
                }
            }
        }
        else
        {
            //since we are forced to view the layer we don't want any keyboard/mouse events to carry over to the next layer
            keyboard.reset();
            mouse.reset();
        }
        
        //the timer progress
        float percent = 1;
        
        if (timer != null)
            percent = timer.getProgress();
        
        //set the progress
        setProgress(percent);
        
        //is timer setup here
        if (timer != null)
        {
            //update timer
            timer.update(time);

            //if time has passed
            if (timer.hasTimePassed())
            {
                //if the Layer does not have pause enabled
                if (!getPause())
                {
                    //set the new layer
                    menu.setLayer(getNextLayerKey());
                }
                else
                {
                    //reset the timer
                    timer.reset();
                }
            }
        }
    }
    
    private void setProgress(float percent)
    {
        if (percent > 1)
            percent = 1;
        if (percent < 0)
            percent = 0;
        
        switch (type)
        {
            case FADE_IN:
                setVisibility(percent);
                break;

            case FADE_OUT:
                setVisibility(1 - percent);
                break;

            case SCROLL_HORIZONTAL_EAST:
            case SCROLL_HORIZONTAL_EAST_REPEAT:
                location.x = screen.x - (int)((1 - percent) * screen.width);
                break;

            case SCROLL_HORIZONTAL_WEST:
            case SCROLL_HORIZONTAL_WEST_REPEAT:
                location.x = screen.x + screen.width - (int)(percent * screen.width);
                break;

            case SCROLL_VERTICAL_NORTH:
            case SCROLL_VERTICAL_NORTH_REPEAT:
                location.y = screen.y + screen.height - (int)(percent * screen.height);
                break;

            case SCROLL_VERTICAL_SOUTH:
            case SCROLL_VERTICAL_SOUTH_REPEAT:
                location.y = screen.y - (int)((1 - percent) * screen.height);
                break;
                
            case CURTAIN_CLOSE_HORIZONTAL:
            case CURTAIN_OPEN_HORIZONTAL:
            case CURTAIN_CLOSE_VERTICAL:
            case CURTAIN_OPEN_VERTICAL:
                this.percentComplete = percent;
                break;
        }
    }
    
    /**
     * Get the background audio. <br><br>
     * If it does not exist null is returned.
     * @return Audio
     */
    protected Audio getSound()
    {
        return this.sound;
    }
    
    /**
     * From the current Option locate the next Option key
     * If the current Option key is not set default the first
     * 
     * @param previous Do we want the previous Option? If not get the next Option
     */
    private void setNextOption(final boolean previous)
    {
        //if the current Option has not been selected yet set the first one
        if (getCurrent() == null)
        {
            setCurrent(options.keySet().toArray()[0]);
            return;
        }
        
        boolean modified = false;
        
        for (int i=0; i < options.size(); i++)
        {
            //have we located the current position of our key
            if (options.keySet().toArray()[i] == getCurrent())
            {
                //do we want the previous Option key
                if (previous)
                {
                    //make sure we are within the bounds of the array
                    if (i - 1 >= 0)
                    {
                        //get the previous
                        setCurrent(options.keySet().toArray()[i - 1]);
                        
                        //a change has been made
                        modified = true;
                    }
                    else
                    {
                        //get the last
                        setCurrent(options.keySet().toArray()[options.keySet().toArray().length - 1]);
                        
                        //a change has been made
                        modified = true;
                    }
                }
                else
                {
                    //make sure we are within the bounds of the array
                    if (i + 1 < options.keySet().toArray().length)
                    {
                        //get the next
                        setCurrent(options.keySet().toArray()[i + 1]);
                        
                        //a change has been made
                        modified = true;
                    }
                    else
                    {
                        //get the first
                        setCurrent(options.keySet().toArray()[0]);
                        
                        //a change has been made
                        modified = true;
                    }
                }
                
                //now that we found key exit loop
                break;
            }
        }
        
        if (modified)
        {
            //also make sure it is marked as highlighted
            setHighlighted();
        }
    }
    
    private void resetOptionsImage()
    {
        //flag that new image needs to be drawn
        renderNewOptionsImage = true;
    }
    
    /**
     * Set all options not highlighted except for the current option
     */
    private void setHighlighted()
    {
        //reset options image
        resetOptionsImage();
        
        //mark all options as not highlighted
        for (Option option : options.values())
        {
            option.setHighlighted(false);
        }
        
        if (getCurrent() == null)
            setNextOption(false);
        
        //mark the current option as highlighted
        getOption(getCurrent()).setHighlighted(true);
    }
    
    /**
     * Set the size of the option container.
     * @param optionContainerRatio Percentage of parent. Must be greater than 0 and less than or equal to 1.0 
     * @throws Exception Exception will be throw if ratio is less than or equal to 0 or ratio is greater then 1.0
     */
    protected void setOptionContainerRatio(final float optionContainerRatio) throws Exception
    {
        if (optionContainerRatio <= 0)
            throw new Exception("Ratio must be greater than 0.00");
        
        if (optionContainerRatio > 1.0)
            throw new Exception("Ratio must be less than or equal to 1.0");
        
        this.optionContainerRatio = optionContainerRatio;
    }
    
    protected void render(Graphics2D graphics, Rectangle screen)
    {
        if (original == null)
            original = graphics.getComposite();
        
        //set the transparency
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, visibility));
        
        if (image != null)
        {
            graphics.drawImage(image, location.x, location.y, location.width, location.height, null);
            
            switch(type)
            {
                case SCROLL_HORIZONTAL_EAST_REPEAT:
                case SCROLL_HORIZONTAL_WEST_REPEAT:
                    graphics.drawImage(image, location.x - location.width, location.y, location.width, location.height, null);
                    graphics.drawImage(image, location.x + location.width, location.y, location.width, location.height, null);
                    break;
                
                case SCROLL_VERTICAL_NORTH_REPEAT:
                case SCROLL_VERTICAL_SOUTH_REPEAT:
                    graphics.drawImage(image, location.x, location.y - location.height, location.width, location.height, null);
                    graphics.drawImage(image, location.x, location.y + location.height, location.width, location.height, null);
                    break;
            }
        }
        
        //do we have options for this Layer
        if (hasOptions())
        {
            //draw the options container first
            drawOptionContainer(graphics);
            
            //then draw the options
            drawOptions(graphics);
        }
        
        //set the original composite back
        graphics.setComposite(original);
        
        //store temp width and height
        int width, height;
        
        //draw curtain(s) here
        switch (type)
        {
            case CURTAIN_CLOSE_HORIZONTAL:
                width = (int)( (screen.width/2) * this.percentComplete);
                graphics.setColor(Color.BLACK);
                graphics.fillRect(screen.x, screen.y, width, screen.height);
                graphics.fillRect(screen.x + screen.width - width, screen.y, width, screen.height);
                break;
                
            case CURTAIN_OPEN_HORIZONTAL:
                width = (int)( (screen.width/2) * this.percentComplete);
                graphics.setColor(Color.BLACK);
                graphics.fillRect(screen.x, screen.y, (screen.width/2) - width, screen.height);
                graphics.fillRect(screen.x + (screen.width/2) + width, screen.y, (screen.width/2), screen.height);
                break;
                
            case CURTAIN_CLOSE_VERTICAL:
                height = (int)( (screen.width/2) * this.percentComplete);
                graphics.setColor(Color.BLACK);
                graphics.fillRect(screen.x, screen.y, screen.width, height);
                graphics.fillRect(screen.x, screen.y + screen.height - height, screen.width, height);
                break;
                
            case CURTAIN_OPEN_VERTICAL:
                height = (int)( (screen.width/2) * this.percentComplete);
                graphics.setColor(Color.BLACK);
                graphics.fillRect(screen.x, screen.y, screen.width, (screen.height/2) - height);
                graphics.fillRect(screen.x, screen.y + (screen.height/2) + height, screen.width, (screen.height/2));
                break;
        }
    }
    
    /**
     * Draw all the options as 1 image
     * @param graphics 
     */
    private void drawOptions(final Graphics2D graphics)
    {
        if (optionsImage == null)
        {
            //create transparent image
            optionsImage = new BufferedImage(optionContainerArea.width, (graphics.getFontMetrics().getHeight() * options.size()), BufferedImage.TYPE_INT_ARGB);
        }
        
        if (renderNewOptionsImage)
        {
            //set flag to false
            renderNewOptionsImage = false;
            
            //get pixel data
            final int[] pixels = ((DataBufferInt)optionsImage.getRaster().getDataBuffer()).getData();
            
            //remove all pixel data
            for (int i=0; i < pixels.length; i++)
            {
                pixels[i] = 0;
            }
            
            //get our graphics object for this image that we will draw to
            tmpGraphics = optionsImage.createGraphics();
            
            //store the font as well
            tmpGraphics.setFont(graphics.getFont());
            
            //keep track so we can calculate the y-coordinates
            int count = 0;
            
            //draw each option in this Layer
            for (Option option : options.values())
            {
                //if the boundary is not set yet we need to set it before drawing option
                if (option.getBoundary() == null)
                    option.setBoundary(new Rectangle(0, (tmpGraphics.getFontMetrics().getHeight() * count), optionContainerArea.width, tmpGraphics.getFontMetrics().getHeight()));
                
                option.render(tmpGraphics, OPTION_BORDER_COLOR, OPTION_TEXT_COLOR);
                
                //keep track so we can calculate the y-coordinates
                count++;
            }
        }
        
        //draw the options
        graphics.drawImage(optionsImage, optionContainerArea.x, startOptionsY, null);
    }
    
    /**
     * Draws the background for the options (if they exist) including the title as well.
     * 
     * @param graphics Graphics2D we want to render to
     * @return Graphics 
     */
    private void drawOptionContainer(final Graphics2D graphics)
    {
        //if we haven't created our option container background
        if (optionContainerImage == null)
        {
            //center of screen
            int middleX = screen.x + (screen.width  / 2);
            int middleY = screen.y + (screen.height / 2);

            //the width and height of this container will be a % of its parent
            int menuAreaW = (int)(screen.width  * optionContainerRatio);
            int menuAreaH = (int)(screen.height * optionContainerRatio);

            //store coordinates for option container
            optionContainerArea = new Rectangle(middleX - (menuAreaW/2), middleY - (menuAreaH/2), menuAreaW, menuAreaH);

            //create transparent image
            optionContainerImage = new BufferedImage(optionContainerArea.width, optionContainerArea.height, BufferedImage.TYPE_INT_ARGB);
            
            //get our graphics object for this image that we will draw to
            tmpGraphics = optionContainerImage.createGraphics();

            //store the font as well
            tmpGraphics.setFont(graphics.getFont());
            
            // the width of the rounded corners will be 15% of the container dimensions
            final int arcWidth  = (int)(optionContainerImage.getWidth()  * .15);
            final int arcHeight = (int)(optionContainerImage.getHeight() * .15);
            
            //set stroke so outline drawn is thick
            tmpGraphics.setStroke(new BasicStroke(10.0f));

            //if back ground color exists
            if (OPTION_BACKGROUND_COLOR != null)
            {
                //fill background of our container with specified color
                tmpGraphics.setColor(OPTION_BACKGROUND_COLOR);
                tmpGraphics.fillRoundRect(0, 0, optionContainerImage.getWidth(), optionContainerImage.getHeight(), arcWidth, arcHeight);
            }

            //draw outline around container
            tmpGraphics.setColor(OPTION_BORDER_COLOR);
            tmpGraphics.drawRoundRect(0, 0, optionContainerImage.getWidth() - 1, optionContainerImage.getHeight() - 1, arcWidth, arcHeight);

            //if the title exists
            if (title != null && title.length() > 0)
            {
                //the appropriate font size
                float fontSize = Menu.getFontSize(title, optionContainerImage.getWidth(), graphics) - 4;
                
                //now that the appropriate font size has been found set it
                tmpGraphics.setFont(graphics.getFont().deriveFont(Font.BOLD, fontSize));
                
                //middle x coordinate of this image
                middleX = (optionContainerImage.getWidth() / 2);
                
                //get the pixel width of the text so we can calculate the appropriate x coordinate
                final int textWidth = tmpGraphics.getFontMetrics().stringWidth(title);

                //draw the title in its appropriate place
                tmpGraphics.drawString(title, middleX - (textWidth/2), tmpGraphics.getFontMetrics().getHeight());
            }
            
            //store the starting y position of the options
            startOptionsY = optionContainerArea.y + (int)(tmpGraphics.getFontMetrics().getHeight() * 1.25);
        }
        else
        {
            //the image has already been created so we just need to draw it
            graphics.drawImage(optionContainerImage, optionContainerArea.x, optionContainerArea.y, null);
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
        
        if (optionSound != null)
            optionSound.stopSound();
        
        optionSound = null;
        
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
        
        if (optionsImage != null)
            optionsImage.flush();
        
        optionsImage = null;
    }
}