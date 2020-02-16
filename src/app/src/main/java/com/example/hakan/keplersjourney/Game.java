package com.example.hakan.keplersjourney;

import android.os.Handler;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Game Class
 * @authors Gravity Guys ( collective work )
 * @version 12.05.2018
 */

public class Game extends Observable
{

    //properties
    private String    name;
    private int       time;
    private Level     currentLevel;
    private int       levelNumber;
    private double    score;
    private LevelData data;
    private Handler   handler;
    private Runnable  runnable;

    //constants
    private static final int    TOTAL_TIME       = 10000;
    private static final int    PERIOD           = 5; //ms
    private static final double VECTOR_MAGNITUDE = 0.17;
    private static final int    RIGHT            = 3;
    private static final int    DOWN             = 6;
    private static final int    LEFT             = 9;
    private static final int    UP               = 12;

    //constructor
    public Game( int level, double score, String name , Level currentlevel ){
        this.name = name;
        this.score = score;
        time = 0;
        data = new LevelData();
        levelNumber = level;
        currentLevel = currentlevel;
        handler = new Handler();
        runnable = new Runnable(){
            public void run(){
                if( !currentLevel.isLevelOver() ) {
                    update();
                    handler.postDelayed(this, PERIOD);
                    if( time >= TOTAL_TIME )
                        currentLevel.timeIsUp();
                }

                else
                {
                    stop();
                    time = 0;
                    if(currentLevel.isCompleted())
                        updateScore();
                }

            }
        };

        handler.removeCallbacks(runnable);
    }

    public Game( int level , double score , String name )
    {
        this.name    = name;
        this.score   = score;
        time         = 0;
        data         = new LevelData();
        levelNumber  = level;
        currentLevel = data.getLevels().get( levelNumber );
        handler      = new Handler();
        runnable     = new Runnable(){
            public void run()
            {

                if( !currentLevel.isLevelOver() && !currentLevel.isPaused() )
                {
                    update();

                    handler.postDelayed( this, PERIOD );
                    if( time >= TOTAL_TIME )
                    {
                        currentLevel.timeIsUp();
                    }
                }
                else
                {
                    stop();
                    time = 0;
                    if (currentLevel.isCompleted())
                    {
                        updateScore();
                    }
                }
            }
        };
        handler.removeCallbacks( runnable );
    }

    //methods

    /**
     * Starts the game
     */
    public void start()
    {
        handler.postDelayed( runnable , 0 );
        currentLevel.setIsPaused( false );
    }

    /**
     * Stops the game
     */
    public void stop()
    {
        handler.removeCallbacks( runnable );
    }

    /**
     * Renews the game
     */
    public void update()
    {
        currentLevel.update();
        time = time + PERIOD;
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the spacecraft with a direction
     * @param direction is the direction the spaceCraft will move in
     */
    public void updateSpaceCraft( int direction )
    {
        getSpaceCraft().addFuel( currentLevel.getSpaceCraft().getFuelConsumption() );

        if( direction == RIGHT )
        {
            currentLevel.updateSpaceCraft( new Vector( VECTOR_MAGNITUDE , 0 ) );
        }
        if( direction == DOWN )
        {
            currentLevel.updateSpaceCraft( new Vector( 0 , VECTOR_MAGNITUDE ) );
        }
        if( direction == LEFT )
        {
            currentLevel.updateSpaceCraft( new Vector( -VECTOR_MAGNITUDE , 0 ) );
        }
        if( direction == UP )
        {
            currentLevel.updateSpaceCraft( new Vector( 0 , -VECTOR_MAGNITUDE ) );
        }
    }

    /**
     * Updates the score
     */
    public void updateScore()
    {
        score = score + 10 * ( TOTAL_TIME / 1000 - (time / 1000) ) * currentLevel.getSpaceCraft().getRemainingFuel() / SpaceCraft.FUEL;
    }

    /**
     * Updates the score
     * @param update is the score incrementation
     */
    public void updateScore( int update )
    {
        score = score + update;
    }

    /**
     * Proceeds to the next level
     */
    public void nextLevel()
    {
        if( !isGameOver() )
        {
            levelNumber++;
            currentLevel = data.getLevels().get( levelNumber );
        }
    }

    /**
     * Restarts the level
     */
    public void restartLevel()
    {
        ArrayList<Boolean> fuels;
        boolean question;

        data         = new LevelData();
        fuels        = currentLevel.getFuelsSelected();
        question     = currentLevel.getQuestionSelected();
        currentLevel = data.getLevels().get( levelNumber );

        for( int i = 0 ; i < fuels.size() ; i++ )
        {
            currentLevel.getFuels().get(i).setSelected( fuels.get( i ) );
        }
        currentLevel.getQuestionBox().setSelected( question );
    }

    /**
     * Checks whether the fuel is completely consumed or not
     * @return boolean representation of fuel status
     */
    public boolean isFuelConsumed()
    {
        return currentLevel.getSpaceCraft().getRemainingFuel() <= 0;
    }

    /**
     * Checks whether the level is completed or not
     * @return boolean representation of the completed status
     */
    public boolean isLevelCompleted()
    {
        return currentLevel.isCompleted();
    }

    /**
     * Checks whether the game is over or not
     * @return boolean
     */
    public boolean isGameOver()
    {
        return isLevelOver() && levelNumber == data.getLevels().size() - 1;
    }

    /**
     * Checks whether the level is over or not
     * @return boolean
     */
    public boolean isLevelOver()
    {
        return currentLevel.isLevelOver();
    }

    /**
     * Checks whether the game is paused
     * @return is paused
     */
    public boolean isPaused()
    {
        return currentLevel.isPaused();
    }

    /**
     * Gets the name of the user
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the number of level
     * @return the levelNumber
     */
    public int getLevelNumber()
    {
        return levelNumber;
    }

    /**
     * Gets the planets as an ArrayList
     * @return ArrayList of planets
     */
    public ArrayList<Planet> getPlanets()
    {
        return currentLevel.getPlanets();
    }

    /**
     * Gets the spacecraft
     * @return the SpaceCraft
     */
    public SpaceCraft getSpaceCraft()
    {
        return currentLevel.getSpaceCraft();
    }


    public ArrayList<Fuel> getFuels()
    {
        return currentLevel.getFuels();
    }

    /**
     * Gets the angle between the target and the spacecraft
     * @return double, the angle
     */
    public double getTarget()
    {
        double x_1;
        double y_1;
        double x_2;
        double y_2;

        x_1 = currentLevel.getPlanets().get( currentLevel.getPlanets().size() - 1 ).getX();
        y_1 = currentLevel.getPlanets().get( currentLevel.getPlanets().size() - 1 ).getY();
        x_2 = currentLevel.getSpaceCraft().getX();
        y_2 = currentLevel.getSpaceCraft().getY();

        return 90 + Math.toDegrees( Math.atan2( y_1 - y_2 , x_1 - x_2 ) );
    }

    /**
     * Gets the passed time
     * @return the time divided by 1000
     */
    public int getTime()
    {
        return time / 1000 ;
    }

    /**
     * Gets the score
     * @return the score
     */
    public double getScore()
    {
        return score;
    }

    /**
     * Gets the questionBox object
     * @return the questionBox object
     */
    public QuestionBox getQuestionBox()
    {
        return currentLevel.getQuestionBox();
    }

    /**
     * Gets the selected status of the questionBox object
     * @return the boolean representation of selected status
     */

    public boolean getQuestionSelected()
    {
        return currentLevel.getQuestionSelected();
    }

    /**
     * Gets the selected status of the questionBox object
     * @return an ArrayList of  Boolean which is used to determine the status of selection of  Fuel objects
     */
    public ArrayList<Boolean> getFuelsSelected()
    {
        return currentLevel.getFuelsSelected();
    }

    public void setIsPaused( boolean b )
    {
        currentLevel.setIsPaused( b );
    }

    public Level getCurrentLevel()
    {
        return currentLevel;
    }
}
