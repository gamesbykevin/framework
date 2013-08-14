package com.gamesbykevin.framework.resources;

import javax.swing.ImageIcon;

public class Image
{
    /**
     * Loads an image with the given location
     * 
     * @param source The class that is located in the same directory as the resources folder
     * @param location The file location if the image 
     * @return Image
     * @throws CustomException 
     */
    public static java.awt.Image getResource(Class source, String location) throws Exception
    {
        return new ImageIcon(source.getResource(location)).getImage();
    }
}