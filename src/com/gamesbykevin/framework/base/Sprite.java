package com.gamesbykevin.framework.base;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Sprite extends Cell 
{
    //(x, y) = location (dx, dy) = velocity for x/y coordinates, (w, h) = width and height
    private double x = 0, y = 0, dx = 0, dy = 0, w = 0, h = 0;
    
    //switch image east/west
    private boolean horizontalFlip = false;
    
    //switch image north-south
    private boolean verticalFlip = false;
    
    //window dimensions of original image, will update every time setImage() is called
    private Rectangle imageDimensions;
    
    //image that we will be using to draw on screen
    private Image image;
    
    //sprite sheet containing all the animations for this sprite
    private SpriteSheet spriteSheet;
    
    //the rectangle representing the sprites x,y width,height coordinates
    private Rectangle rectangle;
    
    //the Point in the upper left corner and Center in the middle
    private Point point, center;
    
    public Sprite()
    {
        
    }
    
    /**
     * Create a Sprite Sheet for this Sprite so we can add animations
     */
    public void createSpriteSheet()
    {
        this.spriteSheet = new SpriteSheet();
    }
    
    public void dispose()
    {
        if (image != null)
            image.flush();

        image = null;

        spriteSheet = null;
    }
    
    public SpriteSheet getSpriteSheet()
    {
        return spriteSheet;
    }
    
    /**
     * Get the cell for this sprite
     * @return 
     */
    public Cell getCell()
    {
        return new Cell(getCol(), getRow());
    }
    
    /**
     * Get the center of the object
     * @return Center of Sprite
     */
    public Point getCenter()
    {
        if (center == null)
            center = new Point();
        
        center.x = (int)getX() + (int)(getWidth()  / 2);
        center.y = (int)getY() + (int)(getHeight() / 2);
        
        return center;
    }
    
    /**
     * Returns the location of the Sprite
     * @return Point the (x,y) coordinates where the Sprite is located
     */
    public Point getPoint()
    {
        if (point == null)
            point = new Point();
        
        point.x = (int)getX();
        point.y = (int)getY();
        
        return point;
    }
    
    public void setImage(final Image image)
    {
        this.image = image;
    }
    
    /**
     * Get the image assigned to the sprite. <br>
     * If no image exists null will be returned.
     * 
     * @return Image 
     */
    public Image getImage()
    {
        return this.image;
    }
    
    public void setVerticalFlip(final boolean verticalFlip)
    {
        this.verticalFlip = verticalFlip;
    }
    
    public boolean hasVerticalFlip()
    {
        return this.verticalFlip;
    }
    
    public void setHorizontalFlip(final boolean horizontalFlip)
    {
        this.horizontalFlip = horizontalFlip;
    }
    
    public boolean hasHorizontalFlip()
    {
        return this.horizontalFlip;
    }
    
    /**
     * Set the x, y coordinate location for this sprite
     * 
     * @param location 
     */
    public void setLocation(final Point location)
    {
        setLocation(location.x, location.y);
    }
    
    /**
     * Set the x, y coordinate location for this sprite
     * 
     * @param x
     * @param y 
     */
    public void setLocation(final double x, final double y)
    {
        setX(x);
        setY(y);
    }
    
    /**
     * Set the x, y coordinate location for this sprite
     * 
     * @param x
     * @param y 
     */
    public void setLocation(final int x, final int y)
    {
        setLocation((double)x, (double)y);
    }
    
    /**
     * Set the x coordinate location for this sprite
     * 
     * @param x 
     */
    public void setX(final double x)
    {
        this.x = x;
    }
    
    /**
     * Set the x coordinate location for this sprite
     * 
     * @param x 
     */
    public void setX(final int x)
    {
        setX((double)x);
    }
    
    /**
     * Get x coordinate location set for this sprite
     * 
     * @return int
     */
    public double getX()
    {
        return this.x;
    }
    
    /**
     * Set the y coordinate location for this sprite
     * 
     * @param y 
     */
    public void setY(final double y)
    {
        this.y = y;
    }
    
    
    /**
     * Set the y coordinate location for this sprite
     * 
     * @param y 
     */
    public void setY(final int y)
    {
        setY((double)y);
    }
    
    /**
     * Get y coordinate location set for this sprite
     * 
     * @return int
     */
    public double getY()
    {
        return this.y;
    }
    
    /**
     * Set the width and height based on the same width/height of the image
     * 
     * @param image The image we want to copy the dimensions from
     */
    public void setDimensions(final Image image)
    {
        setDimensions(image.getWidth(null), image.getHeight(null));
    }
    
    /**
     * Set the width and height based on parameters passed.
     * 
     * @param w Width
     * @param h Height
     */
    public void setDimensions(final int w, final int h)
    {
        setDimensions((double)w, (double)h);
    }
    
    /**
     * Set the width and height based on parameters passed.
     * 
     * @param w Width
     * @param h Height
     */
    public void setDimensions(final double w, final double h)
    {
        setWidth(w);
        setHeight(h);
    }
    
    /**
     * Set the width dimension of this sprite
     * @param w 
     */
    public void setWidth(final double w)
    {
        this.w = w;
    }
    
    /**
     * Set the width dimension of this sprite
     * @param w 
     */
    public void setWidth(final int w)
    {
        setWidth((double)w);
    }
    
    /**
     * Get the width dimension of this sprite
     * @return int
     */
    public double getWidth()
    {
        return this.w;
    }
    
    /**
     * Set the height dimension of this sprite
     * @param h 
     */
    public void setHeight(final double h)
    {
        this.h = h;
    }
    
    /**
     * Set the height dimension of this sprite
     * @param h 
     */
    public void setHeight(final int h)
    {
        setHeight((double)h);
    }
    
    /**
     * Get the height dimension of this sprite
     * @return int
     */
    public double getHeight()
    {
        return this.h;
    }
    
    /**
     * Set the x, y velocity to 0
     */
    public void resetVelocity()
    {
        setVelocity(0,0);
    }
    
    /**
     * Sets the x, y velocity so when update is called
     * the sprite position will be updated accordingly.
     * 
     * @param dx
     * @param dy 
     */
    public void setVelocity(final double dx, final double dy)
    {
        setVelocity(dx, dy);
    }
    
    /**
     * Sets the x, y velocity so when update is called
     * the sprite position will be updated accordingly.
     * 
     * @param dx
     * @param dy 
     */
    public void setVelocity(final int dx, final int dy)
    {
        setVelocityX(dx);
        setVelocityY(dy);
    }
    
    /**
     * Sets the x, y velocity so when update is called
     * the sprite position will be updated accordingly.
     * 
     * @param velocity
     */
    public void setVelocity(final Point velocity)
    {
        setVelocity(velocity.x, velocity.y);
    }
    
    /**
     * Sets the x velocity so when update is called
     * the sprite position will be updated accordingly.
     * 
     * @param dx
     */
    public void setVelocityX(final double dx)
    {
        this.dx = dx;
    }
    
    /**
     * Sets the x velocity so when update is called
     * the sprite position will be updated accordingly.
     * 
     * @param dx
     */
    public void setVelocityX(final int dx)
    {
        setVelocityX((double)dx);
    }
    
    public double getVelocityX()
    {
        return this.dx;
    }
    
    public void setVelocityY(final double dy)
    {
        this.dy = dy;
    }
    
    public void setVelocityY(final int dy)
    {
        setVelocityY((double)dy);
    }
    
    public double getVelocityY()
    {
        return this.dy;
    }
    
    /**
     * Does the user have velocityX or velocityY
     * Will return true if x velocity or y velocity
     * are not equal to 0
     * @return boolean
     */
    public boolean hasVelocity()
    {
        return (hasVelocityX() || hasVelocityY());
    }
    
    /**
     * Is the x velocity not equal to 0? 
     * If so then there is velocity x
     * @return boolean
     */
    public boolean hasVelocityX()
    {
        return (getVelocityX() != 0);
    }
    
    /**
     * Is the y velocity not equal to 0? 
     * If so then there is velocity y
     * @return boolean
     */
    public boolean hasVelocityY()
    {
        return (getVelocityY() != 0);
    }

    /**
     * Adjust the (x,y) based on the current velocity
     */
    private void move()
    {
        this.setLocation(getX() + getVelocityX(), getY() + getVelocityY());
    }
    
    /**
     * Auto set the width/height based on the current spriteSheet animation
     */
    public void setDimensions()
    {   
        setDimensions(spriteSheet.getLocation().getWidth(), spriteSheet.getLocation().getHeight());
    }
    
    /**
     * Get a Rectangle object based on the current x, y, width, height
     * @return Rectangle
     */
    public Rectangle getRectangle()
    {
        if (rectangle == null)
            rectangle = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        
        rectangle.x = (int)getX();
        rectangle.y = (int)getY();
        rectangle.width = (int)getWidth();
        rectangle.height = (int)getHeight();
        
        return rectangle;
    }
    
    /**
     * Update the x,y location based on the current x,y velocity.
     */
    public void update()
    {
        //update x,y
        move();
    }
    
    /**
     * Draw the sprite with the parameter image
     * @param g Graphics object to draw to
     * @param image specified Image to draw
     * @return Graphics
     */
    public void draw(final Graphics g, final Image image)
    {
        if (spriteSheet.hasAnimations())
        {
            draw(g, image, spriteSheet.getLocation());
        }
        else
        {
            draw(g, image, null);
        }
    }
    
    /**
     * Draw our sprite object with the specified 
     * parameter image and draw a specified portion 
     * of that image.
     * @param g Graphics object to draw to
     * @param image Image to be drawn
     * @param location Portion of Image we want to draw
     * @return Graphics
     */
    public void draw(final Graphics g, final Image image, final Rectangle location)
    {
        int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;
            
        dx1 = (int)getX();
        dy1 = (int)getY();
        dx2 = dx1 + (int)getWidth();
        dy2 = dy1 + (int)getHeight();

        if (location != null)
        {
            if (!hasHorizontalFlip())
            {
                sx1 = location.x;
                sx2 = location.x + location.width;
            }
            else
            {
                sx1 = location.x + location.width;
                sx2 = location.x;
            }
            
            if (!hasVerticalFlip())
            {
                sy1 = location.y;
                sy2 = location.y + location.height;
            }
            else
            {
                sy1 = location.y + location.height;
                sy2 = location.y;
            }
        }
        else
        {
            if (!hasHorizontalFlip())
            {
                sx1 = 0;
                sx2 = image.getWidth(null);
            }
            else
            {
                sx1 = image.getWidth(null);
                sx2 = 0;
            }
            
            if (!hasVerticalFlip())
            {
                sy1 = 0;
                sy2 = image.getHeight(null);
            }
            else
            {
                sy1 = image.getHeight(null);
                sy2 = 0;
            }
        }
        
        draw(g, image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
    }
    
    /**
     * Draw our sprite object
     * @param g Graphics object to draw to
     * @return Graphics 
     */
    public void draw(final Graphics g)
    {
        if (spriteSheet != null && spriteSheet.hasAnimations())
        {
            draw(g, spriteSheet.getLocation());
        }
        else
        {
            if (imageDimensions == null || imageDimensions.width != image.getWidth(null) || imageDimensions.height != image.getHeight(null))
                imageDimensions = new Rectangle(0, 0, image.getWidth(null), image.getHeight(null));
            
            draw(g, imageDimensions);
        }
    }
    
    /**
     * Draw our sprite image with the specified parameter
     * that will indicate which part of the image is to be drawn.
     * 
     * @param g Graphics object we want to draw to
     * @param location Rectangle area of image that we want to draw
     * @return Graphics
     */
    public void draw(final Graphics g, final Rectangle location)
    {
        //verify image exists before drawing
        if (getImage() != null)
        {
            int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;
            
            dx1 = (int)getX();
            dy1 = (int)getY();
            dx2 = dx1 + (int)getWidth();
            dy2 = dy1 + (int)getHeight();
            
            if (!hasHorizontalFlip())
            {
                sx1 = location.x;
                sx2 = location.x + location.width;
            }
            else
            {
                sx1 = location.x + location.width;
                sx2 = location.x;
            }
            
            if (!hasVerticalFlip())
            {
                sy1 = location.y;
                sy2 = location.y + location.height;
            }
            else
            {
                sy1 = location.y + location.height;
                sy2 = location.y;
            }
            
            draw(g, getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
        }
    }
    
    /**
     * Draw our sprite with the provided image at the provided location
     * 
     * @param g Graphics object to draw to
     * @param image Image to draw
     * @param dx1 Destination x1
     * @param dy1 Destination y1
     * @param dx2 Destination x2
     * @param dy2 Destination y2
     * @param sx1 Source x1
     * @param sy1 Source y1
     * @param sx2 Source x2
     * @param sy2 Source y2
     * @return 
     */
    public void draw(final Graphics graphics, final Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        graphics.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
}