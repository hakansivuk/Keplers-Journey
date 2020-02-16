package com.example.hakan.keplersjourney;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

/**
 * MainMenu Class
 * @author Yusuf Nevzat Sengun , Hakan Sivuk, Eray Ünsal Atay
 * @version 12.05.2018
 */

public class MainMenu extends AppCompatActivity
{

    //properties
    private Sound  sound;
    private Intent intent;

    //methods
    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );

        if( !Sound.getInstance().isStarted() )
        {
            sound                         = Sound.getInstance();
            ArrayList<MediaPlayer> sounds = new ArrayList<>();

            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.a624 ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.interstellar ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.gameover ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.a753 ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.inthehouse ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.synthgod ) );
            sounds.add( MediaPlayer.create( getApplicationContext() , R.raw.timetowakeup ) );
            sound.setData( sounds );
            sound.start();
        }

        else
        {
            if( Sound.getInstance().getNumber() != 0 )
            {
                Sound.getInstance().changeMusic( 0 );
            }
            if( Sound.getInstance().isPlaying() )
            {
                Sound.getInstance().start();
            }
        }

    }

    /**
     * A method to move to the new journey screen
     * @param view
     */
    public void newGame( View view )
    {
        intent = new Intent ( getApplicationContext() , NickNameActivity.class );
        startActivity( intent );
    }

    /**
     * A method to move to the load game screen
     * @param view
     */
    public void loadGame( View view )
    {
        intent = new Intent ( getApplicationContext() , LoadGame.class );
        startActivity( intent );
    }

    /**
     * A method to move to the high scores screen
     * @param view
     */
    public void highScores( View view )
    {
        intent = new Intent ( getApplicationContext() , HighScores.class );
        startActivity( intent );
    }

    /**
     * A method to move to the settings screen
     * @param view
     */
    public void settings( View view )
    {
        intent = new Intent ( getApplicationContext() , Settings.class );
        startActivity( intent );
    }

    /**
     * A method to move to the credits screen
     * @param view
     */
    public void credits( View view )
    {
        intent = new Intent ( getApplicationContext() , Credits.class );
        startActivity( intent );
    }

    /**
     * A method to move to the exit screen
     * @param view
     */
    public void exit( View view )
    {
        finish();
        System.exit( 0 );
    }
}