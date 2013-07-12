package com.gamesbykevin.framework.resources;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class ImageResourceFilter extends RGBImageFilter  
{
    private Color excludeColor; //which color from image should be transparent
    private Color replaceColor; //which color to search for
    private Color newReplaceColor; //which color to replace once the replace color was found
    
    public ImageResourceFilter(Color excludeColor)
    {
        this.excludeColor = excludeColor;
    }
    
    public ImageResourceFilter(Color excludeColor, Color replaceColor, Color newReplaceColor)
    {
        this.excludeColor = excludeColor;
        this.replaceColor = replaceColor;
        this.newReplaceColor = newReplaceColor;
    }
    
    // This method is called automatically for every pixel in the image
    public int filterRGB(int x, int y, int rgb)
    {
        Color tmp = new Color(rgb);
        
        if (this.excludeColor != null)
        {
            if (tmp.getRed() == this.excludeColor.getRed() && 
                tmp.getGreen() == this.excludeColor.getGreen() && 
                tmp.getBlue() == this.excludeColor.getBlue()
            )
                return this.getTransparentRGB(rgb);
        }
        
        if (this.replaceColor != null && this.newReplaceColor != null)
        {
            if (tmp.getRed() == this.replaceColor.getRed() && 
                tmp.getGreen() == this.replaceColor.getGreen() && 
                tmp.getBlue() == this.replaceColor.getBlue()
            )
                return this.newReplaceColor.getRGB();
        }
        
        return rgb;
    }
    
    private int getTransparentRGB(int rgb)
    {
        return 0x00FFFFFF & rgb;    //return transparent pixel
    }
}
