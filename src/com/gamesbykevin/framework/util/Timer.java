package com.gamesbykevin.framework.util;

import com.gamesbykevin.framework.resources.Disposable;

import java.util.concurrent.TimeUnit;

public final class Timer 
{
    //how much time is remaining on this timer
    private long remaining;
    
    //what is the start time
    private long reset;
    
    //do we pause timeDeduction
    private boolean pause;
    
    public Timer()
    {   //we are not limiting time duration
        this(0);
    }
    
    public Timer(final long reset)
    {
        setReset(reset);
        setRemaining(reset);
    }
    
    /**
     * Update this timer by subtracting the deduction from the remaining time
     * 
     * @param deduction The amount of time to remove from timer
     */
    public void update(final long deduction)
    {
        //if this timer is not paused deduct time from remaining time
        if (!hasPause())
            setRemaining(getRemaining() - deduction);
    }
    
    /**
     * Has the timer started counting down
     * @return Return true if the time remaining is not equal to the reset time, false otherwise
     */
    public boolean hasStarted()
    {
        return (getRemaining() != getReset());
    }
    
    /**
     * Pause the timer, so even when update 
     * is called the timer will not update.
     * 
     * @param boolean
     */
    public void setPause(final boolean pause)
    {
        this.pause = pause;
    }
    
    /**
     * Is this timer paused
     * @return boolean
     */
    private boolean hasPause()
    {
        return this.pause;
    }
    
    /**
     * Is the remaining time less than or equal to 0
     * 
     * @return boolean
     */
    public boolean hasTimePassed()
    {
        return (getRemaining() <= 0);
    }
    
    /**
     * Set the time remaining back to the value that was set for reset
     */
    public void reset()
    {
        setRemaining(getReset());
    }
    
    /**
     * Change the reset time
     * @param reset 
     */
    public final void setReset(final long reset)
    {
        this.reset = reset;
    }
    
    /**
     * Set the time remaining for this Timer
     * @param remaining 
     */
    public final void setRemaining(final long remaining)
    {
        this.remaining = remaining;
    }
    
    /**
     * How much time is remaining for this Timer
     * @return long
     */
    public long getRemaining()
    {
        return this.remaining;
    }
    
    /**
     * Get the reset time
     * @return long
     */
    public long getReset()
    {
        return this.reset;
    }
    
    /**
     * Return a % representing completion towards the remaining time
     * @return float results will range from 0.0 (0%) to 1.0 (100%)
     */
    public float getProgress()
    {
        return (float)((double)(getReset() - getRemaining()) / (double)getReset());
    }
    
    public String getDescPassed(final String format)
    {   //gets the time passed in specified String format
        return getDesc(format, reset - remaining);
    }
    
    /**
     * Get the time remaining in the specified String format
     * @param format
     * @return Time remaining as a friendly String
     */
    public String getDescRemaining(final String format)
    {   
        return getDesc(format, remaining);
    }
    
    private String getDesc(final String format, final long time)
    {
        //MUST CONVERT NANOSECONDS TO MILLISECONDS
        long milliSeconds = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
        
        return DateHelper.getFormattedDate(format, milliSeconds);
    }
}