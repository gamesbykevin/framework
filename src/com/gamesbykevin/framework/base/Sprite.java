package com.gamesbykevin.framework.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Sprite extends Cell 
{
    private int x, y, dx, dy, w, h;
    
    private boolean autoSize = false;
    
    //switch image east/west
    private boolean horizontalFlip = false;
    
    //switch image north-south
    private boolean verticalFlip = false;
    
    private Image img;
    
    private SpriteSheet spriteSheet;
    
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
        
        this.spriteSheet = new SpriteSheet();
    }
    
    public void dispose()
    {
        if (img != null)
            img.flush();

        img = null;

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
    
    public void setAutoSizeSprite(final boolean autoSize)
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
    
    public void setImage(final Image img)
    {
        this.img = img;
    }
    
    public Image getImage()
    {
        return this.img;
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
     * Create a Rectangle object based on the current x, y, width, height
     * @return Rectangle
     */
    public Rectangle getRectangle()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
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
    
    public Graphics draw(Graphics g, final Image img)
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
    
    public Graphics draw(Graphics g, final Image img, final Rectangle location)
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
                sx2 = img.getWidth(null);
            }
            else
            {
                sx1 = img.getWidth(null);
                sx2 = 0;
            }
            
            if (!hasVerticalFlip())
            {
                sy1 = 0;
                sy2 = img.getHeight(null);
            }
            else
            {
                sy1 = img.getHeight(null);
                sy2 = 0;
            }
        }
        
        draw(g, img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
        
        return g;
    }
    
    public Graphics draw(Graphics g)
    {
        if (spriteSheet.hasAnimations())
        {
            return draw(g, spriteSheet.getLocation());
        }
        else
        {
            return draw(g, new Rectangle(0, 0, this.getImage().getWidth(null), this.getImage().getHeight(null)));
        }
    }
    
    public Graphics draw(Graphics g, final Rectangle location)
    {
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
        else
        {
            g.setColor(Color.RED);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }
        
        return g;
    }
    
    public Graphics draw(Graphics g, Image tmp, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        g.drawImage(tmp, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        return g;
    }
}