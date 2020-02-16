package com.example.hakan.keplersjourney;

import android.view.View;

import java.util.ArrayList;

/**
 * Level Class
 * @authors Hakan Sivuk , Berk Bozkurt, Mehmet Tolga Tomris
 * @version 12.05.2018
 */
public class Level
{

    //properties
    private ArrayList <Planet> planets;
    private SpaceCraft         spaceCraft;
    private ArrayList<Fuel>    fuels;
    private QuestionBox        questionBox;
    private boolean            isLevelOver;
    private boolean            isCompleted;
    private boolean            isPaused;
    private boolean            isQuestionSelected;
    private int                levelNumber;


    //constructor
    public Level( int levelNumber , ArrayList<Planet> planets , SpaceCraft spaceCraft , ArrayList<Fuel> fuels , QuestionBox questionBox )
    {
        this.levelNumber   = levelNumber;
        this.planets       = planets;
        this.spaceCraft    = spaceCraft;
        this.fuels         = fuels;
        this.questionBox   = questionBox;
        isLevelOver        = false;
        isCompleted        = false;
        isPaused           = false;
        isQuestionSelected = false;
    }

    //methods
    /**
     * A method to renew the properties of certain objects
     */
    public void update()
    {
        if( !isLevelOver && !isPaused )
        {
            Vector v;
            v = spaceCraft.computeForces( planets );

            for( int i = 0 ; i < planets.size() ; i++ )
            {
                double x;
                double y;

                x = planets.get( i ).getX();
                y = planets.get( i ).getY();
                planets.get( i ).setX( x - v.getXAxis() );
                planets.get( i ).setY( y - v.getYAxis() );
            }

            {
                double x;
                double y;

                x = questionBox.getX();
                y = questionBox.getY();
                questionBox.setX( x - v.getXAxis() );
                questionBox.setY( y - v.getYAxis() );

                if( !questionBox.isSelected() && spaceCraft.isArrived( questionBox , SpaceCraft.SIZE / 2 ) )
                {
                    isPaused = true;
                    questionBox.setSelected( true );
                }
            }

            for( int i = 0 ; i < fuels.size() ; i++ )
            {
                double x;
                double y;

                x = fuels.get( i ).getX();
                y = fuels.get( i ).getY();
                fuels.get( i ).setX( x - v.getXAxis() );
                fuels.get( i ).setY( y - v.getYAxis() );
                if ( !fuels.get(i).getSelected() && spaceCraft.isArrived( fuels.get( i ) , SpaceCraft.SIZE / 2 ) )
                {
                    spaceCraft.addFuel( fuels.get( i ).getFuelAmount() );
                    fuels.get( i ).setSelected( true );
                }
            }

            for( int i = 0 ; i < planets.size() ; i++ )
            {
                if ( spaceCraft.isArrived( planets.get( i ) , planets.get( i ).getRadius() ) )
                {
                    if( i == planets.size() - 1 ) // the last planet added to the ArrayList
                    {
                        isLevelOver = true;
                        isCompleted = true;
                    }
                    else
                        isLevelOver = true;
                }
            }
        }
    }

    /**
     * Ends the level when the time is up
     */
    public void timeIsUp()
    {
        isLevelOver = true;
    }

    /**
     * Updates the spacecraft with a vector
     * @param v the vector to be added to the spaceCraft
     */
    public void updateSpaceCraft( Vector v )
    {
        spaceCraft.addVector( v );
    }

    /**
     * Gets the planets by an ArrayList
     * @return the planets ArrayList
     */
    public ArrayList<Planet> getPlanets()
    {
        return planets;
    }

    /**
     * Gets the spacecraft
     * @return the spaceCraft
     */
    public SpaceCraft getSpaceCraft()
    {
        return spaceCraft;
    }

    /**
     * Gets the the fuels ArrayList
     * @return the fuels ArrayList
     */
    public ArrayList<Fuel> getFuels()
    {
        return fuels;
    }

    /**
     * Checks whether the level is over or not
     * @return isLevelOver status as boolean
     */
    public boolean isLevelOver()
    {
        return isLevelOver;
    }

    /**
     * Checks whether the game is completed or not
     * @return isCompleted status as boolean
     */
    public boolean isCompleted()
    {
        return isCompleted;
    }

    /**
     * Checks whether the game is paused or not
     * @return isPaused status as boolean
     */
    public boolean isPaused()
    {
        return isPaused;
    }

    /**
     * Gets the questionBox object
     * @return the questionBox object
     */
    public QuestionBox getQuestionBox()
    {
        return questionBox;
    }

    /**
     * A method to pause the game when the question box is taken
     * @param b
     */
    public void setIsPaused( boolean b )
    {
        isPaused = b;
    }

    /**
     * Checks whether the question is selected or not
     * @return isSelected status of the question as boolean
     */
    public boolean getQuestionSelected()
    {
        return questionBox.isSelected();
    }

    /**
     * Gets an ArrayList of Boolean which makes it possible to keep track of status of selection of every fuel object in the level
     * @return an ArrayList of Boolean type
     */
    public ArrayList<Boolean> getFuelsSelected()
    {
        ArrayList<Boolean> b;
        b = new ArrayList<>();

        for( int i = 0 ; i < fuels.size() ; i++ )
        {
            b.add( fuels.get( i ).getSelected() );
        }
        return b;
    }

    /**
     * Gets the current level number
     * @return the level number
     */
    public int getLevelNumber()
    {
        return levelNumber;
    }

    /**
     * Sets the level over status
     * @param levelOver
     */
    public void setIsLevelOver(boolean levelOver)
    {
        isLevelOver = levelOver;
    }

    /**
     * Sets the level completeness
     * @param completed
     */
    public void setIsCompleted(boolean completed)
    {
        isCompleted = completed;
    }

}