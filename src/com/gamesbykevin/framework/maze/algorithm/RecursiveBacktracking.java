package com.gamesbykevin.framework.maze.algorithm;

import com.gamesbykevin.framework.base.Cell;
import com.gamesbykevin.framework.maze.Maze;
import com.gamesbykevin.framework.maze.MazeHelper;
import com.gamesbykevin.framework.maze.Room;
import com.gamesbykevin.framework.maze.Room.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Recursive Backtracking maze generation algorithm
 * @author GOD
 */
public class RecursiveBacktracking extends Maze
{
    //our current location
    private int col = 0, row = 0;
    
    //list of places visited, used to help generate the maze
    private List<Cell> steps;
    
    public RecursiveBacktracking(final int cols, final int rows) throws Exception
    {
        super(cols, rows);
        
        //set 4 walls for each room
        super.populateRooms();
        
        //create new list, to track the steps
        this.steps = new ArrayList<>();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        steps.clear();
        steps = null;
    }
    
    /**
     * Create our maze
     * @param random Object used to make random decisions
     */
    @Override
    public void update(final Random random) throws Exception
    {
        //if generated no need to continue
        if (isGenerated())
            return;
        
        //if we haven't visited 1 room, we are just starting
        if (!MazeHelper.hasVisited(this))
        {
            //store the current location as the start location
            col = super.getStartCol();
            row = super.getStartRow();
            
            //add the current location as part of the steps
            steps.add(new Cell(col, row));
        }
        
        //create empty list of optional walls
        List<Wall> options = new ArrayList<>();

        //check the west, make sure we are inbounds and have not visited our neighbor
        if (hasBounds(col - 1, row) && !getRoom(col - 1, row).hasVisited())
            options.add(Wall.West);

        //check the east, make sure we are inbounds and have not visited our neighbor
        if (hasBounds(col + 1, row) && !getRoom(col + 1, row).hasVisited())
            options.add(Wall.East);

        //check the north, make sure we are inbounds and have not visited our neighbor
        if (hasBounds(col, row - 1) && !getRoom(col, row - 1).hasVisited())
            options.add(Wall.North);

        //check the south, make sure we are inbounds and have not visited our neighbor
        if (hasBounds(col, row + 1) && !getRoom(col, row + 1).hasVisited())
            options.add(Wall.South);

        //if there are no options we have to back track
        if (options.isEmpty())
        {
            //the previous location was a dead end, so remove it
            steps.remove(steps.size() - 1);
            
            //now get the location before that
            col = (int)steps.get(steps.size() - 1).getCol();
            row = (int)steps.get(steps.size() - 1).getRow();
        }
        else
        {
            //pick a random wall from our options
            final Wall wall = options.get(random.nextInt(options.size()));
            
            //remove the wall from our current
            getRoom(col, row).removeWall(wall);

            //mark this as visited
            getRoom(col, row).setVisited(true);

            //remove the wall from our neighbor
            switch (wall)
            {
                case North:
                    //now set the new location
                    col = col;
                    row = row - 1;

                    //remove the wall
                    getRoom(col, row).removeWall(Wall.South);

                    //mark this as visited
                    getRoom(col, row).setVisited(true);
                    break;

                case South:
                    //now set the new location
                    col = col;
                    row = row + 1;

                    //remove the wall
                    getRoom(col, row).removeWall(Wall.North);

                    //mark this as visited
                    getRoom(col, row).setVisited(true);
                    break;

                case West:
                    //now set the new location
                    col = col - 1;
                    row = row;

                    //remove the wall
                    getRoom(col, row).removeWall(Wall.East);

                    //mark this as visited
                    getRoom(col, row).setVisited(true);
                    break;

                case East:
                    //now set the new location
                    col = col + 1;
                    row = row;

                    //remove the wall
                    getRoom(col, row).removeWall(Wall.West);

                    //mark this as visited
                    getRoom(col, row).setVisited(true);
                    break;

                default:
                    throw new Exception("The wall was not found here " + wall.toString());
            }
            
            //add the current location as part of the steps
            steps.add(new Cell(col, row));
        }
        
        //update the progress
        super.updateProgress();
    }
}