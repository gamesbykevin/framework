package com.gamesbykevin.framework.display;

import java.awt.Rectangle;

public class WindowHelper 
{
    //the purpose of this class is to take a number of rows, cols
    //and create equal size Rectangle windows within the given Rectangle window
    public static Rectangle[][] getWindows(Rectangle window, int rows, int cols)
    {
        Rectangle[][] windows = new Rectangle[rows][cols];
        
        int eachWidth  = window.width  / cols;
        int eachHeight = window.height / rows;
        
        for (int row=0; row < rows; row++)
        {
            for (int col=0; col < cols; col++)
            {
                int x = window.x + (col * eachWidth);
                int y = window.y + (row * eachHeight);
                
                windows[row][col] = new Rectangle(x, y, eachWidth, eachHeight);
            }
        }
        
        return windows;
    }
}