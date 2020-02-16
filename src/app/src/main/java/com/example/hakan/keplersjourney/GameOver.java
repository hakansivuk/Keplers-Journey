package com.example.hakan.keplersjourney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * GameOver Class
 * @author Muhammed Yusuf Toy
 * @version 12.05.2018
 */

public class GameOver extends AppCompatActivity
{

    //properties
    private Intent   intent;
    private double   score;
    private String   name;
    private TextView result;

    //methods
    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_over );

        intent = getIntent();
        name   = intent.getStringExtra( "name" );
        score  = intent.getDoubleExtra( "score" , 0 );
        result = ( TextView )findViewById( R.id.result );
        result.setText( name + "\n" + score );
    }

    /**
     * Saves the current state of the game
     * @param view
     */
    public void save( View view )
    {
        Intent intentSave;

        intentSave = new Intent( getApplicationContext() , HighScores.class );
        startActivity( intentSave );
    }
}
