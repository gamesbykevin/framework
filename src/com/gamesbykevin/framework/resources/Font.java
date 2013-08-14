package com.gamesbykevin.framework.resources;

public class Font 
{
    public static java.awt.Font getFont(final Class source, final String location) throws Exception
    {
        return java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, source.getResource(location).openStream());
    }
}
