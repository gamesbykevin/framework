package com.gamesbykevin.framework.resources;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import java.applet.AudioClip;
import javax.swing.JApplet;

import javazoom.jl.player.Player;

public final class Audio implements Runnable, Disposable
{
    //audio clip for wav/other format audio
    private AudioClip ac;
    
    //player is to play mp3 files
    private Player player;
    
    //object will assist playing midi
    private Sequence sequence;
    
    //object will assist playing midi
    private Sequencer sequencer;
    
    //file path-name of the audio
    private String relativePath;
    
    //does the audio loop
    private boolean loop = false;
    
    /**
     * What type of audio selections are there
     */
    private enum Type
    {
        MP3, WAV, MID, OTHER
    }
    
    //what type of file is this audio resource
    private final Type type;
    
    //class located in the same directory as main resources folder
    private Class source;
    
    //thread that we will play sounds on (maybe only for mp3 in future)
    private Thread thread;
    
    public Audio(Class source, String relativePath) throws Exception
    {
        //store the file name in case we need it later
        this.relativePath = relativePath;

        //assign the file type so we know how to load/play audio
        this.type = getType();
        
        //store class since it lies in same directory as resources 
        this.source = source;
        
        //create basic objects depending on audio type
        setup();
    }
    
    /**
     * Get the file type based on the file name extension.
     * 
     * @return Type the file type
     */
    private Type getType() throws Exception
    {
        //we already have the type so return it
        if (this.type != null)
            return this.type;
        
        //if the realative path is not set throw exception
        if (relativePath == null)
            throw new Exception("The relative file path must be set first before getType() is called");
        
        //extract the file extension from the file name
        String extension = relativePath.substring(relativePath.lastIndexOf(".") + 1).trim();
        
        Type tmpType = Type.OTHER;

        if (extension.toLowerCase().equals("mp3"))
            tmpType = Type.MP3;

        if (extension.toLowerCase().equals("wav"))
            tmpType = Type.WAV;

        if (extension.toLowerCase().equals("mid"))
            tmpType = Type.MID;
        
        return tmpType;
    }
    
    /**
     * Stop sound and free up resources
     */
    @Override
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

        if (source != null)
            source = null;
        
        if (thread != null)
            thread = null;
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
        thread = new Thread(this);
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
            //make sure necessary objects are created to play audio
            setup();
            
            switch(getType())
            {
                case MID:
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
                        ac.loop();
                    }
                    else
                    {
                        ac.play();
                    }
                    break;     

                case MP3:
                    //are we looping sound
                    if (loop)
                    {
                        while(true)
                        {
                            //does the player exist
                            if (player != null)
                            {
                                //the thread will pause here as the framework plays the mp3 source
                                player.play();
                            }
                            
                            //does the player exist and has the audio finished playing
                            if (player != null && player.isComplete())
                            {
                                //create new instance so mp3 source can restart and the loop will continue
                                player = new Player(source.getResourceAsStream(relativePath));
                            }
                            
                            //the player does not exist so exit infinite loop
                            if (player == null)
                                break;
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
     * Creates the necessary objects depending on the audio type
     */
    private void setup() throws Exception
    {
        switch(this.type)
        {
            case MID:
                
                //if the sequence or sequencer do not exist
                if (sequence == null || sequencer == null)
                {
                    //get the midi sequence from the stream
                    sequence = MidiSystem.getSequence(source.getResource(relativePath));

                    //create a sequencer for the sequence
                    sequencer = MidiSystem.getSequencer();

                    //open so the sequencer will begin to acquire the system resources needed to function
                    sequencer.open();

                    //let the sequencer know where the midi file data is located
                    sequencer.setSequence(sequence); 
                }
                else
                {
                    //reset sequencer back to the beginning
                    sequencer.setTickPosition(0);
                }
                break;
                
            case WAV:
            case OTHER:
                if (this.ac == null)
                {
                    this.ac = JApplet.newAudioClip(source.getResource(relativePath));
                }
                break;
                
            case MP3:
                if (player == null || player.isComplete())
                {
                    //create a new player with the assigned file name
                    player = new Player(source.getResourceAsStream(relativePath));
                }
                break;
        }
    }
    
    /**
     * Stop playing the sound. The method also needs to be called stopSound()
     * because there is an existing stop() method in the Thread class.
     */
    public void stopSound()
    {
        try
        {
            //if thread exists and is running interrupt it
            if (thread != null && thread.isAlive())
                thread.interrupt();
            
            switch (getType())
            {
                case MID:
                    
                    if (sequencer != null)
                    {
                        if (sequencer.isOpen())
                            sequencer.stop();
                    }
                    
                    sequencer = null;
                    sequence = null;
                    break;

                case WAV:
                case OTHER:

                    if (ac != null)
                    {
                        ac.stop();
                    }
                    break;

                case MP3:
                    
                    if (player != null)
                    {
                        player.close();
                        player = null;
                    }
                    break;
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}