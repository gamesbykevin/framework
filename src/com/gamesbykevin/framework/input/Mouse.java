package com.gamesbykevin.framework.input;

import java.awt.event.MouseEvent;
import java.awt.Point;

public class Mouse 
{
    private boolean mouseMoved = false, mousePressed = false, mouseDragged = false, mouseExited = false;
    private boolean mouseEntered = false, mouseReleased = false, mouseClicked = false;
    
    private Point mouseLocation = new Point();
    
    private int mouseButton = -1;
    
    public Mouse()
    {
        
    }
    
    public void dispose()
    {
        mouseLocation = null;
    }
    
    public void setMouseMoved(Point p)
    {
        this.mouseMoved = true;
        this.mouseLocation = p;
    }
    
    public boolean hasMouseMoved()
    {
        return this.mouseMoved;
    }
    
    public void setMousePressed(MouseEvent e)
    {
        this.mouseButton = e.getButton();
        this.mousePressed = true;
        this.mouseLocation = e.getPoint();
    }
    
    public boolean isMousePressed()
    {
        return this.mousePressed;
    }
    
    public void setMouseDragged(Point p)
    {
        this.mouseDragged = true;
        this.mouseLocation = p;
    }
    
    public boolean isMouseDragged()
    {
        return this.mouseDragged;
    }
    
    public void setMouseExited(Point p)
    {
        this.mouseExited = true;
        this.mouseLocation = p;
    }
    
    public boolean hasMouseExited()
    {
        return this.mouseExited;
    }
    
    public void setMouseEntered(Point p)
    {
        this.mouseEntered = true;
        this.mouseLocation = p;
    }
    
    public boolean hasMouseEntered()
    {
        return this.mouseEntered;
    }
    
    public void setMouseReleased(MouseEvent e)
    {
        this.mouseButton = e.getButton();
        this.mouseReleased = true;
        this.mouseLocation = e.getPoint();
    }
    
    public boolean isMouseReleased()
    {
        return this.mouseReleased;
    }
    
    public void setMouseClicked(MouseEvent e)
    {
        this.mouseButton = e.getButton();
        this.mouseClicked = true;
        this.mouseLocation = e.getPoint();
    }
    
    public boolean isMouseClicked()
    {
        return this.mouseClicked;
    }
    
    public Point getMouseLocation()
    {
        return this.mouseLocation;
    }
    
    public int getMouseButton()
    {
        return this.mouseButton;
    }
    
    public boolean hitMouseLeftButton()
    {
        return (this.mouseButton == MouseEvent.BUTTON1);
    }
    
    public boolean hitMouseMiddleButton()
    {
        return (this.mouseButton == MouseEvent.BUTTON2);
    }
    
     public boolean hitMouseRightButton()
    {
        return (this.mouseButton == MouseEvent.BUTTON3);
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