package com.example.hakan.keplersjourney;

/**
 * Vector Class
 * @author Berk Bozkurt
 * @version 12.05.2018
 */

public class Vector
{

    //properties
    private double x;
    private double y;

    //constructor
    public Vector ( double x , double y )
    {
        this.x = x;
        this.y = y;
    }

    //methods
    /**
     * Gets the X-axis of the vector
     * @return x
     */
    public double getXAxis()
    {
        return x;
    }

    /**
     * Gets the Y-axis of the vector
     * @return y
     */
    public double getYAxis()
    {
        return y;
    }

    /**
     * Sets the X-axis of the vector
     * @param x
     */
    public void setXAxis( double x )
    {
        this.x = x;
    }

    /**
     * Sets the Y-axis of the vector
     * @param y
     */
    public void setYAxis( double y )
    {
        this.y = y;
    }
}
