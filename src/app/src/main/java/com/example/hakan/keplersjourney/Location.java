package com.example.hakan.keplersjourney;

/**
 * Location Class
 * @author Mehmet Tolga Tomris
 * @version 12.05.2018
 */

public class Location
{
    //properties
    private double x;
    private double y;

    //constant
    private static final double IN_SIGHT = 500;

    //constructor
    public Location ( double x , double y )
    {
        this.x = x;
        this.y = y;
    }

    //methods

    /**
     * Gets the X coordinate of the location
     * @return x
     */
    public double getX()
    {
        return x;
    }

    /**
     * Gets the Y coordinate of the location
     * @return y
     */
    public double getY()
    {
        return y;
    }


    /**
     * Sets the X coordinate of the location
     * @param x1
     */
    public double setX ( double x1 )
    {
        x = x1;
        return x;
    }

    /**
     * Sets the Y coordinate of the location
     * @param y1
     */
    public double setY ( double y1 )
    {
        y = y1;
        return y;
    }


    /**
     * Checks whether our location is sufficiently close to another location
     * @param location
     * @return boolean
     */
    public boolean closeEnough ( Location location )
    {
        return ( ( ( this.x - location.x ) * ( this.x - location.x ) + ( this.y - location.y ) * ( this.y - location.y ) ) < IN_SIGHT * IN_SIGHT );
    }

}
