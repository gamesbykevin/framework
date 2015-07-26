package com.gamesbykevin.framework.ai;

import com.gamesbykevin.framework.maze.algorithm.RecursiveBacktracking;
import com.gamesbykevin.framework.maze.Maze;
import com.gamesbykevin.framework.maze.MazeHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

/**
 * AStar unit test
 * @author GOD
 */
public class AStarTest 
{
    //object used to make random decisions
    private static final Random RANDOM = new Random();
    
    //our maze object conatining the rooms
    private Maze maze;
    
    //our a* object
    private AStar astar;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //create maze of specified dimensions
        Maze maze = createMaze(0, 0, 5);
        
        //create new instance
        AStar astar = new AStar(
                maze.getStart(), 
                maze.getFinish(), 
                maze.getRooms());
        
        astar.generate();
        assertNotNull(astar.getShortestPath());
        
        //create new instance
        astar = new AStar(
                (int)maze.getStart().getCol(), 
                (int)maze.getStart().getRow(), 
                (int)maze.getFinish().getCol(), 
                (int)maze.getFinish().getRow(), 
                maze.getRooms());

        astar.generate();
        assertNotNull(astar.getShortestPath());
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        //create maze of specified dimensions
        Maze maze = createMaze(0, 0, 5);
        
        //create new instance
        AStar astar = new AStar(
                maze.getStart(), 
                maze.getFinish(), 
                maze.getRooms());
        
        astar.dispose();
    }
    
    @Before
    public void setUp() throws Exception
    {
        //create the maze
        setupMaze(0, 0, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
    }
    
    @After
    public void tearDown() 
    {
        astar.dispose();
        assertNull(astar.getShortestPath());
        astar = null;
        assertNull(astar);
    }
    
    @Test
    public void getShortestPathTest() throws Exception
    {
        final int startCol = 2;
        final int startRow = 3;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.generate();
        
        //assume the start position
        assertTrue(maze.getStart().getCol() == startCol);
        assertTrue(maze.getStart().getRow() == startRow);
        
        //assume the first element is the finish
        assertTrue(astar.getShortestPath().get(0).getCol() == maze.getFinishCol());
        assertTrue(astar.getShortestPath().get(0).getRow() == maze.getFinishRow());
        
        //assume the last element is the start
        assertTrue(astar.getShortestPath().get(astar.getShortestPath().size() - 1).getCol() == startCol);
        assertTrue(astar.getShortestPath().get(astar.getShortestPath().size() - 1).getRow() == startRow);
    }
    
    @Test
    public void setDiagonalTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        
        astar.setDiagonal(true);
        astar.setDiagonal(false);
    }
    
    @Test
    public void setRoomsTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        
        astar.setRooms(maze.getRooms());
        astar.setRooms(null);
    }
    
    @Test
    public void setStartColumnTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.setStartColumn(2);
        
        astar.generate();
        
        //assume the last element is the start
        assertTrue(astar.getShortestPath().get(astar.getShortestPath().size() - 1).getCol() == 2);
    }
    
    @Test
    public void setStartRowTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.setStartRow(4);
        
        astar.generate();
        
        //assume the last element is the start
        assertTrue(astar.getShortestPath().get(astar.getShortestPath().size() - 1).getRow() == 4);
    }
    
    
    @Test
    public void setGoalColumnTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.setGoalColumn(2);
        
        astar.generate();
        
        //assume the last element is the start
        assertTrue(astar.getShortestPath().get(0).getCol() == 2);
    }
    
    @Test
    public void setGoalRowTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.setGoalRow(4);
        
        astar.generate();
        
        //assume the last element is the start
        assertTrue(astar.getShortestPath().get(0).getRow() == 4);
    }
    
    @Test
    public void generateTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.generate();
        
        astar.setRooms(maze.getRooms());
        astar.setStartColumn(0);
        astar.setStartRow(0);
        astar.setGoalColumn(0);
        astar.setGoalRow(4);
        astar.generate();
    }
    
    @Test
    public void disposeTest() throws Exception
    {
        final int startCol = 0;
        final int startRow = 0;
        
        //create the maze
        setupMaze(startCol, startRow, 5);
        
        //create new instance
        astar = new AStar(maze.getStart(), maze.getFinish(), maze.getRooms());
        astar.generate();
        assertNotNull(astar.getShortestPath());
        astar.dispose();
        assertNull(astar.getShortestPath());
    }
    
    private static Maze createMaze(final int startCol, final int startRow, final int dimensions) throws Exception
    {
        //create maze of specified dimensions
        Maze maze = new RecursiveBacktracking(dimensions, dimensions);
        
        //set the start
        maze.setStartLocation(startCol, startRow);
        
        while (!maze.isGenerated())
        {
            maze.update(RANDOM);
        }
        
        //calculate cost and find finish
        MazeHelper.locateFinish(maze);
        
        return maze;
    }
    
    private void setupMaze(final int startCol, final int startRow, final int dimensions) throws Exception
    {
        //create maze of specified dimensions
        maze = new RecursiveBacktracking(dimensions, dimensions);
        
        //set the start
        maze.setStartLocation(startCol, startRow);
        
        while (!maze.isGenerated())
        {
            maze.update(RANDOM);
        }
        
        //calculate cost and find finish
        MazeHelper.locateFinish(maze);
    }
}