package com.gamesbykevin.framework.util;

import java.text.*;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Timer 
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
    
    public void update(final long timeDeduction)
    {
        //if this timer is not paused deduct time from remaining time
        if (!hasPause())
            setRemaining(getRemaining() - timeDeduction);
    }
    
    public void setPause(final boolean pause)
    {
        this.pause = pause;
    }
    
    private boolean hasPause()
    {
        return this.pause;
    }
    
    public boolean hasTimePassed()
    {
        return (getRemaining() <= 0);
    }
    
    public void resetRemaining()
    {
        setRemaining(getReset());
    }
    
    public void setReset(final long reset)
    {
        this.reset = reset;
    }
    
    public void setRemaining(final long remaining)
    {
        this.remaining = remaining;
    }
    
    public long getRemaining()
    {
        return this.remaining;
    }
    
    public long getReset()
    {
        return this.reset;
    }
    
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
        
        return DateHelper.getFormatedDate(format, milliSeconds);
    }
}