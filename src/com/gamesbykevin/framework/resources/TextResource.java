package com.gamesbykevin.framework.resources;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextResource 
{
    //each line in the text file is stored in this array list
    private List<String> lines;
    
    public TextResource(Class source, final String location) throws Exception
    {
        InputStream in = source.getResource(location).openStream();
        BufferedReader input =  new BufferedReader(new InputStreamReader(in));

        String line = null;
        lines = new ArrayList<String>();
        
        while ((line = input.readLine()) != null)
        {
            lines.add(line);
        }

        input.close();
        input = null;
        
        in.close();
        in = null;
    }
    
    /**
     * Returns all of the lines in the text file
     * 
     * @return List<String> ArrayList of lines
     */
    public List<String> getLines()
    {
        return lines;
    }
    
    /**
     * Returns a line from the text file at the given index (Line Number)
     * The first line number is 0
     * 
     * @param index Line number from text file
     * @return String the line specified
     */
    public String getLine(final int index)
    {
        return lines.get(index);
    }
}