package com.example.hakan.keplersjourney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Credits Class
 * @author Hakan Sivuk
 * @version 12.05.2018
 */

public class Credits extends AppCompatActivity
{

    //methods
    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_credits );
    }

    /**
     * A method to return to the main menu
     * @param view
     */
    public void mainMenu( View view )
    {
        Intent intent;

        intent = new Intent( getApplicationContext() , MainMenu.class );
        startActivity( intent );
    }
}
