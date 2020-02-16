package com.example.hakan.keplersjourney;

/**
 * Planet Class
 * @author Muhammed Yusuf Toy
 * @version 12.05.2018
 */

public class Planet extends Location
{

    //properties
    private int radius;
    private int weight;

    //constructor
    public Planet( int radius , int weight , double x , double y )
    {
        super( x , y );
        this.radius = radius;
        this.weight = weight;
    }

    //methods
    /**
     * Gets the weight of planet
     * @return weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Gets the radius of planet
     * @return radius
     */
    public int getRadius()
    {
        return radius;
    }
}
