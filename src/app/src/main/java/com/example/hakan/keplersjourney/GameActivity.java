package com.example.hakan.keplersjourney;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * GameActivity Class
 * @authors Gravity Guys ( collective work )
 * @version 12.05.2018
 */

public class GameActivity extends AppCompatActivity implements Observer
{

    //variables
    Observable                    game;
    ArrayList<ArrayList<Integer>> fuels;
    ArrayList<ArrayList<Integer>> images;
    ArrayList<Integer>            imageSpaceCraft;
    ArrayList<Integer>            questionBoxes;
    ArrayList<Integer>            layouts;
    ArrayList<Integer>            fuelBars;
    ArrayList<Integer>            timeTextViews;
    ArrayList<Integer>            arrows;
    ArrayList<Integer>            gifs;
    ArrayList<ArrayList<Integer>> flames;
    AlertDialog.Builder           alert;
    Intent                        intent;
    AppDataBase                   database;
    ArrayList<ArrayList<Integer>> buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new AlertDialog.Builder(this);
        database = AppDataBase.getInstance( this );

        intent = getIntent();

        if( intent.getIntExtra("isLoaded" , 0 ) == 1 ){
            Level lev = database.userDao().getUser( intent.getStringExtra("name") ).getCurrentLevel();
            game = new Game( intent.getIntExtra("level", 0), intent.getDoubleExtra("score", 0.0 ), intent.getStringExtra("name") , lev );
        }
        else {
            game = new Game(intent.getIntExtra("level", 0), intent.getDoubleExtra("score", 0.0 ), intent.getStringExtra("name") );
        }
        game.addObserver( this );

        changeMusic();
        createLayouts();
        setLayout();
        createFlames();
        createButtons();
        setButtonListeners();
        createPlanets();
        createSpaceCrafts();
        createFuelBar();
        createTimeTextViews();
        createArrows();
        createGifs();
        createFuel();
        createQuestionBoxes();
        startGame();
    }

    /**
     * Updates the GUI of the game periodically
     * @param o is the observable object tı be observed
     * @param arg
     */
    @Override
    public void update( Observable o , Object arg )
    {
        if( !( ( Game ) game ).isLevelOver() )
        {
            if( !( ( Game ) game ).isPaused() )
            {
                updateProgressBar();
                updatePlanets();
                updateQuestionBoxLocations();
                updateFuelLocations();
                updateSpaceCraft();
                updateArrow();
                updateTime();
                if( ((Game)game).getTime() % 100 == 0 )
                {
                    new InsertUser().execute( new User( ((Game)game).getName() , ((Game)game).getScore() , ((Game)game).getLevelNumber() , 1 , ((Game)game).getCurrentLevel() ) );
                }
            }
            else
            {
                setQuestion();
            }
        }
        else
        {
            Sound.getInstance().finish();
            if( ( ( Game)game ).isLevelCompleted() )
            {
                if( !( ( Game ) game ).isGameOver() )
                {
                    //Level is completed but ((Game)game) is not over
                    alert.setTitle( "Level has been completed!" );
                    alert.setCancelable( false );
                    alert.setMessage( "Start next level?" );
                    alert.setPositiveButton( "Yes" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface , int i ) {
                            ( ( Game ) game ).nextLevel();
                            newLevel();
                        }
                    });
                    alert.show();
                }

                else
                {
                    // game  is completed
                    Sound.getInstance().changeMusic(  ( ( Game ) game ).getLevelNumber() + 2 );
                    if( Sound.getInstance().isPlaying() )
                        Sound.getInstance().start();

                    alert.setTitle( "YOU HAVE WON THE GAME!" );
                    alert.setCancelable( false );
                    alert.setMessage( "GAME HAS BEEN COMPLETED!" );
                    alert.setPositiveButton( "CONTINUE" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface , int i ) {
                            Intent intentGameOver = new Intent( getApplicationContext() , GameOver.class );
                            intentGameOver.putExtra( "name" , ( ( Game ) game ).getName() );
                            intentGameOver.putExtra( "score" , ( ( Game ) game ).getScore());

                            new InsertUser().execute( new User( ( ( Game ) game ).getName() , ( ( Game ) game ).getScore() , ( ( Game ) game ).getLevelNumber() , 0 , ( ( Game ) game ).getCurrentLevel() )  );

                            startActivity(intentGameOver);
                        }
                    });

                    alert.show();
                }
            }

            else
            {
                //level could not be completed
                LevelData levelData;
                levelData = new LevelData();
                new InsertUser().execute( new User( ((Game)game).getName() , ((Game)game).getScore() , ((Game)game).getLevelNumber() , 1 , levelData.getLevels().get( ((Game)game).getLevelNumber() ) ) );
                alert.setTitle( "YOU HAVE LOST" );
                alert.setMessage( "LEVEL COULD NOT BE COMPLETED" );
                alert.setCancelable( false );
                alert.setPositiveButton( "RESTART" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialogInterface , int i ) {
                        ( ( Game ) game ).restartLevel();
                        ( ( Game ) game ).setIsPaused( false );
                        ( ( Game ) game ).start();
                        if( Sound.getInstance().isPlaying() )
                            Sound.getInstance().start();
                        findViewById( imageSpaceCraft.get( ( ( Game ) game ).getLevelNumber() ) ).setVisibility( View.VISIBLE );
                        updateFuelVisibility();
                        updateQuestionVisibility();
                        updateProgressBar();
                    }
                });

                alert.setNegativeButton("MAIN MENU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentMainMenu = new Intent(getApplicationContext(),MainMenu.class);
                        startActivity(intentMainMenu);
                    }
                });
                new CountDownTimer(500 , 500 ){
                    public void onTick( long millisUntilFinished ){
                    }

                    public void onFinish(){
                        findViewById(gifs.get(((Game)game).getLevelNumber())).setVisibility(View.INVISIBLE);
                        alert.show();
                    }
                }.start();
                findViewById( gifs.get( ( ( Game )game ).getLevelNumber() ) ).setX( ( float ) ( ( Game )game ).getSpaceCraft().getX() - 80 );
                findViewById( gifs.get( ( ( Game )game ).getLevelNumber() ) ).setY( ( float ) ( ( Game )game ).getSpaceCraft().getY() - 80 );
                findViewById( gifs.get( ( ( Game )game ).getLevelNumber() ) ).setVisibility( View.VISIBLE );
                findViewById( imageSpaceCraft.get( ( ( Game ) game ).getLevelNumber() ) ).setVisibility( View.INVISIBLE );
            }
        }
    }

    public void updatePlanets()
    {
        ArrayList<Planet> planets;

        planets = ( ( Game ) game ).getPlanets();
        for( int i = 0 ; i < planets.size() ; i++ )
        {
            //updating planet's locations
            findViewById( images.get( ( ( Game ) game ).getLevelNumber() ).get( i ) ).setX( ( float ) planets.get( i ).getX() );
            findViewById( images.get( ( ( Game ) game ).getLevelNumber() ).get( i ) ).setY( ( float ) planets.get( i ).getY() );
        }
    }

    public void updateSpaceCraft()
    {
        findViewById( imageSpaceCraft.get( ( ( Game )game ).getLevelNumber() ) ).setX( ( float ) ( ( Game ) game ).getSpaceCraft().getX() );
        findViewById( imageSpaceCraft.get( ( ( Game )game ).getLevelNumber() ) ).setY( ( float ) ( ( Game ) game ).getSpaceCraft().getY() );
    }

    public void updateArrow()
    {
        findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).setX( ( float ) ( ( (Game ) game).getSpaceCraft().getX() + 50 + 150 * Math.cos(Math.toRadians( ( ( Game ) game ).getTarget() - 90 ) ) ) );
        findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).setY( ( float ) ( ( (Game ) game).getSpaceCraft().getY() + 40 + 150 * Math.sin(Math.toRadians( ( ( Game ) game ).getTarget() - 90 ) ) ) );
        findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).setRotation( ( float ) ( ( (Game ) game ).getTarget() - 90 ));

        if ( ( ( Game ) game ).getSpaceCraft().isInSight( ( ( Game ) game ).getPlanets().get( ( ( Game ) game ).getPlanets().size() - 1 ) ) )
        {
            findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).setVisibility( View.INVISIBLE );
        }
        else
        {
            if( findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).getVisibility() == View.INVISIBLE )
                findViewById( arrows.get( ( ( Game ) game ).getLevelNumber() ) ).setVisibility( View.VISIBLE );
        }
    }

    public void updateTime()
    {
        //updating time
        ( ( TextView ) findViewById( timeTextViews.get( ( ( Game ) game ).getLevelNumber() ) ) ).setText( "" + ( ( Game ) game ).getTime() );
    }

    private class InsertUser extends AsyncTask< User , Void , Void >
    {
        @Override
        protected Void doInBackground( User... params )
        {

            if( database.userDao().isExist( params[ 0 ].getName() ) != null )
            {
                database.userDao().updateUser( params[ 0 ] );
            }
            else
            {
                database.userDao().insertUser( params[ 0 ] );
            }
            return null;
        }
    }

    //touch listener
    class MyTouchListener implements View.OnTouchListener
    {
        private int direction;
        public  MyTouchListener( int direction )
        {
            this.direction = direction;
        }

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run()
            {
                if( !( ( Game ) game ).isFuelConsumed() && !((Game)game).isPaused() )
                {
                    ( ( Game ) game ).updateSpaceCraft( direction );
                   // updateProgressBar();
                    handler.postDelayed( this , 100 );
                }
                else
                {
                    handler.removeCallbacks( runnable );
                    try{
                        findViewById( flames.get( ( ( Game ) game ).getLevelNumber() ).get( direction % 4 ) ).setVisibility( View.INVISIBLE );
                    }catch ( NullPointerException e){


                    }
                }
            }
        };

        @Override
        public boolean onTouch( View view , MotionEvent motionEvent )
        {
            if( motionEvent.getAction() == MotionEvent.ACTION_UP )
            {
                findViewById( flames.get( ( ( Game ) game).getLevelNumber() ).get( direction % 4 ) ).setVisibility( View.INVISIBLE );
                handler.removeCallbacks( runnable );
                return true;
            }

            else if( motionEvent.getAction() == MotionEvent.ACTION_DOWN )
            {
                if( !( ( Game ) game ).isFuelConsumed() )
                {
                    findViewById( flames.get( ( ( Game ) game ).getLevelNumber() ).get( direction % 4 ) ).setVisibility( View.VISIBLE );
                    handler.postDelayed( runnable , 0 );
                }
                return true;
            }
            return false;
        }
    }

    public void pause(View view)
    {
        ( ( Game ) game ).stop();
        setContentView(R.layout.activity_pause);
        if( Sound.getInstance().isPlaying() )
        {
            findViewById( R.id.soundOffp ).setVisibility( View.INVISIBLE );
        }
        else
        {
            findViewById( R.id.soundOffp ).setVisibility( View.VISIBLE );
        }
    }

    public void mainMenu(View view)
    {
        AlertDialog.Builder alertM = new AlertDialog.Builder(this );

        new InsertUser().execute( new User( ( (Game ) game ).getName() , ( ( Game ) game ).getScore() , ( ( Game ) game ).getLevelNumber() , 1 , ( ( Game ) game ).getCurrentLevel() ) );

        alertM.setMessage("Your progress has been saved!");
        alertM.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentMainMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intentMainMenu);
            }
        });

        alertM.show();

    }

    //pause sayfasindaki oyuna devam et butonu
    public void resumeGame( View view ){
        resumeGame();
    }

    public void resumeGame(){
        setLayout();
        //((ProgressBar)findViewById(fuelBars.get(((Game)game).getLevelNumber()))).setProgress(((Game)game).getSpaceCraft().getRemainingFuel());
        updateProgressBar();
        ((Game)game).start();
        setButtonListeners();
        setFlamesLocations();
        updateFuelVisibility();
        updateQuestionVisibility();
        findViewById(gifs.get(((Game)game).getLevelNumber())).setVisibility(View.INVISIBLE);
    }

    //pause sayfasindaki ses butonu
    public void sound(View view)
    {
        if( Sound.getInstance().isPlaying() )
        {
            findViewById(R.id.soundOffp).setVisibility(View.VISIBLE);
            Sound.getInstance().pause();
        }
        else
        {
            findViewById(R.id.soundOffp).setVisibility(View.INVISIBLE);
            Sound.getInstance().start();
        }
    }


    public void createLayouts(){
        layouts = new ArrayList<>();
        layouts.add( R.layout.activity_level1 );
        layouts.add( R.layout.activity_level2 );
        layouts.add( R.layout.activity_level3 );
        layouts.add( R.layout.activity_level4 );
        layouts.add( R.layout.activity_level5);
    }

    public void startGame(){
        ((Game)game).start();
    }

    public void createSpaceCrafts(){
        imageSpaceCraft = new ArrayList<>();
        imageSpaceCraft.add( R.id.spaceCraft_1);
        imageSpaceCraft.add( R.id.spaceCraft_2);
        imageSpaceCraft.add( R.id.spaceCraft_3);
        imageSpaceCraft.add( R.id.spaceCraft_4);
        imageSpaceCraft.add ( R.id.spaceCraft_5);
    }

    public void createPlanets(){
        images = new ArrayList<>();
        ArrayList<Integer> images1 = new ArrayList<>();
        images1.add( R.id.planet1_1 );
        images1.add( R.id.planet2_1 );
        ArrayList<Integer> images2 = new ArrayList<>();
        images2.add( R.id.planet1_2 );
        images2.add( R.id.planet2_2 );
        images2.add( R.id.planet3_2 );
        images2.add( R.id.planet4_2 );
        ArrayList<Integer> images3 = new ArrayList<>();
        images3.add( R.id.planet1_3 );
        images3.add( R.id.planet2_3 );
        images3.add( R.id.planet3_3 );
        images3.add( R.id.planet4_3 );
        ArrayList<Integer> images4 = new ArrayList<>();
        images4.add( R.id.planet1_4 );
        images4.add( R.id.planet2_4 );
        images4.add( R.id.planet3_4 );
        images4.add( R.id.planet4_4 );
        images4.add( R.id.planet5_4 );
        ArrayList<Integer> images5 = new ArrayList<>();
        images5.add( R.id.planet1_5 );
        images5.add( R.id.planet2_5 );
        images5.add( R.id.planet3_5 );
        images5.add( R.id.planet4_5 );
        images5.add( R.id.planet5_5 );
        images5.add( R.id.planet6_5 );
        images5.add( R.id.planet7_5 );
        images5.add( R.id.planet8_5 );

        images.add(images1);
        images.add(images2);
        images.add(images3);
        images.add(images4);
        images.add(images5);
    }

    public void createFuelBar(){
        fuelBars = new ArrayList<>();
        fuelBars.add( R.id.fuelBar_1 );
        fuelBars.add( R.id.fuelBar_2 );
        fuelBars.add( R.id.fuelBar_3 );
        fuelBars.add( R.id.fuelBar_4 );
        fuelBars.add( R.id.fuelBar_5);
    }

    public void createTimeTextViews(){
        timeTextViews = new ArrayList<>();
        timeTextViews.add( R.id.timeTextView_1 );
        timeTextViews.add( R.id.timeTextView_2 );
        timeTextViews.add( R.id.timeTextView_3 );
        timeTextViews.add( R.id.timeTextView_4 );
        timeTextViews.add( R.id.timeTextView_5 );
    }

    public void createArrows(){
        arrows = new ArrayList<>();
        arrows.add(R.id.arrow_1);
        arrows.add(R.id.arrow_2);
        arrows.add(R.id.arrow_3);
        arrows.add(R.id.arrow_4);
        arrows.add(R.id.arrow_5);
    }

    public void createGifs(){
        gifs = new ArrayList<>();
        gifs.add( R.id.gif_1);
        gifs.add( R.id.gif_2);
        gifs.add( R.id.gif_3);
        gifs.add( R.id.gif_4);
        gifs.add( R.id.gif_5);
        findViewById(gifs.get(((Game)game).getLevelNumber())).setVisibility(View.INVISIBLE);
    }

    public void createButtons(){
        buttons = new ArrayList<>();
        ArrayList<Integer> buttons1 = new ArrayList<>();

        buttons1.add(R.id.dright1);
        buttons1.add(R.id.ddown1);
        buttons1.add(R.id.dleft1);
        buttons1.add(R.id.dup1);

        ArrayList<Integer> buttons2 = new ArrayList<>();

        buttons2.add(R.id.dright2);
        buttons2.add(R.id.ddown2);
        buttons2.add(R.id.dleft2);
        buttons2.add(R.id.dup2);

        ArrayList<Integer> buttons3 = new ArrayList<>();

        buttons3.add(R.id.dright3);
        buttons3.add(R.id.ddown3);
        buttons3.add(R.id.dleft3);
        buttons3.add(R.id.dup3);

        ArrayList<Integer> buttons4 = new ArrayList<>();

        buttons4.add(R.id.dright4);
        buttons4.add(R.id.ddown4);
        buttons4.add(R.id.dleft4);
        buttons4.add(R.id.dup4);

        ArrayList<Integer> buttons5 = new ArrayList<>();

        buttons5.add(R.id.dright5);
        buttons5.add(R.id.ddown5);
        buttons5.add(R.id.dleft5);
        buttons5.add(R.id.dup5);

        buttons.add( buttons1 );
        buttons.add( buttons2 );
        buttons.add( buttons3 );
        buttons.add( buttons4 );
        buttons.add( buttons5 );
    }

    public void createFlames() {
        flames = new ArrayList<>();
        ArrayList<Integer> flame1 = new ArrayList<>();
        flame1.add( R.id.tdown1);
        flame1.add( R.id.tright1);
        flame1.add( R.id.tup1);
        flame1.add( R.id.tleft1);

        ArrayList<Integer> flame2 = new ArrayList<>();
        flame2.add( R.id.tdown2);
        flame2.add( R.id.tright2);
        flame2.add( R.id.tup2);
        flame2.add( R.id.tleft2);

        ArrayList<Integer> flame3= new ArrayList<>();
        flame3.add( R.id.tdown3);
        flame3.add( R.id.tright3);
        flame3.add( R.id.tup3);
        flame3.add( R.id.tleft3);

        ArrayList<Integer> flame4 = new ArrayList<>();
        flame4.add( R.id.tdown4);
        flame4.add( R.id.tright4);
        flame4.add( R.id.tup4);
        flame4.add( R.id.tleft4);

        ArrayList<Integer> flame5 = new ArrayList<>();
        flame5.add( R.id.tdown5);
        flame5.add( R.id.tright5);
        flame5.add( R.id.tup5);
        flame5.add( R.id.tleft5);

        flames.add( flame1 );
        flames.add( flame2 );
        flames.add( flame3 );
        flames.add( flame4 );
        flames.add( flame5 );

        setFlamesLocations();

    }

    public void setFlamesLocations(){
        findViewById(flames.get(((Game)game).getLevelNumber()).get(3)).setX( (float)((Game)game).getSpaceCraft().getX() - 20 );
        findViewById(flames.get(((Game)game).getLevelNumber()).get(3)).setY( (float)((Game)game).getSpaceCraft().getY() + 40 );

        findViewById(flames.get(((Game)game).getLevelNumber()).get(2)).setX( (float)((Game)game).getSpaceCraft().getX() + 40 );
        findViewById(flames.get(((Game)game).getLevelNumber()).get(2)).setY( (float)((Game)game).getSpaceCraft().getY() - 20 );

        findViewById(flames.get(((Game)game).getLevelNumber()).get(1)).setX( (float)((Game)game).getSpaceCraft().getX() + 97 );
        findViewById(flames.get(((Game)game).getLevelNumber()).get(1)).setY( (float)((Game)game).getSpaceCraft().getY() + 40 );

        findViewById(flames.get(((Game)game).getLevelNumber()).get(0)).setX( (float)((Game)game).getSpaceCraft().getX() + 40 );
        findViewById(flames.get(((Game)game).getLevelNumber()).get(0)).setY( (float)((Game)game).getSpaceCraft().getY() + 97 );

        for( int i = 0 ; i < 4 ; i++ )
        {
            findViewById(flames.get(((Game)game).getLevelNumber()).get(i)).setVisibility(View.INVISIBLE);
        }
    }

    public void setButtonListeners(){
        for( int i = 0 ; i < 4 ; i++ )
        {
            findViewById(buttons.get(((Game)game).getLevelNumber()).get(i)).setOnTouchListener(new MyTouchListener( 3 * ( i + 1 )));
        }
    }

    public void setGifLocation(){
        findViewById(gifs.get(((Game)game).getLevelNumber())).setVisibility(View.INVISIBLE);
    }

    public void newLevel(){
        setContentView(layouts.get( ((Game)game).getLevelNumber() ));
        ((Game)game).start();
        changeMusic();
        setButtonListeners();
        setFlamesLocations();
        setGifLocation();
        updateFuelVisibility();
        updateQuestionVisibility();
    }

    public void changeMusic(){
        Sound.getInstance().changeMusic( ((Game)game).getLevelNumber() + 1 );
        if( Sound.getInstance().isPlaying() )
            Sound.getInstance().start();
    }

    public void setLayout(){
        setContentView(layouts.get(((Game)game).getLevelNumber()));
    }

    public void updateProgressBar()
    {
        try{
            ((ProgressBar)findViewById(fuelBars.get(((Game)game).getLevelNumber()))).setProgress(((Game)game).getSpaceCraft().getRemainingFuel() );
        }catch ( NullPointerException e ){
        }
    }

    public void setQuestion(){
        setContentView(R.layout.activity_question);
        ((TextView)(findViewById( R.id.questionText ))).setText(((Game)game).getQuestionBox().getQuestion());
        ((TextView)(findViewById( R.id.choice1 ))).setText(((Game)game).getQuestionBox().getChoice( 0 ));
        ((TextView)(findViewById( R.id.choice2 ))).setText(((Game)game).getQuestionBox().getChoice( 1 ));
        ((TextView)(findViewById( R.id.choice3 ))).setText(((Game)game).getQuestionBox().getChoice( 2 ));
        ((TextView)(findViewById( R.id.choice4 ))).setText(((Game)game).getQuestionBox().getChoice( 3 ));
    }

    public void choice( View view ){
        if( ((TextView)view).getText( ).equals( ((Game)game).getQuestionBox().getAnswer() ) )
        {
            ((Game)game).updateScore( 200 );
            alert.setTitle("RIGHT ANSWER! +200 POINTS ");
            alert.setCancelable(false);
            alert.setMessage("");
            alert.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    resumeGame();
                }
            });

            alert.show();

        }
        else
        {
            alert.setTitle("WRONG ANSWER!");
            alert.setCancelable(false);
            alert.setMessage("");
            alert.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    resumeGame();
                }
            });

            alert.show();
        }
    }

    public void createQuestionBoxes()
    {
        questionBoxes = new ArrayList<>();
        questionBoxes.add( R.id.qb1);
        questionBoxes.add( R.id.qb2);
        questionBoxes.add( R.id.qb3);
        questionBoxes.add( R.id.qb4);
        questionBoxes.add( R.id.qb5);
        updateQuestionVisibility();
    }

    public void updateQuestionVisibility(){
        if( ((Game)game).getQuestionSelected() )
            findViewById(questionBoxes.get(((Game)game).getLevelNumber())).setVisibility(View.INVISIBLE);
    }

    public void createFuel()
    {
        fuels = new ArrayList<>();
        ArrayList<Integer> fuel1 = new ArrayList<>();
        fuel1.add( R.id.fuel1);
        ArrayList<Integer> fuel2 = new ArrayList<>();
        fuel2.add( R.id.fuel2);
        ArrayList<Integer> fuel3 = new ArrayList<>();
        fuel3.add( R.id.fuel3);
        fuel3.add ( R.id.fuel3_1);
        ArrayList<Integer> fuel4 = new ArrayList<>();
        fuel4.add( R.id.fuel4);
        fuel4.add( ( R.id.fuel4_1 ) );
        ArrayList<Integer> fuel5 = new ArrayList<>();
        fuel5.add( R.id.fuel5);
        fuels.add(fuel1);
        fuels.add(fuel2);
        fuels.add(fuel3);
        fuels.add(fuel4);
        fuels.add(fuel5);

        updateFuelVisibility();
    }

    public void updateFuelVisibility(){
        for( int i = 0 ; i < fuels.get(((Game)game).getLevelNumber()).size()  ; i++ ){
            if( ((Game)game).getFuelsSelected().get(i) )
                findViewById(fuels.get(((Game)game).getLevelNumber()).get(i)).setVisibility(View.INVISIBLE);
        }
    }

    public void updateFuelLocations(){
        for( int i = 0 ; i < fuels.get(((Game)game).getLevelNumber()).size() ; i++ ){
            findViewById(fuels.get(((Game)game).getLevelNumber()).get(i)).setX((float)(((Game)game).getFuels().get(i).getX()));
            findViewById(fuels.get(((Game)game).getLevelNumber()).get(i)).setY((float)(((Game)game).getFuels().get(i).getY()));
        }
        updateFuelVisibility();
    }

    public void updateQuestionBoxLocations(){
        findViewById(questionBoxes.get(((Game)game).getLevelNumber())).setX((float)(((Game)game).getQuestionBox().getX()));
        findViewById(questionBoxes.get(((Game)game).getLevelNumber())).setY((float)(((Game)game).getQuestionBox().getY()));
        updateQuestionVisibility();
    }

}

