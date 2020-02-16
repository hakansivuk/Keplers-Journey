package com.example.hakan.keplersjourney;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * HighScores Class
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */

public class HighScores extends AppCompatActivity
{

    //properties
    private ListView listView1;
    private ListView listView2;
    private ArrayList< String > list1;
    private ArrayList< String > list2;
    private ArrayAdapter arrayAdapter1;
    private ArrayAdapter arrayAdapter2;
    AppDataBase database;

    //inner class
    private class PrepareHighScores extends AsyncTask< Void , Void , ArrayList<User> > {
        @Override
        protected ArrayList<User> doInBackground( Void... params ){
            return (ArrayList<User>)database.userDao().getCompleted();
        }

        @Override
        protected void onPostExecute( ArrayList<User> result) {
            super.onPostExecute(result);
            for( int i = 0 ; i < result.size() ; i++ ){
                list1.add( result.get( i ).getName() );
                list2.add( "" + (int)result.get( i ).getScore() );
            }

            arrayAdapter1 = new ArrayAdapter(getApplicationContext() , android.R.layout.simple_list_item_1 , list1 );
            listView1.setAdapter( arrayAdapter1 );
            arrayAdapter2 = new ArrayAdapter(getApplicationContext() , android.R.layout.simple_list_item_1 , list2 );
            listView2.setAdapter( arrayAdapter2 );
        }
    }

    //methods

    /**
     * Executes the initial necessary tasks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        database = AppDataBase.getInstance( this );

        listView1 = (ListView) findViewById( R.id.nameList );
        list1 = new ArrayList<String>();
        listView2 = (ListView) findViewById( R.id.scoreList );
        list2 = new ArrayList<String>();
        new PrepareHighScores().execute();
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

}
