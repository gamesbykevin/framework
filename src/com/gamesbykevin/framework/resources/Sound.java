package com.gamesbykevin.framework.resources;

public interface Sound 
{
    /**
     * Set the audio enabled or disabled
     * @param enabled true sound will play, false otherwise
     */
    public void setEnabled(final boolean enabled);
    
    /**
     * Is the audio enabled
     * @return true if so, false otherwise
     */
    public boolean isEnabled();
}
