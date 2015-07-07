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
    
    public SidewinderTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Maze maze = new Sidewinder(COLS, ROWS);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Maze maze = new Sidewinder(COLS, ROWS);
        maze.dispose();
        maze = null;
    }
    
    @Before
    @Override
    public void setUp() 
    {
        maze = new Sidewinder(COLS, ROWS);
        assertNotNull(maze);
    }
    
    @After
    @Override
    public void tearDown() 
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
            final int totalCols = getRandom().nextInt(COLS - MIN) + MIN;
            final int totalRows = getRandom().nextInt(ROWS - MIN) + MIN;
            
            maze = new Sidewinder(totalCols, totalRows);
            
            for (int col1 = 0; col1 < maze.getCols(); col1++)
            {
                for (int row1 = 0; row1 < maze.getRows(); row1++)
                {
                    final Room other = maze.getRoom(col1, row1);
                    
                    for (int col = 0; col < maze.getCols(); col++)
                    {
                        for (int row = 0; row < maze.getRows(); row++)
                        {
                            //don't check the same location
                            if (col == col1 && row == row1)
                                continue;
                            
                            final Room room = maze.getRoom(col, row);
                            
                            //assume each room has their own id
                            assertFalse(room.hasId(other));
                        }
                    }
                }
            }
            
            System.out.println("Creating Maze " + maze.toString() + " " + index);
            
            while (!maze.isGenerated())
            {
                maze.update(super.getRandom());
            }
            
            System.out.println("Maze Created " + maze.toString() + " " + index);

            //make sure there aren't any rooms with 4 walls
            for (int col = 0; col < maze.getCols(); col++)
            {
                for (int row = 0; row < maze.getRows(); row++)
                {
                    final Room room = maze.getRoom(col, row);
                    
                    assertNotNull(room);
                    assertNotNull(room.getWalls());
                    
                    if (room.getWalls().size() == 4)
                        System.out.println("Room has 4 walls (" + col + "," + row + ")");
                    
                    assertTrue(room.getWalls().size() < 4);
                }
            }
        }
    }
    
    @Test
    @Override
    public void disposeTest() 
    {
        maze = new Sidewinder(COLS, ROWS);
        maze.dispose();
        maze = null;
        assertNull(maze);
    }
}