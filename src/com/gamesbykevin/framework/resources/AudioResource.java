package com.gamesbykevin.framework.resources;

import java.applet.AudioClip;
import javax.swing.JApplet;

import javazoom.jl.player.Player;

public class AudioResource 
{
    private AudioClip ac;
    
    private Player player;
    
    private String fileName;
    
    public enum Type
    {
        MP3, WAV, MID, OTHER
    }
    
    private Class source;
    
    private Type type;
    
    public AudioResource(Class source, String fileName)
    {
        try
        {
            this.fileName = fileName;

            String fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1).trim();

            this.type = Type.OTHER;
            
            if (fileExtension.toLowerCase().equals("mp3"))
                this.type = Type.MP3;
            
            if (fileExtension.toLowerCase().equals("wav"))
                this.type = Type.WAV;
            
            if (fileExtension.toLowerCase().equals("mid"))
                this.type = Type.MID;
            
            switch(type)
            {
                case MP3:
                    this.source = source;
                    break;

                case MID:
                case WAV:
                case OTHER:
                default:
                    this.ac = JApplet.newAudioClip(source.getResource(fileName));
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void dispose()
    {
        switch(type)
        {
            case MP3:
                if (player != null)
                    player.close();

                player = null;
                break;

            case MID:
            case WAV:
            case OTHER:
            default:

                if (ac != null)
                    ac.stop();
                ac = null;
                break;
        }

        if (source != null)
            source = null;
    }
    
    public void play(final boolean loop)
    {
        switch (type)
        {
            case MID:
            case WAV:
            case OTHER:
            default:
                new Thread() {
                    public void run()
                    {
                        try
                        {
                            if (loop)
                                ac.loop();
                            else
                                ac.play();
                        }
                        catch (Exception ex)
                        {
                            if (ac != null)
                                ac.stop();
                        }
                    }
                }.start();
                break;

            case MP3:
                new Thread() {
                    public void run()
                    {
                        try
                        {
                            player = new Player(source.getResourceAsStream(fileName));
                            
                            if (loop)
                            {
                                while(true)
                                {
                                    player.play();
                                    
                                    if (player.isComplete())
                                        player = new Player(source.getResourceAsStream(fileName));
                                }
                            }
                            else
                            {
                                player.play(); 
                            }

                            Thread.sleep(5);
                        }
                        catch(Exception ex)
                        {
                            System.out.println(ex);
                        }
                    }
                }.start();
                break;
        }
    }
    
    public void play()
    {
        play(false);
    }
    
    public void stop()
    {
        try
        {
            if (player != null)
                player.close();
            
            player = null;
            
            if (ac != null)
                ac.stop();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
    
    public Type getType()
    {
        return this.type;
    }
}