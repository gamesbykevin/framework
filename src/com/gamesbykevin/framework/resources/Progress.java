package com.gamesbykevin.framework.resources;

import com.gamesbykevin.framework.menu.Menu;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Progress implements Disposable
{
    //we will draw an image to reduce load on machine
    private BufferedImage image;
    
    //the decimal representing our progress
    private double progress;
    
    //the goal will be the logic used to determine Progress isComplete()
    private int goal  = 0;
    
    //the count will be the logic used to track how close we are to goal
    private int count = 0;
    
    //text to be displayed to the user
    private String description = null;
    
    //the location where the dynamic progress will be displayed
    private Point progressTextLocation;
    
    //the dimensions the progress bar will be contained within
    private Rectangle progressBarDimension;
    
    //the text width will be 75% of the container width
    private static final double TEXT_CONTAINER_WIDTH_RATIO = .75;
    
    //extra text to include with loading description
    private static final String EXTRA_TEXT = "99%";
    
    //store the font in this object so when the progress is rendered everything is consistent
    private Font font;
    
    //the area the progress will be drawn within
    private Rectangle screen;
    
    /**
     * Create new Progress tracker with the goal set
     * 
     * @param goal 
     */
    public Progress(final int goal)
    {
        this.goal = goal;
    }
    
    /**
     * Set the screen where the progress will be shown
     * @param screen 
     */
    public void setScreen(final Rectangle screen)
    {
        this.screen = screen;
    }
    
    public Rectangle getScreen()
    {
        return this.screen;
    }
    
    @Override
    public void dispose()
    {
        if (image != null)
            image.flush();
        
        this.image = null;
        this.description = null;
        this.progressTextLocation = null;
        this.progressBarDimension = null;
        this.font = null;
        this.screen = null;
    }
    
    /**
     * Set Text to display when render is called, can be null.<br>
     * Note: You can only set the description once.
     * @param description The description to display
     * @throws Exception if the description was already set previously and exception will be thrown.
     */
    public void setDescription(final String description) throws Exception
    {
        if (this.description != null)
            throw new Exception("Description has already been set.");
        
        this.description = description;
    }
    
    /**
     * Get the description that will be displayed when Progress is rendered
     * @return String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Get the goal we have set
     * @return int
     */
    public int getGoal()
    {
        return goal;
    }
    
    /**
     * Set the goal we want.
     * @param goal The goal we are trying to reach
     */
    public void changeGoal(final int goal)
    {
        this.goal = goal;
    }
    
    /**
     * Where are we currently at
     * @return int
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * Increase the progress towards our goal by 1
     */
    public void increase()
    {
        setCount(getCount() + 1);
    }
    
    /**
     * Set the progress towards our goal
     * @param count 
     */
    public void setCount(final int count)
    {
        this.count = count;
    }
    
    /**
     * Make the progress 100% complete
     */
    public void setComplete()
    {
        count = goal;
    }
    
    /**
     * Get the progress towards reaching the goal in the form of a decimal ranging from 0.0 to 1.0
     * @return double
     */
    public double getProgress()
    {
        progress = ((double)getCount() / (double)getGoal());
        
        return progress;
    }
    
    /**
     * Has the count reached the goal
     * @return boolean
     */
    public boolean isComplete()
    {
        return (getCount() >= getGoal());
    }
    
    /**
     * Draw the progress within the set screen.<br>
     * If the screen is not set a null exception will be thrown
     * @param graphics 
     */
    public void render(Graphics graphics)
    {
        render(graphics, getScreen());
    }
    
    /**
     * Draw the progress within Rectangle container
     * @param graphics
     * @param screen
     */
    public void render(Graphics graphics, Rectangle screen)
    {
        //create image of Progress information as all information will be static except getProgress()
        if (image == null)
        {
            //create an image of the same size as Rectangle screen
            this.image = new BufferedImage(screen.width, screen.height, BufferedImage.TYPE_INT_ARGB);
            
            //get Graphics object so we can write to image
            Graphics imageGraphics = image.createGraphics();
            
            //set the font style the same
            imageGraphics.setFont(graphics.getFont());
            
            //make the background the size of our Rectangle and fill it with Black Color
            imageGraphics.setColor(Color.BLACK);
            imageGraphics.fillRect(screen.x, screen.y, screen.width, screen.height);

            //we will display the information in the middle
            final int middleX = screen.x + (screen.width /2);
            final int middleY = screen.y + (screen.height/2);

            //default description
            String loadingDesc = "Loading ";

            //if a description exists we will use it
            if (getDescription() != null && getDescription().trim().length() > 0)
                loadingDesc = getDescription() + " ";

            //add extra text to loadingDesc so the overall result will appear centered
            float fontSize = Menu.getFontSize(loadingDesc + EXTRA_TEXT, (int)(screen.width * TEXT_CONTAINER_WIDTH_RATIO), imageGraphics);
            
            //correct font size has been found, so we set it
            imageGraphics.setFont(graphics.getFont().deriveFont(fontSize));

            //now get the font height
            final int fontHeight = imageGraphics.getFontMetrics().getHeight();
            
            //also store the font for consistency
            this.font = imageGraphics.getFont();
            
            //get the pixel width of our full description including extra text
            final int fullTextWidth = imageGraphics.getFontMetrics().stringWidth(loadingDesc + EXTRA_TEXT);

            //get the pixel width of just the text description not including extra text
            final int textWidth = imageGraphics.getFontMetrics().stringWidth(loadingDesc);
            
            //make the appropriate calculations do the display text will appear in the center
            final int drawX = middleX - (fullTextWidth / 2);
            final int drawY = middleY;

            //write the loading description to image as this information will not change
            imageGraphics.setColor(Color.WHITE);
            imageGraphics.drawString(loadingDesc, drawX, drawY);

            //the location where we will draw the dynamic Progress
            progressTextLocation = new Point(drawX + textWidth, drawY);
            
            //the starting point where we will draw the progress bar
            progressBarDimension = new Rectangle(screen.x, drawY + fontHeight, screen.width, imageGraphics.getFontMetrics().getHeight());
        }
        
        //draw background loading image
        graphics.drawImage(this.image, screen.x, screen.y, screen.width, screen.height, null);
        
        //set same font and color to match background image
        graphics.setColor(Color.WHITE);
        graphics.setFont(this.font);
        
        //these display values need to be calculated on the fly as they will dynamically change
        graphics.drawString(getCompleteProgress() + "%", progressTextLocation.x, progressTextLocation.y);
        graphics.fillRect(progressBarDimension.x, progressBarDimension.y, (int)(progressBarDimension.width * getProgress()), progressBarDimension.height);
    }
    
    /**
     * Calculate the percentage complete as a percentage.
     * 
     * @return int Percent complete from 0% - 100%
     */
    private int getCompleteProgress()
    {
        int complete = (int)(getProgress() * 100);
        
        if (complete >= 100)
            complete = 100;
        
        return complete;
    }
}