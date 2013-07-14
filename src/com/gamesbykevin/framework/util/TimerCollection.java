package com.gamesbykevin.framework.util;

import java.util.HashMap;

public class TimerCollection 
{
    public static final String FORMAT_1 = "MM/dd/yyyy HH:mm:ss.SSS";
    public static final String FORMAT_2 = "MM/dd/yyyy HH:mm:ss";
    public static final String FORMAT_3 = "HH:mm:ss";
    public static final String FORMAT_4 = "HH:mm:ss.SSS";
    public static final String FORMAT_5 = "ss";
    public static final String FORMAT_6 = "mm:ss.SSS";
    
    public static final long NANO_SECONDS_PER_SECOND = 1000000000;
    public static final long NANO_SECONDS_PER_MILLISECOND = 1000000;
    public static final long NANO_SECONDS_PER_MINUTE = 60000000000L;
    public static final long NANO_SECONDS_PER_HOUR = 3600000000000L;
    
    private HashMap timers;
    
    private long timeDeduction;
    
    public TimerCollection(final long timeDeduction)
    {
        this.timeDeduction = timeDeduction;
        timers = new HashMap();
    }
    
    /**
     * Updates all timers
     */
    public void update()
    {
        Object[] keys = timers.keySet().toArray();
        
        for (Object key : keys)
        {
            update(key);
        }
    }
    
    /**
     * Update a specific timer
     * @param key The identifier for this Timer
     */
    public void update(Object key)
    {
        update(key, getTimeDeduction());
    }
    
    /**
     * Update a specific timer with a custom deduction amount
     * @param key The identifier for this Timer
     * @param timeDeduction The custom deduction amount
     */
    public void update(Object key, long timeDeduction)
    {
        getTimer(key).update(timeDeduction);
    }
    
    /**
     * Add Timer with no time limit
     * @param key The identifier for this Timer
     */
    public void add(Object key)
    {
        add(key, 0);
    }
    
    /**
     * Add Timer with specific time limit
     * @param key The identifier for this Timer
     * @param reset The time limit (usually is milliseconds or nanoseconds)
     */
    public void add(Object key, long reset)
    {   //add timer to collection
        timers.put(key, new Timer(reset));
    }
    
    /**
     * Pauses/Un-Pauses all timers in the collection
     * 
     * @param pause Do we pause all timers
     */
    public void setPause(boolean pause)
    {
        Object[] keys = timers.keySet().toArray();
        
        for (Object key : keys)
        {
            getTimer(key).setPause(pause);
        }
    }
    
    /**
     * Pauses/Un-Pauses a specific timer
     * @param key The identifier for this Timer
     * @param pause Do we pause the specific timer
     */
    public void setPause(Object key, boolean pause)
    {   //set pause for specific timer
        getTimer(key).setPause(pause);
    }
    
    public void setRemaining(Object key, long remaining)
    {   //set time remaining for specific timer
        getTimer(key).setRemaining(remaining);
    }
    
    public float getProgress(Object key)
    {
        return getTimer(key).getProgress();
    }
    
    /**
     * Gets the Timer at the specific key
     * @param key The identifier for this Timer
     * @return Timer The timer
     */
    public Timer getTimer(Object key)
    {
        return (Timer)timers.get(key);
    }
    
    /**
     * Resets the remaining time for all of the timers
     */
    public void resetRemaining()
    {
        Object[] keys = timers.keySet().toArray();
        
        for (Object key : keys)
        {
            resetRemaining(key);
        }
    }

    /**
     * Reset the remaining time for the specific timer
     * @param key Unique key to identify the timer we want to reset
     */
    public void resetRemaining(Object key)
    {
        getTimer(key).resetRemaining();
    }
    
    public void setReset(Object key, long reset)
    {
        getTimer(key).setReset(reset);
    }
    
    public boolean hasTimePassed(Object key)
    {
        return getTimer(key).hasTimePassed();
    }
    
    public String getDescPassed(Object key, String format)
    {   //gets a description of the time passed for a specific timer and specific format
        return getTimer(key).getDescPassed(format);
    }
    
    public String getDescRemaining(Object key, String format)
    {   //gets a description of the time remaining for a specific timer and specific format
        return getTimer(key).getDescRemaining(format);
    }
    
    public long getTimeDeduction()
    {
        return this.timeDeduction;
    }
    
    public static long toNanoSeconds(long milliseconds)
    {
        return (milliseconds * TimerCollection.NANO_SECONDS_PER_MILLISECOND);
    }
    
    public static long toNanoSeconds(int minutes)
    {
        return (minutes * TimerCollection.NANO_SECONDS_PER_MINUTE);
    }
}