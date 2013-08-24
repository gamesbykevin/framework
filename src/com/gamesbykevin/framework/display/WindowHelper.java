package com.gamesbykevin.framework.display;

import java.awt.Rectangle;

/**
 * The purpose of this class is to take a number of rows, columns and create equal size Rectangle windows within a container Rectangle window
 * @author GOD
 */
public class WindowHelper 
{
    /**
     * Create a number of smaller windows equal in dimensions within the specified container window.
     * 
     * @param window The container for all of the children windows
     * @param rows Number of rows
     * @param cols Number of columns
     * @return Rectangle[][] each Rectangle 
     */
    public static Rectangle[][] getWindows(final Rectangle window, final int rows, final int cols)
    {
        //our Array containing all the windows
        Rectangle[][] windows = new Rectangle[rows][cols];
        
        //determine the width of each child window
        int eachWidth  = window.width  / cols;
        
        //determine the height of each child window
        int eachHeight = window.height / rows;
        
        for (int row=0; row < rows; row++)
        {
            for (int col=0; col < cols; col++)
            {
                //current x coordinate of window
                int x = window.x + (col * eachWidth);
                
                //current y coordinate of window
                int y = window.y + (row * eachHeight);
                
                windows[row][col] = new Rectangle(x, y, eachWidth, eachHeight);
            }
        }
        
        //all window dimensions have been established, return result
        return windows;
    }
}