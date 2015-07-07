package com.gamesbykevin.framework.maze;

import com.gamesbykevin.framework.resources.Disposable;
import java.awt.Graphics;
import java.util.Random;

/**
 * Necessary classes each maze must implement
 * @author GOD
 */
public interface IMaze extends Disposable
{
    /**
     * Update the maze generation
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    public void update(final Random random) throws Exception;
    
    /**
     * Render the maze whether it be the progress, or a basic visual representation.
     * @param graphics Object to write to, to create the final image
     */
    public void render(final Graphics graphics);
    
    /**
     * Verify the location is within the maze.
     * @param col Column
     * @param row Row
     * @return true if the location is in the maze, false otherwise
     */
    public boolean hasBounds(final int col, final int row);
}
