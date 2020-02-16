package com.example.hakan.keplersjourney;

/**
 * Fuel Class
 * @authors Berk Bozkurt , Muhammed Yusuf Toy
 * @version 12.05.2018
 */

public class Fuel extends Location
{

    //properties
    private int     fuelAmount;
    private boolean selected;

    //constant
    public final static int SIZE = 60;

    //constructor
    public Fuel( int x , int y , int fuelAmount )
    {
        super( x , y );
        selected        = false;
        this.fuelAmount = fuelAmount;
    }

    //methods
    /**
     * Gets the amount of fuel
     * @return fuelAmount
     */
    public int getFuelAmount()
    {
        return fuelAmount;
    }

    /**
     * Gets the status of selection
     * @return selected status as boolean
     */
    public boolean getSelected()
    {
        return selected;
    }

    /**
     * Sets the selected status
     * @param selected
     */
    public void setSelected( boolean selected )
    {
        this.selected = selected;
    }
}
