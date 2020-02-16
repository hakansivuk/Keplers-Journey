package com.example.hakan.keplersjourney;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * LoadGame Class
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */

public class LoadGame extends AppCompatActivity {

    private ArrayList< String > list;
    private ArrayList< User > users;
    //private User user;
    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private AppDataBase database;

    private class PrepareLoaded extends AsyncTask< Void , Void , ArrayList<User> > {
        @Override
        protected ArrayList<User> doInBackground( Void... params ){
            return (ArrayList<User>)database.userDao().getOnGame();
        }

        @Override
        protected void onPostExecute( ArrayList<User> result) {
            super.onPostExecute(result);
            for( int i = 0 ; i < result.size() ; i++ ){
                list.add( result.get( i ).getName() );

            }
            arrayAdapter = new ArrayAdapter( getApplicationContext() , android.R.layout.simple_list_item_1 , list );
            listView.setAdapter( arrayAdapter );
            users = result;
        }
    }

    public class SetClicked extends AsyncTask< Integer , Void , User >{
        @Override
        protected User doInBackground( Integer... params){
            return database.userDao().getUser( users.get( params[0] ).getName() );
            //database.userDao().deleteUser( sr );
        }

        @Override
        protected void onPostExecute( User result) {
            Intent intent = new Intent( getApplicationContext() , GameActivity.class );

            intent.putExtra("name" , result.getName());
            intent.putExtra("score" , result.getScore() );
            intent.putExtra("level" , result.getLevel() );
            intent.putExtra("isLoaded" , 1 );

            startActivity(intent);
        }
    }

    public void mainMenu(View view){
        Intent intent;
        intent = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        database = AppDataBase.getInstance( this );

        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<String>();
        new PrepareLoaded().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new SetClicked().execute( position );
            }
        });
    }
}
