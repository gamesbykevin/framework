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
 * Binary Tree unit test
 * @author GOD
 */
public class BinaryTreeTest extends MazeTest
{
    //our maze object
    private Maze maze;
    
    public BinaryTreeTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Maze maze = new BinaryTree(COLS, ROWS);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Maze maze = new BinaryTree(COLS, ROWS);
        maze.dispose();
        maze = null;
    }
    
    @Before
    @Override
    public void setUp() 
    {
        maze = new BinaryTree(COLS, ROWS);
        assertNotNull(maze);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        maze = new BinaryTree(COLS, ROWS);
        maze.dispose();
        maze = null;
    }
    
    @Test
    @Override
    public void updateTest() throws Exception
    {
        for (int index = 0; index < CREATE_MAZE_LIMIT; index++)
        {
            final int totalCols = getRandom().nextInt(COLS - MIN) + MIN;
            final int totalRows = getRandom().nextInt(ROWS - MIN) + MIN;
            
            maze = new BinaryTree(totalCols, totalRows);
            
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
    public void disposeTest() 
    {
        maze = new BinaryTree(COLS, ROWS);
        maze.dispose();
        maze = null;
        assertNull(maze);
    }
}