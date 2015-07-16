package com.gamesbykevin.framework.maze.algorithm;

import com.gamesbykevin.framework.maze.Maze;
import com.gamesbykevin.framework.maze.MazeTest;
import com.gamesbykevin.framework.maze.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sidewinder unit test
 * @author GOD
 */
public class SidewinderTest extends MazeTest
{
    //our maze object
    private Maze maze;
    
    public SidewinderTest() throws Exception
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Maze maze = new Sidewinder(COLS, ROWS);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Maze maze = new Sidewinder(COLS, ROWS);
        maze.dispose();
        maze = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        maze = new Sidewinder(COLS, ROWS);
        assertNotNull(maze);
    }
    
    @After
    @Override
    public void tearDown() throws Exception
    {
        maze = new Sidewinder(COLS, ROWS);
        maze.dispose();
        maze = null;
    }
    
    @Test
    @Override
    public void updateTest() throws Exception
    {
        for (int index = 0; index < CREATE_MAZE_LIMIT; index++)
        {
            final int totalCols = getRandomCols();
            final int totalRows = getRandomRows();
            
            maze = new Sidewinder(totalCols, totalRows);
            
            //make sure each room has its own id
            super.roomIdTest(maze);
            
            //generate the maze
            super.generateMazeTest(maze, index);
            
            //make sure there are no rooms with all 4 walls
            super.roomWallTest(maze);
        }
    }
    
    @Test
    @Override
    public void disposeTest() throws Exception
    {
        maze = new Sidewinder(COLS, ROWS);
        maze.dispose();
        maze = null;
        assertNull(maze);
    }
}