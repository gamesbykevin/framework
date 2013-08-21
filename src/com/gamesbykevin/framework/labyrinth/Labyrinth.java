package com.gamesbykevin.framework.labyrinth;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Labyrinth 
{
    public enum Algorithm
    {
        DepthFirstSearch, Prims, Kruskals
    }
    
    private DepthFirstSearch depthFirstSearch;
    
    private Prims prims;
    
    private Kruskals kruskals;
    
    public Labyrinth(final int cols, final int rows, final Algorithm algorithm)
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch = new DepthFirstSearch(cols, rows);
                break;
                
            case Prims:
                prims = new Prims(cols, rows);
                break;
                
            case Kruskals:
                kruskals = new Kruskals(cols, rows);
                break;
        }
    }
    
    /**
     * Create the Maze. 
     * IDEAS: Make the maze update once per each frame, and/or make a progress loading bar
     * 
     * @throws Exception If the start Location is not set
     */
    public void create() throws Exception
    {
        if (depthFirstSearch != null)
            depthFirstSearch.create();
        if (prims != null)
            prims.create();
        if (kruskals != null)
            kruskals.create();
    }
    
    public void setStart(final int col, final int row)
    {
        if (depthFirstSearch != null)
            depthFirstSearch.setStart(col, row);
        if (prims != null)
            prims.setStart(col, row);
        if (kruskals != null)
            kruskals.setStart(col, row);
    }
    
    public void setFinish(final int col, final int row)
    {
        if (depthFirstSearch != null)
            depthFirstSearch.setFinish(col, row);
        if (prims != null)
            prims.setFinish(col, row);
        if (kruskals != null)
            kruskals.setFinish(col, row);
    }
    
    public Graphics render(final Graphics graphics, final Rectangle screen)
    {
        if (depthFirstSearch != null)
            depthFirstSearch.render(graphics, screen);
        if (prims != null)
            prims.render(graphics, screen);
        if (kruskals != null)
            kruskals.render(graphics, screen);
        
        return graphics;
    }
}