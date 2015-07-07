package com.gamesbykevin.framework.maze;

import com.gamesbykevin.framework.maze.Room.Wall;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;

/**
 * Room unit test
 * @author GOD
 */
public class RoomTest 
{
    private Room room;
    
    @BeforeClass
    public static void setUpClass() 
    {
        Room room = new Room(0,0);
        
        for (int col = -100; col <= 100; col++)
        {
            for (int row = -200; row <= 200; row++)
            {
                room = new Room(col, row);
            }
        }
        
        //assume true
        assertTrue(Room.COST_MINIMUM == 0);
        
        //assume there will be 4 walls
        assertTrue(Room.Wall.values().length == 4);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Room room = new Room(0,0);
        
        for (int col = -100; col <= 100; col++)
        {
            for (int row = -200; row <= 200; row++)
            {
                room = new Room(col, row);
                room.dispose();
                room = null;
            }
        }
        
    }
    
    @Before
    public void setUp() 
    {
        room = new Room(0,0);
        assertNotNull(room);
    }
    
    @After
    public void tearDown() 
    {
        room = new Room(0,0);
        room.dispose();
        room = null;
        
        assertNull(room);
    }
    
    @Test
    public void getColTest() 
    {
        for (int col = -100; col <= 100; col++)
        {
            for (int row = -200; row <= 200; row++)
            {
                room = new Room(col, row);
                
                assertTrue(room.getCol() == col);
            }
        }
    }
    
    @Test
    public void getRowTest() 
    {
        for (int col = -100; col <= 100; col++)
        {
            for (int row = -200; row <= 200; row++)
            {
                room = new Room(col, row);
                
                assertTrue(room.getRow() == row);
            }
        }
    }
    
    @Test
    public void disposeTest()
    {
        room = new Room(0,0);
        assertNotNull(room.getWalls());
        room.dispose();
        assertNull(room.getWalls());
    }
    
    @Test
    public void getIdTest()
    {
        room = new Room(0,0);
        assertNotNull(room.getId());
    }
    
    @Test
    public void hasIdTest()
    {
        room = new Room(0,0);
        
        assertFalse(room.hasId(UUID.randomUUID()));
        assertTrue(room.hasId(room.getId()));
        
        Room room2 = new Room(0,0);
        
        assertFalse(room.hasId(room2));
        assertTrue(room2.hasId(room2.getId()));
    }
    
    @Test
    public void setIdTest()
    {
        room = new Room(0,0);
        
        UUID uuid = UUID.randomUUID();
        
        assertFalse(room.hasId(uuid));
        room.setId(uuid);
        assertTrue(room.hasId(uuid));
        
        Room room2 = new Room(0,0);
        
        assertFalse(room.hasId(room2));
        room.setId(room2);
        assertTrue(room.hasId(room2));
    }
    
    @Test
    public void addWallTest()
    {
        room = new Room(0,0);
        
        //add these walls for the first time
        for (Wall wall : Wall.values())
        {
            boolean result = room.addWall(wall);
            
            //they will all add true
            assertTrue(result);
        }
        
        //now add all again
        for (Wall wall : Wall.values())
        {
            boolean result = room.addWall(wall);
            
            //they will all be false, because the walls already exist
            assertFalse(result);
        }
    }
    
    @Test
    public void hasWallTest()
    {
        room = new Room(0,0);
        
        //check all walls
        for (Wall wall : Wall.values())
        {
            assertFalse(room.hasWall(wall));
        }
        
        //check all walls
        for (Wall wall : Wall.values())
        {
            room.addWall(wall);
        }
        
        //check all walls
        for (Wall wall : Wall.values())
        {
            assertTrue(room.hasWall(wall));
        }
    }
    
    @Test
    public void addAllWallsTest()
    {
        room = new Room(0,0);
        
        assertTrue(room.getWalls().isEmpty());
        
        room.addAllWalls();
        
        assertTrue(room.getWalls().size() == Wall.values().length);
    }
    
    @Test
    public void removeAllWallsTest()
    {
        room = new Room(0,0);
        
        assertTrue(room.getWalls().isEmpty());
        
        room.addAllWalls();
        
        assertTrue(room.getWalls().size() == Wall.values().length);
        
        room.removeAllWalls();
        
        assertTrue(room.getWalls().isEmpty());
    }
    
    @Test
    public void removeWallTest()
    {
        room = new Room(0,0);
        room.addAllWalls();
        
        assertTrue(room.hasWall(Wall.East));
        room.removeWall(Wall.East);
        assertFalse(room.hasWall(Wall.East));
    }
    
    @Test
    public void getWallsTest()
    {
        room = new Room(0,0);
        
        assertNotNull(room.getWalls());
        assertTrue(room.getWalls().isEmpty());
        
        room.addWall(Wall.East);
        assertTrue(room.getWalls().size() == 1);
        
        room.addWall(Wall.East);
        assertTrue(room.getWalls().size() == 1);
        
        room.addWall(Wall.West);
        assertTrue(room.getWalls().size() == 2);
    }
    
    @Test
    public void setCostTest()
    {
        room = new Room(0,0);
        
        final int cost = 103;
        
        assertFalse(room.getCost() == cost);
        room.setCost(cost);
        assertTrue(room.getCost() == cost);
    }
    
    @Test
    public void getCostTest()
    {
        room = new Room(0,0);
        
        final int cost = 103;
        
        assertFalse(room.getCost() == cost);
        room.setCost(cost);
        assertTrue(room.getCost() == cost);
    }
    
    @Test
    public void setVisitedTest()
    {
        room = new Room(0,0);
        room.setVisited(true);
        room.setVisited(false);
    }
    
    @Test
    public void hasVisitedTest()
    {
        room = new Room(0,0);
        assertFalse(room.hasVisited());
        room.setVisited(true);
        assertTrue(room.hasVisited());
        room.setVisited(false);
        assertFalse(room.hasVisited());
    }
    
    @Test
    public void hasLocationTest()
    {
        room = new Room(0,0);
        
        Room room1 = new Room(0,0);
        assertTrue(room.hasLocation(room1));
        assertTrue(room.hasLocation(0, 0));
        
        Room room2 = new Room(1,0);
        assertFalse(room.hasLocation(room2));
        assertFalse(room.hasLocation(room2.getCol(), room2.getRow()));
        
        Room room3 = new Room(0,1);
        assertFalse(room.hasLocation(room3));
        assertFalse(room.hasLocation(room3.getCol(), room3.getRow()));
    }
}