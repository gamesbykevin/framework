package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.resources.Progress;
import com.gamesbykevin.framework.resources.Disposable;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

public final class Labyrinth implements Disposable
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
    
    //our random number generator
    private final Random random;
    
    //the seed used to generate random numbers
    private final long seed = System.nanoTime();
    
    //has the maze been initialied
    private boolean initialized = false;
    
    /**
     * Create a new labyrinth using the specified Algorithm and the number of columns/rows
     * 
     * @param cols
     * @param rows
     * @param algorithm 
     */
    public Labyrinth(final int cols, final int rows, final Algorithm algorithm) throws Exception
    {
        //set the algorithm we will be using
        this.algorithm = algorithm;
        
        //create new random number generator
        this.random = new Random(seed);
        
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
    
    /**
     * Get the algorithm used in this maze
     * @return Algorithm
     */
    public Algorithm getAlgorithm()
    {
        return algorithm;
    }
    
    @Override
    public void dispose()
    {
        try
        {
            switch(getAlgorithm())
            {
                case DepthFirstSearch:
                    depthFirstSearch.dispose();
                    depthFirstSearch = null;
                    break;

                case Prims:
                    prims.dispose();
                    prims = null;
                    break;

                case Kruskals:
                    kruskals.dispose();
                    kruskals = null;
                    break;

                case Ellers:
                    ellers.dispose();
                    ellers = null;
                    break;

                case HuntKill:
                    huntKill.dispose();
                    huntKill = null;
                    break;

                case Sidewinder:
                    sidewinder.dispose();
                    sidewinder = null;
                    break;

                default:
                    throw new Exception("Algorithm not setup here");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        algorithm = null;
    }
    
    /**
     * Initialize the necessary objects needed to generate the maze.
     * 
     * @throws Exception If the start Location is not set
     */
    private void initialize() throws Exception
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
     * Generate the maze continuously until the entire maze is generated
     * @throws Exception 
     */
    public void generate() throws Exception
    {
        //continue to update until the maze has been generated
        while (!isComplete())
        {
            //update the maze creation
            update();
        }
    }
    
    /**
     * Generate the maze once every time update() is called. 
     * Once the maze isComplete() no further generation will occur. 
     * @throws Exception 
     */
    public void update() throws Exception
    {
        if (isComplete())
            return;
        
        if (!initialized)
            setInitialized();
        
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.update(random);
                break;
                
            case Prims:
                prims.update(random);
                break;
                
            case Kruskals:
                kruskals.update(random);
                break;
                
            case Ellers:
                ellers.update(random);
                break;

            case HuntKill:
                huntKill.update(random);
                break;
                
            case Sidewinder:
                sidewinder.update(random);
                break;
                
            default:
                throw new Exception("Algorithm not setup here");
        }
        
        //if the maze is complete now after update, calculate the cost of each Location
        if (isComplete())
            setCost();
    }
    
    private void setInitialized() throws Exception
    {
        //setup objects for the maze
        initialize();

        //mark as initialized
        initialized = true;
    }
    
    private void setCost() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.setCost();
                break;
                
            case Prims:
                prims.setCost();
                break;
                
            case Kruskals:
                kruskals.setCost();
                break;
                
            case Ellers:
                ellers.setCost();
                break;

            case HuntKill:
                huntKill.setCost();
                break;
                
            case Sidewinder:
                sidewinder.setCost();
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
     * Get Location at Cell column/row
     * @param cell
     * @return Location
     * @throws Exception 
     */
    public Location getLocation(Cell cell) throws Exception
    {
        return getLocation((int)cell.getCol(), (int)cell.getRow());
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
    
    /**
     * Returns true if the Cell(Column,Row) specified are located inside the maze.
     * @param Cell cell
     * @return boolean
     * @throws Exception 
     */
    public boolean hasLocation(final Cell cell) throws Exception
    {
        return hasLocation((int)cell.getCol(), (int)cell.getRow());
    }
    
    /**
     * Returns true if the (Column,Row) specified are located inside the maze.
     * @param col Column
     * @param row Row
     * @return boolean
     * @throws Exception 
     */
    public boolean hasLocation(int col, int row) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.hasLocation(col, row);
                
            case Prims:
                return prims.hasLocation(col, row);
                
            case Kruskals:
                 return kruskals.hasLocation(col, row);
                
             case Ellers:
                return ellers.hasLocation(col, row);
                 
            case HuntKill:
                return huntKill.hasLocation(col, row);
                 
            case Sidewinder:
                return sidewinder.hasLocation(col, row);
                
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
     * Get the Start Location
     * @return Location
     * @throws Exception 
     */
    public Cell getStart() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.getStart();
                
            case Prims:
                return prims.getStart();
                
            case Kruskals:
                return kruskals.getStart();
                
            case Ellers:
                return ellers.getStart();

            case HuntKill:
                return huntKill.getStart();
                
            case Sidewinder:
                return sidewinder.getStart();
                
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
     * Get the Finish Location
     * @return Cell
     * @throws Exception 
     */
    public Cell getFinish() throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                return depthFirstSearch.getFinish();
                
            case Prims:
                return prims.getFinish();
                
            case Kruskals:
                return kruskals.getFinish();
                
            case Ellers:
                return ellers.getFinish();

            case HuntKill:
                return huntKill.getFinish();
                
            case Sidewinder:
                return sidewinder.getFinish();
                
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
        if (!initialized)
            setInitialized();
        
        switch(getAlgorithm())
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
    public void renderProgress(final Graphics graphics, final Rectangle screen) throws Exception
    {
        switch(algorithm)
        {
            case DepthFirstSearch:
                depthFirstSearch.renderProgress(graphics, screen);
                break;

            case Prims:
                prims.renderProgress(graphics, screen);
                break;

            case Kruskals:
                kruskals.renderProgress(graphics, screen);
                break;

            case Ellers:
                ellers.renderProgress(graphics, screen);
                break;

            case HuntKill:
                huntKill.renderProgress(graphics, screen);
                break;
                
            case Sidewinder:
                sidewinder.renderProgress(graphics, screen);
                break;

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
    public void render(final Graphics graphics, final Rectangle screen) throws Exception
    {
        if (!isComplete())
        {
            renderProgress(graphics, screen);
        }
        else
        {
            switch(algorithm)
            {
                case DepthFirstSearch:
                    depthFirstSearch.render(graphics, screen);
                    break;

                case Prims:
                    prims.render(graphics, screen);
                    break;

                case Kruskals:
                    kruskals.render(graphics, screen);
                    break;

                case Ellers:
                    ellers.render(graphics, screen);
                    break;

                case HuntKill:
                    huntKill.render(graphics, screen);
                    break;

                case Sidewinder:
                    sidewinder.render(graphics, screen);
                    break;
                    
                default:
                    throw new Exception("Algorithm not setup here");
            }
        }
    }
}