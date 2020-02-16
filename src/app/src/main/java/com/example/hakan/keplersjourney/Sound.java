package com.example.hakan.keplersjourney;

import android.media.MediaPlayer;
import java.util.ArrayList;

/**
 * Sound Class
 * @author Hakan Sivuk
 * @version 12.05.2018
 */

public class Sound
{

    //properties
    private static Sound           instance;
    private ArrayList<MediaPlayer> sounds;
    private MediaPlayer            sound;
    private boolean                playing;
    private boolean                isStarted;
    private int                    number;

    //constructor
    public Sound()
    {
        sounds    = new ArrayList<>();
        playing   = false;
        isStarted = false;
        number    = 0;
    }

    //methods
    /**
     * Gets the sound
     * @return sound
     */
    public MediaPlayer getData()
    {
        return sound;
    }

    /**
     * Checks whether the sound is started or not
     * @return isStarted
     */
    public boolean isStarted()
    {
        return isStarted;
    }

    /**
     * Sets the sounds and puts them into a loop
     * @param sounds MediaPlayer ArrayList
     */
    public void setData( ArrayList<MediaPlayer> sounds )
    {
        this.sounds = sounds;
        for( int i = 0 ; i < sounds.size() ; i++ )
        {
            sounds.get( i ).setLooping( true );
        }
        sound = this.sounds.get( 0 );
    }

    /**
     * Checks whether the sound is playing or not
     * @return playing
     */
    public boolean isPlaying()
    {
        return playing;
    }

    /**
     * A static method to get a sound instance
     * @return instance
     */
    public static Sound getInstance()
    {
        if( instance == null )
        {
            instance = new Sound();
        }
        return instance;
    }

    /**
     * Starts the sound
     */
    public void start()
    {
        sound.start();
        playing   = true;
        isStarted = true;
    }

    /**
     * Pauses the sound
     */
    public void pause()
    {
        if( playing )
        {
            sound.pause();
            playing = false;
        }
    }

    /**
     * Changes the music
     * @param i
     */
    public void changeMusic( int i )
    {
        number = i;
        if( sound != null )
        {
            if( playing )
            {
                sound.pause();
            }
            sound = null;
            sound = sounds.get(i);
            if( sound.getCurrentPosition() != 0 )
            {
                sound.seekTo( 0 );
            }
        }
    }

    /**
     * Gets the sound number
     * @return number
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * Pauses the sound
     */
    public void finish()
    {
        sound.pause();
    }

}
