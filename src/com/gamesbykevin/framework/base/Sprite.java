package com.gamesbykevin.framework.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Sprite extends Cell 
{
    //(x, y) = location (dx, dy) = velocity for x/y coordinates, (w, h) = width and height
    private int x, y, dx, dy, w, h;
    
    //do we automatically set the width and height based on the current animations dimensions
    private boolean autoSize = false;
    
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
    
    public Sprite()
    {
        this(0,0,0,0,0,0,null);
    }
    
    public Sprite(final Image img)
    {
        this(0, 0, 0, 0, 0, 0, img);
    }
    
    public Sprite(final Rectangle r, final Image img)
    {
        this(r.x, r.y, r.width, r.height, 0, 0, img);
    }
    
    public Sprite(final int x, final int y, final int w, final int h, final int dx, final int dy, final Image img)
    {
        this.setLocation(x, y);
        this.setVelocity(dx, dy);
        this.setDimensions(w, h);
        this.setImage(img);
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
    
    public Cell getCell()
    {
        return new Cell(getCol(), getRow());
    }
    
    public void setAutoSize(final boolean autoSize)
    {
        this.autoSize = autoSize;
    }
    
    public boolean hasAutoSize()
    {
        return this.autoSize;
    }
    
    /**
     * Get the center of the object
     * @return Center of Sprite
     */
    public Point getCenter()
    {
        return new Point(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
    }
    
    /**
     * Returns the location of the Sprite
     * @return Point the (x,y) coordinates where the Sprite is located
     */
    public Point getPoint()
    {
        return new Point(getX(), getY());
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
    
    public void setLocation(final Point p)
    {
        setLocation(p.x, p.y);
    }
    
    public void setLocation(final double x, final double y)
    {
        setX(x);
        setY(y);
    }
    
    public void setLocation(final int x, final int y)
    {
        setX(x);
        setY(y);
    }
    
    public void setX(final double x)
    {
        setX((int)x);
    }
    
    public void setX(final int x)
    {
        this.x = x;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public void setY(final double y)
    {
        setY((int)y);
    }
    
    public void setY(final int y)
    {
        this.y = y;
    }
    
    public int getY()
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
        setWidth(w);
        setHeight(h);
    }
    
    public void setWidth(final double w)
    {
        setWidth((int)w);
    }
    
    public void setWidth(final int w)
    {
        this.w = w;
    }
    
    public int getWidth()
    {
        return this.w;
    }
    
    public void setHeight(final double h)
    {
        setHeight((int)h);
    }
    
    public void setHeight(final int h)
    {
        this.h = h;
    }
    
    public int getHeight()
    {
        return this.h;
    }
    
    public void setVelocity(final double dx, final double dy)
    {
        this.setVelocityX(dx);
        this.setVelocityY(dy);
    }
    
    public void setVelocity(final int dx, final int dy)
    {
        this.setVelocityX(dx);
        this.setVelocityY(dy);
    }
    
    public void setVelocity(final Point velocity)
    {
        setVelocity(velocity.x, velocity.y);
    }
    
    public void setVelocityX(final double dx)
    {
        this.dx = (int)dx;
    }
    
    public void setVelocityX(final int dx)
    {
        this.dx = dx;
    }
    
    public int getVelocityX()
    {
        return this.dx;
    }
    
    public void setVelocityY(final double dy)
    {
        this.dy = (int)dy;
    }
    
    public void setVelocityY(final int dy)
    {
        this.dy = dy;
    }
    
    public int getVelocityY()
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
        return (getVelocityX() != 0 || getVelocityY() != 0);
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
        setDimensions(spriteSheet.getLocation().width, spriteSheet.getLocation().height);
    }
    
    /**
     * Get a Rectangle object based on the current x, y, width, height
     * @return Rectangle
     */
    public Rectangle getRectangle()
    {
        if (rectangle == null)
            rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        
        rectangle.x = getX();
        rectangle.y = getY();
        rectangle.width = getWidth();
        rectangle.height = getHeight();
        
        return rectangle;
    }
    
    /**
     * If auto size is enabled set width and height 
     * according to the current animation dimensions 
     * in our sprite sheet if it exists. Also update
     * the x,y location based on the current x,y velocity.
     */
    public void update() throws Exception
    {
        if (hasAutoSize())
        {
            if (spriteSheet != null)
            {
                setWidth(spriteSheet.getLocation().width);
                setHeight(spriteSheet.getLocation().height);
            }
        }
        
        //update x,y
        move();
    }
    
    public Graphics draw(final Graphics g, final Image img)
    {
        if (spriteSheet.hasAnimations())
        {
            return draw(g, img, spriteSheet.getLocation());
        }
        else
        {
            return draw(g, img, null);
        }
    }
    
    public Graphics draw(final Graphics g, final Image image, final Rectangle location)
    {
        int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;

        if (hasAutoSize() && location != null)
        {
            setWidth(location.width);
            setHeight(location.height);
        }
            
        dx1 = getX();
        dy1 = getY();
        dx2 = dx1 + getWidth();
        dy2 = dy1 + getHeight();

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
        
        return g;
    }
    
    public Graphics draw(final Graphics g)
    {
        if (spriteSheet != null && spriteSheet.hasAnimations())
        {
            return draw(g, spriteSheet.getLocation());
        }
        else
        {
            if (imageDimensions == null || imageDimensions.width != image.getWidth(null) || imageDimensions.height != image.getHeight(null))
                imageDimensions = new Rectangle(0, 0, image.getWidth(null), image.getHeight(null));
            
            return draw(g, imageDimensions);
        }
    }
    
    public Graphics draw(final Graphics g, final Rectangle location)
    {
        //verify image exists before drawing
        if (getImage() != null)
        {
            if (hasAutoSize())
            {
                setWidth(location.width);
                setHeight(location.height);
            }
            
            int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;
            
            dx1 = getX();
            dy1 = getY();
            dx2 = dx1 + getWidth();
            dy2 = dy1 + getHeight();
            
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
        
        return g;
    }
    
    public Graphics draw(final Graphics g, Image tmp, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        g.drawImage(tmp, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        return g;
    }
}