package com.gamesbykevin.framework.resources;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ImageResource 
{
    /**
     * Loads an image with the given file path name
     * @param source
     * @param fileName
     * @return Image If an error occurs null is returned
     * @throws CustomException 
     */
    public static Image getImageResource(Class source, String fileName)
    {
        try
        {
            return new ImageIcon(source.getResource(fileName)).getImage();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Image getImageResource(Class source, String fileName, int red, int green, int blue)
    {
        return getImageResource(source, fileName, new Color(red, green, blue));
    }
    
    public static Image getImageResource(Class source, String fileName, Color excludeColor)
    {
        return getImageResource(source, fileName, excludeColor, null, null);
    }
    
    public static Image getImageResource(Class source, String fileName, Color excludeColor, Color replaceColor, Color newReplaceColor)
    {
        Image originalImage = new ImageIcon(source.getResource(fileName)).getImage();
        ImageFilter imageResourceFilter = new ImageResourceFilter(excludeColor, replaceColor, newReplaceColor);
        FilteredImageSource fis = new FilteredImageSource(originalImage.getSource(), imageResourceFilter);
        Image img = Toolkit.getDefaultToolkit().createImage(fis);
        
        originalImage.flush();
        imageResourceFilter = null;
        fis = null;
        
        return img;
    }
}