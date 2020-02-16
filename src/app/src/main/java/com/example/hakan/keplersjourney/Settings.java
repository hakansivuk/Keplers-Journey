package com.example.hakan.keplersjourney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Settşngs Class
 * @author Yusuf Nevzat Sengun , Mehmet Tolga Tomris
 * @version 12.05.2018
 */
public class Settings extends AppCompatActivity {

    //properties
    Sound sound;
    ImageView soundOn;
    ImageView soundOff;
    AppDataBase database;

    //methods

    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        soundOn = (ImageView) findViewById(R.id.soundOn);
        soundOff = (ImageView) findViewById(R.id.soundOff);

        database = AppDataBase.getInstance( this );

        if( Sound.getInstance().isPlaying() )
        {
            soundOff.setVisibility(View.INVISIBLE);
        }
        else
        {
            soundOff.setVisibility(View.VISIBLE);
        }
    }

    /**
     * A method to return to the main menu
     * @param view
     */
    public void mainMenu(View view){
        Intent intent;
        intent = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(intent);
    }

    /**
     * A method used in toggling the sound on/off
     * @param view
     */
    public void sound(View view){
        if( Sound.getInstance().isPlaying() )
        {
            soundOff.setVisibility(View.VISIBLE);
            Sound.getInstance().pause();
        }
        else
        {
            soundOff.setVisibility(View.INVISIBLE);
            Sound.getInstance().start();
        }
    }

    /**
     * Uses the DeleteAll class to clear all of the current data and displays an alert
     * @param view
     */
    public void clearData( View view ){
        new DeleteAll().execute();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("All saved games and high scores are deleted");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //inner class
    private class DeleteAll extends AsyncTask< Void , Void , Void > {

        /**
         * Clears all of the current data of the game
         * @param params
         * @return null
         */
        @Override
        protected Void doInBackground( Void... params ){
            database.userDao().deleteAll();
            return null;
        }
    }
}
