package com.gamesbykevin.framework.maze;

import com.gamesbykevin.framework.maze.Room.Wall;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Maze Helper unit test
 * @author GOD
 */
public class MazeHelperTest extends MazeTest
{
    public MazeHelperTest() throws Exception
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Test
    public void joinRoomsTest() throws Exception
    {
        //don't continue if not big enough
        if (getMaze().getCols() < 2)
            return;
        if (getMaze().getRows() < 2)
            return;
        
        //fill each room with 4 walls
        getMaze().populateRooms();
        
        Room room1 = getMaze().getRoom(0, 0);
        Room room2 = getMaze().getRoom(1, 0);

        assertTrue(room1.getWalls().size() == Wall.values().length);
        assertTrue(room2.getWalls().size() == Wall.values().length);

        MazeHelper.joinRooms(room1, room2);

        assertFalse(room1.getWalls().size() == Wall.values().length);
        assertFalse(room2.getWalls().size() == Wall.values().length);

        assertTrue(room1.getWalls().size() == Wall.values().length - 1);
        assertTrue(room2.getWalls().size() == Wall.values().length - 1);

        assertFalse(room1.hasWall(Wall.East));
        assertFalse(room2.hasWall(Wall.West));
        
        //fill each room with 4 walls
        getMaze().populateRooms();
        
        room1 = getMaze().getRoom(1, 0);
        room2 = getMaze().getRoom(0, 0);
        
        assertTrue(room1.getWalls().size() == Wall.values().length);
        assertTrue(room2.getWalls().size() == Wall.values().length);
        
        MazeHelper.joinRooms(room1, room2);
        
        assertFalse(room1.getWalls().size() == Wall.values().length);
        assertFalse(room2.getWalls().size() == Wall.values().length);
        
        assertTrue(room1.getWalls().size() == Wall.values().length - 1);
        assertTrue(room2.getWalls().size() == Wall.values().length - 1);
        
        assertFalse(room1.hasWall(Wall.West));
        assertFalse(room2.hasWall(Wall.East));
        
        
        //fill each room with 4 walls
        getMaze().populateRooms();
        
        room1 = getMaze().getRoom(0, 1);
        room2 = getMaze().getRoom(0, 0);
        
        assertTrue(room1.getWalls().size() == Wall.values().length);
        assertTrue(room2.getWalls().size() == Wall.values().length);
        
        MazeHelper.joinRooms(room1, room2);
        
        assertFalse(room1.getWalls().size() == Wall.values().length);
        assertFalse(room2.getWalls().size() == Wall.values().length);
        
        assertTrue(room1.getWalls().size() == Wall.values().length - 1);
        assertTrue(room2.getWalls().size() == Wall.values().length - 1);
        
        assertFalse(room1.hasWall(Wall.North));
        assertFalse(room2.hasWall(Wall.South));
        
        
        //fill each room with 4 walls
        getMaze().populateRooms();
        
        room1 = getMaze().getRoom(0, 0);
        room2 = getMaze().getRoom(0, 1);
        
        assertTrue(room1.getWalls().size() == Wall.values().length);
        assertTrue(room2.getWalls().size() == Wall.values().length);
        
        MazeHelper.joinRooms(room1, room2);
        
        assertFalse(room1.getWalls().size() == Wall.values().length);
        assertFalse(room2.getWalls().size() == Wall.values().length);
        
        assertTrue(room1.getWalls().size() == Wall.values().length - 1);
        assertTrue(room2.getWalls().size() == Wall.values().length - 1);
        
        assertFalse(room1.hasWall(Wall.South));
        assertFalse(room2.hasWall(Wall.North));
    }
    
    @Test
    public void calculateCostTest() throws Exception
    {
        while (!getMaze().isGenerated())
        {
            getMaze().update(getRandom());
        }
        
        final int startCol = 0;
        final int startRow = 0;
        
        getMaze().setStartLocation(startCol, startRow);
        
        //calculate the cost
        MazeHelper.calculateCost(getMaze());
        
        for (int col = 0; col < getMaze().getCols(); col++)
        {
            for (int row = 0; row < getMaze().getRows(); row++)
            {
                //don't check the start location
                if (startCol == col && startRow == row)
                {
                    //start location will have a cost of 0
                    assertTrue(getMaze().getRoom(col, row).getCost() == 0);
                }
                else
                {
                    //anything else should be greater than 0
                    assertTrue(getMaze().getRoom(col, row).getCost() > 0);
                }
            }
        }
    }
    
    @Test
    public void locateFinishTest() throws Exception
    {
        while (!getMaze().isGenerated())
        {
            getMaze().update(getRandom());
        }
        
        //calculate cost
        MazeHelper.calculateCost(getMaze());
        
        int finishCol = 0;
        int finishRow = 0;
        
        int cost = 0;
        
        for (int row = 0; row < getMaze().getRows(); row++)
        {
            for (int col = 0; col < getMaze().getCols(); col++)
            {
                //get the current room
                final Room room = getMaze().getRoom(col, row);
                
                //set the current champion
                if (room.getCost() > cost)
                {
                    cost = room.getCost();
                    
                    finishCol = col;
                    finishRow = row;
                }
            }
        }
        
        //now we know who should win, so lets calculate the finish
        MazeHelper.locateFinish(getMaze());
        
        //assume this is the location
        assertTrue(getMaze().getFinishCol() == finishCol);
        assertTrue(getMaze().getFinishRow() == finishRow);
    }
    
    @Test
    public void hasVisitedTest() throws Exception
    {
        //first mark all rooms not visited
        for (int col = 0; col < getMaze().getCols(); col++)
        {
            for (int row = 0; row < getMaze().getRows(); row++)
            {
                //get the current room
                final Room room = getMaze().getRoom(col, row);
                
                //mark as not visited
                room.setVisited(false);
            }
        }
        
        assertFalse(MazeHelper.hasVisited(getMaze()));
        
        getMaze().getRoom(0, 0).setVisited(true);
        
        assertTrue(MazeHelper.hasVisited(getMaze()));
    }
    
    @Test
    public void setVisitedAllTest() throws Exception
    {
        //mark all visited
        MazeHelper.setVisitedAll(getMaze(), true);
        
        //check all rooms
        for (int col = 0; col < getMaze().getCols(); col++)
        {
            for (int row = 0; row < getMaze().getRows(); row++)
            {
                //get the current room
                final Room room = getMaze().getRoom(col, row);
                
                //assume true
                assertTrue(room.hasVisited());
            }
        }
        
        //mark all not visited
        MazeHelper.setVisitedAll(getMaze(), false);
        
        //check all rooms
        for (int col = 0; col < getMaze().getCols(); col++)
        {
            for (int row = 0; row < getMaze().getRows(); row++)
            {
                //get the current room
                final Room room = getMaze().getRoom(col, row);
                
                //assume true
                assertFalse(room.hasVisited());
            }
        }
    }
}