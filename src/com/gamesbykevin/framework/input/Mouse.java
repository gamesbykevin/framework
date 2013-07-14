package com.gamesbykevin.framework.input;

import java.awt.event.MouseEvent;
import java.awt.Point;

public class Mouse 
{
    private boolean mouseMoved = false, mousePressed = false, mouseDragged = false, mouseExited = false;
    private boolean mouseEntered = false, mouseReleased = false, mouseClicked = false;
    
    private Point location = new Point();
    
    private int button = -1;
    
    public Mouse()
    {
        
    }
    
    public void dispose()
    {
        location = null;
    }
    
    public void setMouseMoved(final Point location)
    {
        this.mouseMoved = true;
        this.location = location;
    }
    
    public boolean hasMouseMoved()
    {
        return this.mouseMoved;
    }
    
    public void setMousePressed(MouseEvent e)
    {
        this.button = e.getButton();
        this.mousePressed = true;
        this.location = e.getPoint();
    }
    
    public boolean isMousePressed()
    {
        return this.mousePressed;
    }
    
    public void setMouseDragged(final Point location)
    {
        this.mouseDragged = true;
        this.location = location;
    }
    
    public boolean isMouseDragged()
    {
        return this.mouseDragged;
    }
    
    public void setMouseExited(final Point location)
    {
        this.mouseExited = true;
        this.location = location;
    }
    
    public boolean hasMouseExited()
    {
        return this.mouseExited;
    }
    
    public void setMouseEntered(final Point location)
    {
        this.mouseEntered = true;
        this.location = location;
    }
    
    public boolean hasMouseEntered()
    {
        return this.mouseEntered;
    }
    
    public void setMouseReleased(MouseEvent e)
    {
        this.button = e.getButton();
        this.mouseReleased = true;
        this.location = e.getPoint();
    }
    
    public boolean isMouseReleased()
    {
        return this.mouseReleased;
    }
    
    public void setMouseClicked(MouseEvent e)
    {
        this.button = e.getButton();
        this.mouseClicked = true;
        this.location = e.getPoint();
    }
    
    public boolean isMouseClicked()
    {
        return this.mouseClicked;
    }
    
    public Point getLocation()
    {
        return this.location;
    }
    
    public int getButton()
    {
        return this.button;
    }
    
    public boolean hitMouseLeftButton()
    {
        return (this.button == MouseEvent.BUTTON1);
    }
    
    public boolean hitMouseMiddleButton()
    {
        return (this.button == MouseEvent.BUTTON2);
    }
    
     public boolean hitMouseRightButton()
    {
        return (this.button == MouseEvent.BUTTON3);
    }
   
    public void resetMouseEvents()
    {
        this.mouseClicked  = false;
        this.mouseDragged  = false;
        this.mouseEntered  = false;
        this.mouseExited   = false;
        this.mouseMoved    = false;
        this.mousePressed  = false;
        this.mouseReleased = false;
    }
}