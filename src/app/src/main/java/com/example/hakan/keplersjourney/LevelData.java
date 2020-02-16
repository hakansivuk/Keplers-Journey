package com.example.hakan.keplersjourney;

import android.widget.Space;

/**
 * LevelData Class
 * @authors Berk Bozkurt , Eray Ünsal Atay
 * @version 12.05.2018
 */
import java.util.ArrayList;

/**
 * LevelData Class
 * @authors Gravity Guys ( collective work )
 * @version 12.05.2018
 */
public class LevelData
{

    //properties
    private ArrayList<Level> levels;

    //constructor
    public LevelData()
    {
        levels = new ArrayList<>();
        createLevels();
    }

    //methods
    /**
     * Gets the levels as an ArrayList
     * @return levels
     */
    public ArrayList<Level> getLevels()
    {
        return levels;
    }

    /**
     * Creates all the levels
     */
    public void createLevels()
    {
        Level             level1;
        Level             level2;
        Level             level3;
        Level             level4;
        Level             level5;
        ArrayList<Planet> planets1;
        ArrayList<Planet> planets2;
        ArrayList<Planet> planets3;
        ArrayList<Planet> planets4;
        ArrayList<Planet> planets5;
        SpaceCraft        sc1;
        SpaceCraft        sc2;
        SpaceCraft        sc3;
        SpaceCraft        sc4;
        SpaceCraft        sc5;
        QuestionBox       q1;
        QuestionBox       q2;
        QuestionBox       q3;
        QuestionBox       q4;
        QuestionBox       q5;
        ArrayList<Fuel>   f1;
        ArrayList<Fuel>   f2;
        ArrayList<Fuel>   f3;
        ArrayList<Fuel>   f4;
        ArrayList<Fuel>   f5;
        ArrayList<String> choiceLevel1;
        ArrayList<String> choiceLevel2;
        ArrayList<String> choiceLevel3;
        ArrayList<String> choiceLevel4;
        ArrayList<String> choiceLevel5;

        //level 1
        planets1 = new ArrayList<>();
        planets1.add( new Planet( 150 ,2540 , 350 , 450 ) );
        planets1.add( new Planet( 150 ,2540 , 200 , -100 ) );
        sc1 = new SpaceCraft( 10 , -3,500 , 1000 );

        choiceLevel1 = new ArrayList<>();
        choiceLevel1.add( "F=m*a" );
        choiceLevel1.add( "e = mc^2" );
        choiceLevel1.add( "V=I*R" );
        choiceLevel1.add( "G*M*m/d^2" );

        q1 = new QuestionBox(150 , 600 ,"Which of the following is the formula for the law of universal gravitation" ,"G*M*m/d^2" , choiceLevel1 );
        f1 = new ArrayList<>();
        f1.add( new Fuel ( 250 , 350 ,25 ) );
        level1 = new Level( 0 ,  planets1 , sc1 , f1 , q1 );
        levels.add( level1 );

        //level 2
        planets2 = new ArrayList<>();
        planets2.add( new Planet( 210 ,3750 , -200 , 400 ) );
        planets2.add( new Planet( 180 ,3750 , 400 , 300 ) );
        planets2.add( new Planet( 180 ,3750 , 950 , -100 ) );
        planets2.add( new Planet( 165 ,5250 , 400 , -1000 ) );
        sc2 = new SpaceCraft( 10 , -2 , 500 , 1000 );

        choiceLevel2 = new ArrayList<>();
        choiceLevel2.add( "Newtons first law" );
        choiceLevel2.add( "Newtons second law" );
        choiceLevel2.add( "None of these" );
        choiceLevel2.add( "Newtons third law" );

        q2 = new QuestionBox(300 , 650 , "Rocket engine works on the principle of" , "Newtons third law" , choiceLevel2 );
        f2 = new ArrayList<>();
        f2.add( new Fuel ( 820 , 250 , 40 ) );
        level2 = new Level( 1 , planets2 , sc2 , f2 , q2 );
        levels.add ( level2 );

        //level 3
        planets3 = new ArrayList<>();
        planets3.add( new Planet( 200 ,4500 , 900 , -300 ) );
        planets3.add( new Planet( 200 ,4500 , 525 , -870 ) );
        planets3.add( new Planet( 300 ,10130 , 100 , 25 ) );
        planets3.add( new Planet( 225 ,5690 , 300 , -1350 ) );
        sc3 = new SpaceCraft(10 , -3 ,500 ,1000);

        choiceLevel3 = new ArrayList<>();
        choiceLevel3.add( "Apollo" );
        choiceLevel3.add( "Eagle Challenger" );
        choiceLevel3.add( "Columbia" );
        choiceLevel3.add( "Challenger" );

        q3 = new QuestionBox(450 , -200 , "What was the name of the space shuttle that landed man on the moon?" , "Apollo" , choiceLevel3 );
        f3 = new ArrayList<>();
        f3.add( new Fuel ( 840 , -450 , 60 ) );
        f3.add ( new Fuel ( 800 , 400 , 20));
        level3 = new Level( 2 , planets3 , sc3 , f3 , q3 );
        levels.add ( level3 );

        //level 4
        planets4 = new ArrayList<>();
        planets4.add( new Planet( 225 , 5690 , 1750 , -2150 ) );
        planets4.add( new Planet( 150 , 2540 , 1600 , -400 ) );
        planets4.add( new Planet( 300 , 10130 , 2000 , -3000 ) );
        planets4.add( new Planet( 900 , 45000 , -300 , -1150 ) );
        planets4.add( new Planet( 225 , 5690 , 1300 , -2800 ) );
        sc4 = new SpaceCraft(10 , -2 , 500 , 1000 );

        choiceLevel4 = new ArrayList<>();
        choiceLevel4.add( "Nikola Tesla" );
        choiceLevel4.add( "Thomas Edison" );
        choiceLevel4.add( "Steve Jobs" );
        choiceLevel4.add( "Edwin Hubble" );

        q4 = new QuestionBox(-400 ,-700  , "Hubble Space Telescope is named after who?" , "Edwin Hubble" , choiceLevel4 );
        f4 = new ArrayList<>();
        f4.add( new Fuel ( 2030 , -200 , 80 ) );
        f4.add ( new Fuel(2500 , -1900, 80) );
        level4 = new Level( 3 , planets4 , sc4 , f4 , q4 );
        levels.add ( level4 );

        //level 5
        planets5 = new ArrayList<>();
        planets5.add( new Planet( 150 , 1000 , 300 , 350 ) );
        planets5.add( new Planet( 375 , 1500 , 650 , -650 ) );
        planets5.add( new Planet( 175 ,3000 , -200 , -600 ) );
        planets5.add( new Planet( 400 ,500 ,1150 , 200 ) );
        planets5.add( new Planet( 100 ,10000 , -100 , 550 ) );//
        planets5.add( new Planet( 100 ,15000 , -300 , -300 ) );//
        planets5.add( new Planet( 100 ,30000 , 550 , -1050 ) );//
        planets5.add( new Planet( 150 ,2000 , 750 , -1200 ) );
        sc5 = new SpaceCraft(10 , -1 , 500 , 1000);

        choiceLevel5 = new ArrayList<>();
        choiceLevel5.add( "Large Magellanic Cloud" );
        choiceLevel5.add( "Cartwheel Galaxy" );
        choiceLevel5.add( "Comet Galaxy" );
        choiceLevel5.add( "The Milky Way galaxy" );

        q5 = new QuestionBox(700 , -800 , "Earth is located in which galaxy?" , "The Milky Way galaxy" , choiceLevel5 );
        f5 = new ArrayList<>();
        f5.add( new Fuel ( -450 , -150 , 40 ) );
        level5 = new Level( 4 , planets5 , sc5 , f5 , q5 );
        levels.add ( level5 );
    }
}
