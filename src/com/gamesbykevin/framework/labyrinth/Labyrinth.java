package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.resources.Progress;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Labyrinth 
{
    public enum Algorithm
    {
        DepthFirstSearch, Prims, Kruskals, Ellers, HuntKill, Sidewinder
    }
    
    private Algorithm algorithm;
    
    //below the objects to be used depending on the selected algorithm
    private DepthFirstSearch depthFirstSearch;
    private Prims prims;
    private Kruskals kruskals;
    private Ellers ellers;
    private HuntKill huntKill;
    private Sidewinder sidewinder;
    
    /**
     * Create a new labyrinth using the specified Algorithm and the number of columns/rows
     * 
     * @param cols
     * @param rows
     * @param algorithm 
     */
    public Labyrinth(final int cols, final int rows, final Algorithm algorithm) throws Exception
    {
        this.algorithm = algorithm;
        
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
                
            case Ellers:
                ellers = new Ellers(cols, rows);
                break;
                
            case HuntKill:
                huntKill = new HuntKill(cols, rows);
                break;
                
            case Sidewinder:
                sidewinder = new Sidewinder(cols, rows);
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    public void dispose()
    {
        algorithm = null;
        
        if (depthFirstSearch != null)
            depthFirstSearch.dispose();
        if (prims != null)
            prims.dispose();
        if (kruskals != null)
            kruskals.dispose();
        if (ellers != null)
            ellers.dispose();
        if (huntKill != null)
            huntKill.dispose();
        if (sidewinder != null)
            sidewinder.dispose();
        
        depthFirstSearch = null;
        prims = null;
        kruskals = null;
        ellers = null;
        huntKill = null;
        sidewinder = null;
    }
    
    /**
     * Initialize the necessary objects needed to generate the maze.
     * 
     * @throws Exception If the start Location is not set
     */
    public void create() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.initialize();
                break;
                
            case Prims:
                prims.initialize();
                break;
                
            case Kruskals:
                kruskals.initialize();
                break;
                
            case Ellers:
                ellers.initialize();
                break;

            case HuntKill:
                huntKill.initialize();
                break;
                
            case Sidewinder:
                sidewinder.initialize();
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Update the maze creation a little more
     * @throws Exception 
     */
    public void update() throws Exception
    {
        if (isComplete())
            return;
        
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.update();
                break;
                
            case Prims:
                prims.update();
                break;
                
            case Kruskals:
                kruskals.update();
                break;
                
            case Ellers:
                ellers.update();
                break;

            case HuntKill:
                huntKill.update();
                break;
                
            case Sidewinder:
                sidewinder.update();
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Get all of the Cell(s) of the Maze
     * 
     * @return List<Location>
     * @throws Exception 
     */
    public List<Location> getLocations() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.getLocations();
                
            case Prims:
                return prims.getLocations();
                
            case Kruskals:
                 return kruskals.getLocations();
                
             case Ellers:
                return ellers.getLocations();
                 
            case HuntKill:
                return huntKill.getLocations();
                 
            case Sidewinder:
                return sidewinder.getLocations();
                
           default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Get Location at column/row
     * @param col
     * @param row
     * @return Location
     * @throws Exception 
     */
    public Location getLocation(int col, int row) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.getLocation(col, row);
                
            case Prims:
                return prims.getLocation(col, row);
                
            case Kruskals:
                 return kruskals.getLocation(col, row);
                
             case Ellers:
                return ellers.getLocation(col, row);
                 
            case HuntKill:
                return huntKill.getLocation(col, row);
                 
            case Sidewinder:
                return sidewinder.getLocation(col, row);
                
           default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    public Progress getProgress() throws Exception
    {
        Progress tmp = null;
        
        switch(algorithm)
        {
            case DepthFirstSearch:
                tmp = depthFirstSearch.getProgress();
                break;
                
            case Prims:
                tmp = prims.getProgress();
                break;
                
            case Kruskals:
                 tmp = kruskals.getProgress();
                break;
                
             case Ellers:
                tmp = ellers.getProgress();
                break;
                 
            case HuntKill:
                tmp = huntKill.getProgress();
                break;
                 
            case Sidewinder:
                tmp = sidewinder.getProgress();
                break;
                
           default:
                throw new Exception("Algorithm not setup here");
        }
        
        if (tmp == null)
            throw new Exception("create() must be called first to initialize the Progress object.");
        
        return tmp;
    }
    
    /**
     * Set the start position.
     * 
     * @param col
     * @param row
     * @throws Exception 
     */
    public void setStart(final int col, final int row) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.setStart(col, row);
                break;
                
            case Prims:
                prims.setStart(col, row);
                break;
                
            case Kruskals:
                kruskals.setStart(col, row);
                break;
                
            case Ellers:
                ellers.setStart(col, row);
                break;
                
            case HuntKill:
                huntKill.setStart(col, row);
                break;
                
            case Sidewinder:
                sidewinder.setStart(col, row);
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Set the finish position.
     * 
     * @param col
     * @param row
     * @throws Exception 
     */
    public void setFinish(final int col, final int row) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.setFinish(col, row);
                break;
                
            case Prims:
                prims.setFinish(col, row);
                break;
                
            case Kruskals:
                kruskals.setFinish(col, row);
                break;
                
            case Ellers:
                ellers.setFinish(col, row);
                break;

            case HuntKill:
                huntKill.setFinish(col, row);
                break;
                
            case Sidewinder:
                sidewinder.setFinish(col, row);
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Has the maze been created
     * 
     * @return boolean
     * @throws Exception 
     */
    public boolean isComplete() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.isComplete();
                
            case Prims:
                return prims.isComplete();
                
            case Kruskals:
                return kruskals.isComplete();
                
            case Ellers:
                return ellers.isComplete();

            case HuntKill:
                return huntKill.isComplete();
                
            case Sidewinder:
                return sidewinder.isComplete();
                
            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Draw the progress information screen
     * @param graphics Object to draw Progress information to
     * @param screen Container for Progress information
     * @return Graphics
     * @throws Exception 
     */
    public Graphics renderProgress(final Graphics graphics, final Rectangle screen) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.renderProgress(graphics, screen);

            case Prims:
                return prims.renderProgress(graphics, screen);

            case Kruskals:
                return kruskals.renderProgress(graphics, screen);

            case Ellers:
                return ellers.renderProgress(graphics, screen);

            case HuntKill:
                return huntKill.renderProgress(graphics, screen);

            case Sidewinder:
                return sidewinder.renderProgress(graphics, screen);

            default:
                throw new Exception("Algorithm not setup here");
        }
    }
    
    /**
     * Render basic top down 2d visual of maze. <br>
     * NOTE: Should only be used for debugging
     * 
     * @param graphics
     * @param screen
     * @return Graphics
     * @throws Exception 
     */
    public Graphics render(final Graphics graphics, final Rectangle screen) throws Exception
    {
        if (!isComplete())
        {
            return renderProgress(graphics, screen);
        }
        else
        {
            switch(algorithm)
            {
                case DepthFirstSearch:
                    return depthFirstSearch.render(graphics, screen);

                case Prims:
                    return prims.render(graphics, screen);

                case Kruskals:
                    return kruskals.render(graphics, screen);

                case Ellers:
                    return ellers.render(graphics, screen);

                case HuntKill:
                    return huntKill.render(graphics, screen);

                case Sidewinder:
                    return sidewinder.render(graphics, screen);

                default:
                    throw new Exception("Algorithm not setup here");
            }
        }
    }
}