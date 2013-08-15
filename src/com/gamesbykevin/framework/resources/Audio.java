package com.gamesbykevin.framework.resources;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import java.applet.AudioClip;
import javax.swing.JApplet;

import javazoom.jl.player.Player;

public class Audio implements Runnable
{
    //audio clip for wav/other format audio
    private AudioClip ac;
    
    //player is to play mp3 files
    private Player player;
    
    //object will assist playing midi
    private Sequence sequence;
    
    //object will assist playing midi
    private Sequencer sequencer;
    
    //file name of the audio
    private String fileName;
    
    //does the audio loop
    private boolean loop = false;
    
    private enum Type
    {
        MP3, WAV, MID, OTHER
    }
    
    //what type of file is this audio resource
    private final Type type;
    
    //class located in the same directory as main resources folder
    private Class source;
    
    public Audio(Class source, String fileName) throws Exception
    {
        //store the file name in case we need it later
        this.fileName = fileName;

        //assign the file type so we know how to load/play audio
        this.type = getType();
        
        try
        {
            switch(type)
            {
                case MP3:
                    this.source = source;
                    break;

                case MID:
                    sequence = MidiSystem.getSequence(source.getResource(fileName));
                    break;
                    
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
    
    /**
     * Get the file type based on the file name extension.
     * 
     * @return Type the file type
     */
    private Type getType() throws Exception
    {
        //if we already have the type so return it
        if (this.type != null)
            return this.type;
        
        if (fileName == null)
            throw new Exception("File name must be set first before getType() is called");
        
        //extract the file extension from the file name
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).trim();
        
        Type type = Type.OTHER;

        if (extension.toLowerCase().equals("mp3"))
            type = Type.MP3;

        if (extension.toLowerCase().equals("wav"))
            type = Type.WAV;

        if (extension.toLowerCase().equals("mid"))
            type = Type.MID;
        
        return type;
    }
    
    /**
     * Stop sound and free up resources
     */
    public void dispose()
    {
        //attempt to stop sound 
        stopSound();
        
        switch(type)
        {
            case MP3:
                player = null;
                break;

            case MID:
                sequence = null;
                sequencer = null;
                break;
                
            case WAV:
            case OTHER:
            default:
                ac = null;
                break;
        }

        source = null;
    }
    
    /**
     * Play the audio and loop if true
     * @param loop Do we loop the audio once complete
     */
    public void play(final boolean loop)
    {
        //we are looping this audio
        this.loop = loop;
        
        //every time the sound is to be played, create a new thread
        Thread thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Play the audio
     */
    public void play()
    {
        play(false);
    }
    
    /**
     * Play sound when thread start() is called
     */
    @Override
    public void run()
    {
        try
        {
            switch(getType())
            {
                case MID:
                    
                    //create a sequencer for the sequence
                    sequencer = MidiSystem.getSequencer();

                    //open so the sequencer will begin to acquire the system resources needed to function
                    sequencer.open();

                    //let the sequencer know where the midi file data is located
                    sequencer.setSequence(sequence); 

                    //are we looping sound
                    if (loop)
                    {
                        //user static integer to loop forever
                        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                    }

                    //start playing sound
                    sequencer.start();

                    break;

                case WAV:
                case OTHER:

                    //are we looping sound
                    if (loop)
                    {
                        //start playing sound with infinite loop
                        ac.loop();
                    }
                    else
                    {
                        //start playing sound
                        ac.play();
                    }

                    break;     

                case MP3:
                    
                    //create a new player with the assigned file name
                    player = new Player(source.getResourceAsStream(fileName));

                    //are we looping sound
                    if (loop)
                    {
                        while(true)
                        {
                            //the thread will pause here as the framework plays the mp3 source
                            player.play();

                            //now that the player is complete
                            if (player.isComplete())
                            {
                                //create new instance so mp3 source can restart and the loop will continue
                                player = new Player(source.getResourceAsStream(fileName));
                            }
                        }
                    }
                    else
                    {
                        //the thread will pause here as the framework plays the mp3 source
                        player.play(); 
                    }

                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

            //if there was an error attempt to stop audio
            stopSound();
        }
    }
    
    /**
     * Stop playing the sound. The method 
     * also needs to be called stopSound()
     * because there is an existing stop()
     * method in the Thread class.
     */
    public void stopSound()
    {
        try
        {
            switch (getType())
            {
                case MID:
                    
                    if (sequencer != null)
                    {
                        if (sequencer.isOpen())
                            sequencer.stop();
                    }
                    
                    break;

                case WAV:
                case OTHER:

                    if (ac != null)
                        ac.stop();
                    
                    break;     

                case MP3:
                    
                    if (player != null)
                        player.close();

                    player = null;
                    
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}