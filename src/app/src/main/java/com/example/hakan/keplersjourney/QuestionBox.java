package com.example.hakan.keplersjourney;

import java.util.ArrayList;

/**
 * QuestionBox Class
 * @authors Eray Ünsal Atay
 * @version 12.05.2018
 */

public class QuestionBox extends Location
{

    // properties
    private String            question;
    private String            answer;
    private ArrayList<String> choices;
    private boolean           selected;

    // constructors
    public QuestionBox( int x , int y , String question , String answer , ArrayList<String> choices )
    {
        super( x , y );
        this.question = question;
        selected      = false;
        this.answer   = answer;
        this.choices  = choices;
    }

    // methods
    /**
     * Gets the question text
     * @return question
     */
    public String getQuestion()
    {
        return question;
    }

    /**
     * Gets the answer text
     * @return the answer
     */
    public String getAnswer()
    {
        return answer;
    }

    /**
     * Gets first wrong answer
     * @param index
     * @return choice
     */
    public String getChoice( int index )
    {
        return choices.get( index );
    }

    /**
     * sets the selected status of the questionBox
     */
    public void setSelected( boolean b )
    {
        selected = b;
    }

    /**
     * Gets the status of selection of the questionBox
     * @return the boolean representation of the status of selection
     */
    public boolean isSelected()
    {
        return selected;
    }
}
