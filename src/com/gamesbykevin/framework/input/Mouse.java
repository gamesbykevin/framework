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
        this.location = new Point();
    }
    
    public void dispose()
    {
        location = null;
    }
    
    /**
     * Flag the mouse as moved
     * @param location The (x,y) location of the mouse
     */
    public void setMouseMoved(final Point location)
    {
        this.mouseMoved = true;
        setLocation(location);
    }
    
    /**
     * Has the mouse been moved?
     * @return true=yes, false=no
     */
    public boolean hasMouseMoved()
    {
        return this.mouseMoved;
    }
    
    /**
     * Flag the mouse as pressed
     * @param e Mouse Event
     */
    public void setMousePressed(MouseEvent e)
    {
        this.button = e.getButton();
        this.mousePressed = true;
        setLocation(location);
    }
    
    /**
     * Has the mouse been pressed?
     * @return true=yes, false=no
     */
    public boolean isMousePressed()
    {
        return this.mousePressed;
    }
    
    /**
     * Flag the mouse as dragged
     * @param location The (x,y) location of the mouse
     */
    public void setMouseDragged(final Point location)
    {
        this.mouseDragged = true;
        setLocation(location);
    }
    
    /**
     * Has the mouse been dragged?
     * @return true=yes, false=no
     */
    public boolean isMouseDragged()
    {
        return this.mouseDragged;
    }
    
    /**
     * Flag the mouse as exited
     * @param location The (x,y) location of the mouse
     */
    public void setMouseExited(final Point location)
    {
        this.mouseExited = true;
        setLocation(location);
    }
    
    /**
     * Has the mouse exited?
     * @return true=yes, false=no
     */
    public boolean hasMouseExited()
    {
        return this.mouseExited;
    }
    
    public void setMouseEntered(final Point location)
    {
        this.mouseEntered = true;
        setLocation(location);
    }
    
    /**
     * Has the mouse entered?
     * @return true=yes, false=no
     */
    public boolean hasMouseEntered()
    {
        return this.mouseEntered;
    }
    
    /**
     * Flag the mouse as released.<br>
     * This will also flag drag false, since you can't drag if the mouse is released
     * @param e Mouse Event
     */
    public void setMouseReleased(MouseEvent e)
    {
        this.button = e.getButton();
        this.mouseReleased = true;
        
        //if the mouse was released then we can't be dragging it
        this.mouseDragged = false;
        
        //update location
        setLocation(e.getPoint());
    }
    
    /**
     * Has the mouse been released?
     * @return true=yes, false=no
     */
    public boolean isMouseReleased()
    {
        return this.mouseReleased;
    }
    
    /**
     * Flag the mouse as clicked
     * @param e Mouse Event
     */
    public void setMouseClicked(MouseEvent e)
    {
        this.button = e.getButton();
        this.mouseClicked = true;
        setLocation(e.getPoint());
    }
    
    /**
     * Has the mouse been clicked?
     * @return true=yes, false=no
     */
    public boolean isMouseClicked()
    {
        return this.mouseClicked;
    }
    
    /**
     * Get the x,y coordinates
     * @return Point
     */
    public Point getLocation()
    {
        return this.location;
    }
    
    /**
     * Set the location of the mouse.
     * @param location The (x,y) location
     */
    private void setLocation(final Point location)
    {
        this.location = location;
    }
    
    /**
     * Get the button.
     * @return The value of the button pressed
     */
    public int getButton()
    {
        return this.button;
    }
    
    /**
     * Did we hit the left button?
     * @return true if the left mouse button was pressed, false otherwise
     */
    public boolean hitLeftButton()
    {
        return (getButton() == MouseEvent.BUTTON1);
    }
    
    /**
     * Did we hit the middle button?
     * @return true if the middle mouse button was pressed, false otherwise
     */
    public boolean hitMiddleButton()
    {
        return (getButton() == MouseEvent.BUTTON2);
    }
    
    /**
     * Did we hit the right button?
     * @return true if the right mouse button was pressed, false otherwise
     */
    public boolean hitRightButton()
    {
        return (getButton() == MouseEvent.BUTTON3);
    }
    
    /**
     * Reset all mouse events.<br>
     * We will not reset the mouse (x,y) location.
     */
    public void reset()
    {
        this.mouseClicked  = false;
        this.mouseDragged  = false;
        this.mouseEntered  = false;
        this.mouseExited   = false;
        this.mouseMoved    = false;
        this.mousePressed  = false;
        this.mouseReleased = false;
        
        this.button = -1;
    }
}