package com.gamesbykevin.framework.labyrinth;

import com.gamesbykevin.framework.resources.Disposable;

import java.util.Random;

public interface IAlgorithm extends Disposable
{
    /**
     * Every time update is called the maze creation will make progress toward completion
     */
    public void update(final Random random) throws Exception;
    
    /**
     * This method will create all needed objects and set position at starting point.
     * Also, will set the progress goal.
     */
    public void initialize() throws Exception;
}