package com.example.hakan.keplersjourney;

import java.util.ArrayList;

/**
 * SpaceCraft Class
 * @authors Hakan Sivuk , Mehmet Tolga Tomris , Berk Bozkurt
 * @version 12.05.2018
 */
public class SpaceCraft extends Location
{

    //properties
    private int    fuelLevel;
    private int    weight;
    private Vector vector;
    private int    fuelConsumption;

    //constants
    private static final double G    = 0.5; //arbitrary value to adjust the forces
    public static final int     FUEL = 100;
    public static final int     SIZE = 120;

    //constructor
    public SpaceCraft( int weight , int fuelConsumption , int x , int y )
    {
        super( x , y );
        fuelLevel            = FUEL;
        this.weight          = weight;
        this.fuelConsumption = fuelConsumption;
        vector               = new Vector( 0 , 0 );
    }

    //methods
    /**
     * Gets the remaining fuel of the spacecraft
     * @return fuelLevel
     */
    public int getRemainingFuel()
    {
        return fuelLevel;
    }

    /**
     * Adds fuel to the spacecraft
     * @param fuel that will be added
     */
    public void addFuel( int fuel )
    {
        fuelLevel = fuelLevel + fuel;
    }

    /**
     * Computes the forces on the spacecraft
     * @param planets ArrayList
     */
    public Vector computeForces( ArrayList<Planet> planets )
    {

        double x;
        double y;
        double force;
        double horizontalDistance;
        double verticalDistance;
        Vector v;

        x = 0;
        y = 0;

        //computed forces on the space craft
        for( int i = 0 ; i < planets.size() ; i++ )
        {

            horizontalDistance = planets.get( i ).getX() +  planets.get( i ).getRadius() - getX() - SIZE / 2;
            verticalDistance = planets.get( i ).getY() +  planets.get( i ).getRadius() - getY()- SIZE / 2;

            force = ( G * weight * (planets.get( i ).getWeight())/ 10) / ( horizontalDistance * horizontalDistance + verticalDistance * verticalDistance );

            x += force * horizontalDistance / Math.sqrt( horizontalDistance * horizontalDistance + verticalDistance * verticalDistance );
            y += force * verticalDistance / Math.sqrt( ( horizontalDistance * horizontalDistance + verticalDistance * verticalDistance ));
        }
        v = new Vector ( x , y );
        this.addVector( v );
        return new Vector(  vector.getXAxis() , vector.getYAxis() );
    }

    /**
     * Adds the vector
     * @param v the vector that will be added
     */
    public void addVector( Vector v )
    {
        vector.setXAxis( vector.getXAxis() + v.getXAxis() );
        vector.setYAxis( vector.getYAxis() + v.getYAxis() );
    }

    /**
     * Checks whether our location is inside the boundary of another location
     * @param location
     * @param radius
     * @return the boolean representation
     */
    public boolean isArrived( Location location , double radius )
    {
        double spaceCraftCenterX;
        double spaceCraftCenterY;
        double locationCenterX;
        double locationCenterY;
        double minDistance;
        double distance;

        spaceCraftCenterX = SIZE / 2 + getX();
        spaceCraftCenterY = SIZE / 2 + getY();
        locationCenterX   = radius + location.getX();
        locationCenterY   = radius + location.getY();
        minDistance       = SIZE / 2 + radius;
        distance          = Math.sqrt( ( spaceCraftCenterX - locationCenterX ) * ( spaceCraftCenterX - locationCenterX ) + ( spaceCraftCenterY - locationCenterY ) * ( spaceCraftCenterY - locationCenterY ) );

        return ( distance <= minDistance );
    }
    /**
     * Checks if the spacecraft is close enough to a specified planet
     * @param planet that is checked whether it is close enough or not
     * @return true if the spacecraft is close enough to the planet
     */
    public boolean isInSight( Planet planet )
    {
        return ( this.closeEnough( planet ) );
    }

    /**
     * Gets the fuel consumption parameter of the spaceCraft
     * @return the fuel consumption value
     */
    public int getFuelConsumption()
    {
        return fuelConsumption;
    }

    public void setFuelLevel( int fuelLevel )
    {
        this.fuelLevel = fuelLevel;
    }

    public void setVector( Vector v  ){
        vector = v;
    }

    public Vector getVector(){
        return vector;
    }

}
