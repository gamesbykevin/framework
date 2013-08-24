package com.gamesbykevin.framework.labyrinth;

public interface LabyrinthRules 
{
    /**
     * Every time update is called the maze creation will make progress toward completion
     */
    public void update() throws Exception;
    
    /**
     * This method will create all needed objects and set position at starting point.
     * Also, will set the progress goal.
     */
    public void initialize() throws Exception;
    
    /**
     * Make sure resources are recycled properly
     */
    public void dispose();
}