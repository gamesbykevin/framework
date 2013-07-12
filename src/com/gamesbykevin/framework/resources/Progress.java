package com.gamesbykevin.framework.resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Progress 
{
    private double progress;
    
    private int total = 0;
    private int current = 0;
    
    private String desc = "";
    
    public Progress(int total)
    {
        this.total = total;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public int getTotal()
    {
        return total;
    }
    
    public int getCurrentCount()
    {
        return current;
    }
    
    public void increaseProgress()
    {
        current++;
    }
    
    public void setComplete()
    {
        current = total;
    }
    
    public double getProgress()
    {
        progress = ((double)getCurrentCount() / (double)getTotal());
        
        return progress;
    }
    
    public boolean isLoadingComplete()
    {
        return (getProgress() >= 1);
    }
    
    public static Graphics draw(Graphics g, Rectangle screen, double progress, String desc) //draw progress bar within Rectangle r
    {
        g.setColor(Color.BLACK);
        g.fillRect(screen.x, screen.y, screen.width, screen.height);

        int middleX = screen.x + (screen.width/2);
        int middleY = screen.y + (screen.height/2);
        
        int percentComplete = (int)(progress * 100);
        
        if (percentComplete >= 100)
            percentComplete = 100;
        
        String loadingDesc = "";
        
        if (desc != null && desc.length() > 0)
        {
            loadingDesc = desc + " " + percentComplete + "%";
        }
        else
        {
            loadingDesc = "Loading " + percentComplete + "%";
        }
        
        int fontSize = 18;
        g.setFont(g.getFont().deriveFont(Font.PLAIN, fontSize));
        
        while(true)
        {
            if (g.getFontMetrics().stringWidth(loadingDesc) > screen.width)
            {
                fontSize--;
                g.setFont(g.getFont().deriveFont(Font.PLAIN, fontSize));
                
                if (fontSize <= 1)
                    break;
            }
            else
            {
                break;
            }
        }
        
        int textWidth = g.getFontMetrics().stringWidth(loadingDesc);
        int fillWidth = (int)(textWidth * progress);
        
        if (percentComplete >= 100)
            fillWidth = textWidth;
        
        int drawX = middleX - (textWidth / 2);
        int drawY = middleY + (g.getFontMetrics().getHeight());
        
        g.setColor(Color.WHITE);
        
        g.drawString(loadingDesc, drawX, drawY);
        
        g.fillRect(drawX, drawY + (g.getFontMetrics().getHeight() * 2), fillWidth, g.getFontMetrics().getHeight());
        
        return g;
    }
}