package com.example.hakan.keplersjourney;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

/**
 * NickNameActivity Class
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */

public class NickNameActivity extends AppCompatActivity {

    //properties
    EditText nickName;
    AppDataBase database;
    String name;

    //methods

    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        nickName = (EditText)findViewById(R.id.nickName);
        database = AppDataBase.getInstance( this );
    }

    /**
     * Deletes the nickname by setting it to an empty String
     * @param view
     */
    public void delete(View view){
        nickName.setText("");
    }

    /**
     * A method to return to the main menu
     * @param view
     */
    public void mainMenu(View view){
        Intent intent = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(intent);
    }

    /**
     * Initializes the journey
     * @param view
     */
    public void startGame(View view){
        name = "" + nickName.getText();

        if( database.userDao().isExist( name ) != null ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            //random string genarator, creates only number for this version
            Random generator = new Random();
            StringBuilder randomStringBuilder = new StringBuilder();
            int randomLength;
            char tempChar;
            do {
                randomLength = generator.nextInt(3);
                for (int i = 0; i < randomLength; i++) {
                    tempChar = (char) (generator.nextInt(10) + '0');
                    randomStringBuilder.append(tempChar);
                }
            } while( database.userDao().isExist(  name + randomStringBuilder.toString() ) != null );
            name = name + randomStringBuilder.toString();

            alertDialog.setMessage("This nickname is already taken\nPlease try : " + name );
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    nickName.setText( name );
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("score" , 0.0);
            intent.putExtra("level" , 0);
            startActivity(intent);
        }
    }
}