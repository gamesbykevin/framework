package com.gamesbykevin.framework.maze;

import com.gamesbykevin.framework.maze.algorithm.Kruskals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Maze unit test
 * @author GOD
 */
public class MazeTest 
{
    //random object
    private Random random;
    
    //our maze object
    private Maze maze;
    
    protected static final int COLS = 21;
    protected static final int ROWS = 21;
    
    /**
     * Static test image
     */
    public static final BufferedImage IMAGE = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    
    /**
     * The number of mazes to create
     */
    protected static final int CREATE_MAZE_LIMIT = 500;
    
    //the minimum dimension size of the maze
    protected static final int MIN = 10;
    
    public MazeTest() throws Exception
    {
        random = new Random();
        
        //create a new instance of this maze, just for testing
        this.maze = new Kruskals(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION);
    }
    
    protected int getRandomCols()
    {
        return (getRandom().nextInt(COLS - MIN) + MIN);
    }
    
    protected int getRandomRows()
    {
        return (getRandom().nextInt(ROWS - MIN) + MIN);
    }
    
    protected Random getRandom()
    {
        return this.random;
    }

    public Maze getMaze()
    {
        return this.maze;
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Maze maze = new Kruskals(10, 10);
        
        assertTrue(Maze.DEFAULT_MAZE_DIMENSION == 10);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Maze maze = new Kruskals(10, 10);
        maze.dispose();
        maze = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume each room has it's own id
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
    }
    
    @After
    public void tearDown() throws Exception
    {
        maze.dispose();
        maze = null;
    }
    
    @Test
    public void getProgressTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume not null
        assertNotNull(maze.getProgress());
        
        //assume true
        assertTrue(maze.getProgress().getGoal() == (maze.getCols() * maze.getRows()));
    }
    
    @Test
    public void isGeneratedTest() throws Exception
    {
        //assume false
        assertFalse(maze.isGenerated());
        
        while (!maze.isGenerated())
        {
            maze.update(random);
        }
        
        //assume true
        assertTrue(maze.isGenerated());
    }
    
    @Test
    public void getStartRowTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getStartRow() == 0);
        
        //assign a value
        maze.setStartLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getStartRow() == 3);
    }
    
    @Test
    public void getStartColTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getStartCol() == 0);
        
        //assign a value
        maze.setStartLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getStartCol() == 6);
    }
    
    @Test
    public void getStartLocationTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getStartCol() == 0);
        assertTrue(maze.getStartRow() == 0);
        
        //assign a value
        maze.setStartLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getStartCol() == 6);
        assertTrue(maze.getStartRow() == 3);
    }

    @Test
    public void getFinishRowTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getFinishRow() == 0);
        
        //assign a value
        maze.setFinishLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getFinishRow()== 3);
    }
    
    @Test
    public void getFinishColTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getFinishCol() == 0);
        
        //assign a value
        maze.setFinishLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getFinishCol() == 6);
    }
    
    @Test
    public void getFinishLocationTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //assume true since nothing has been assigned
        assertTrue(maze.getFinishCol()== 0);
        assertTrue(maze.getFinishRow()== 0);
        
        //assign a value
        maze.setFinishLocation(6, 3);
        
        //assume true 
        assertTrue(maze.getFinishCol() == 6);
        assertTrue(maze.getFinishRow() == 3);
    }
    
    @Test
    public void populateRoomsTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
    
        maze.populateRooms();
        
        for (int col = 0; col < maze.getCols(); col++)
        {
            for (int row = 0; row < maze.getRows(); row++)
            {
                assertTrue(maze.getRoom(col, row).getWalls().size() == 4);
            }
        }
    }
    
    @Test
    public void hasBoundsTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION);
        
        assertFalse(maze.hasBounds(-1, COLS));
        assertFalse(maze.hasBounds(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION));
        assertTrue(maze.hasBounds(Maze.DEFAULT_MAZE_DIMENSION - 1, Maze.DEFAULT_MAZE_DIMENSION - 1));
        assertTrue(maze.hasBounds(0,0));
    }
    
    @Test
    public void disposeTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION);
        
        assertNotNull(maze.getRooms());
        
        maze.dispose();
        
        assertNull(maze.getRooms());
    }
    
    @Test
    public void getRoomTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        //only assume if in bounds
        if (maze.hasBounds(COLS - 3, ROWS - 7))
            assertNotNull(maze.getRoom(COLS - 3, ROWS - 7));
        
        assertNull(maze.getRoom(-1, 0));
        assertNotNull(maze.getRoom(0, 0));
        
        //only assume if in bounds
        if (maze.hasBounds(COLS - 1, ROWS - 1))
            assertNotNull(maze.getRoom(COLS - 1, ROWS - 1));
        
        assertNull(maze.getRoom(COLS, ROWS));
        
        for (int col = 0; col < maze.getCols(); col++)
        {
            for (int row = 0; row < maze.getRows(); row++)
            {
                assertNotNull(maze.getRoom(col, row));
                assertTrue(maze.getRoom(col, row).getCol() == col);
                assertTrue(maze.getRoom(col, row).getRow() == row);
            }
        }
    }
    
    @Test
    public void getRoomsTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        assertNotNull(maze.getRooms());
        assertTrue(maze.getRooms().length == ROWS);
        assertTrue(maze.getRooms()[0].length == COLS);
    }
    
    @Test
    public void getColsTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        assertTrue(maze.getCols() == COLS);
    }
    
    @Test
    public void getRowsTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        
        assertTrue(maze.getRows()== ROWS);
    }
    
    @Test
    public void updateProgrssTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
    
        final int count = maze.getProgress().getCount();
        
        maze.updateProgress();
        
        //there is 0 progress because there are no visited rooms
        assertTrue(maze.getProgress().getCount() == count);
        assertTrue(count == 0);
        
        //now make a room visited
        maze.getRoom(0, 0).setVisited(true);
        
        //progress is based on # of visited rooms
        maze.updateProgress();
        
        assertTrue(maze.getProgress().getCount() == count + 1);
    }
    
    @Test
    public void setDisplayProgressTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(COLS, ROWS);
        maze.setDisplayProgress(true);
        maze.setDisplayProgress(false);
    }
    
    @Test
    public void updateTest() throws Exception
    {
        //create new instance, use this for testing
        maze = new Kruskals(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION);
        
        //call update until the maze is generated
        while (!maze.isGenerated())
        {
            maze.update(random);
        }
    }
    
    @Test
    public void renderTest() throws Exception
    {
        //get the graphics so we can draw
        Graphics graphics = IMAGE.createGraphics();
        
        //create new instance, use this for testing
        maze = new Kruskals(Maze.DEFAULT_MAZE_DIMENSION, Maze.DEFAULT_MAZE_DIMENSION);
        
        //call update until the maze is generated
        while (!maze.isGenerated())
        {
            maze.update(random);
            maze.render(graphics);
        }
        
        maze.render(graphics);
    }
    
    /**
     * Here we are making sure each room has their own unique id
     * @param maze The maze we want to check
     */
    protected void roomIdTest(final Maze maze)
    {
        assertNotNull(maze);
        
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
    }
    
    /**
     * At this point we assume the maze has been generated,<br> 
     * so we make sure there aren't any rooms with 4 walls.<br>
     * @param maze The maze we want to check
     */
    protected void roomWallTest(final Maze maze)
    {
        assertNotNull(maze);
        
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
    
    /**
     * Here we will generate the maze until it is 100% generated
     * @param maze The maze we want to generate
     */
    protected void generateMazeTest(final Maze maze, final int index) throws Exception
    {
        assertNotNull(maze);
        
        System.out.println("Creating Maze " + maze.toString() + " count = " + index);

        while (!maze.isGenerated())
        {
            maze.update(getRandom());
        }
    }
}