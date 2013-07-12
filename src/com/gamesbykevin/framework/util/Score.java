package com.gamesbykevin.framework.util;

public class Score 
{
    private int score = 0;             //actual score
    private int displayScore = 0;      //displayScore that always chases score
    private int chaseIncrement = 1;    //how much to add to the displayScore until reached the score
    private int maxScore = 0;          //maximum score allowed
    
    public Score()
    {
        
    }
    
    public Score(int chaseIncrement)
    {
        this.chaseIncrement = chaseIncrement;
    }
    
    public void manage()
    {
        if (getDisplayScore() < getScore())
            setDisplayScore(getDisplayScore() + getChaseIncrement());
        
        if (getDisplayScore() > getScore())
            setDisplayScore(getScore());
    }
    
    public void setChaseIncrement(int chaseIncrement)
    {
        this.chaseIncrement = chaseIncrement;
    }
    
    private int getChaseIncrement()
    {
        return this.chaseIncrement;
    }
    
    public void addScore(int score)
    {
        setScore(getScore() + score);
        
        if (getScore() > getMaxScore())
        {
            setScore(getMaxScore());
        }
    }
    
    public int getScore()
    {
        return this.score;
    }
    
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public void setMaxScore(int maxScore)
    {
        this.maxScore = maxScore;
    }
    
    public int getMaxScore()
    {
        return this.maxScore;
    }
    
    private void setDisplayScore(int displayScore)
    {
        this.displayScore = displayScore;
    }
    
    public int getDisplayScore()
    {
        return this.displayScore;
    }
}