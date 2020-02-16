package com.example.hakan.keplersjourney;

import android.arch.persistence.room.TypeConverter;

/**
 * Converter Class
 * @authors Yusuf Nevzat Sengun , Hakan Sivuk
 * @version 12.05.2018
 */

public class Converters
{
    //properties
    private static String result;
    private static int    last;

    //methods

    public static Double getFloat( ) {
        String ret;

        ret = "";
        while( result.charAt( last ) != ' ' ){
            ret = ret + result.charAt( last );
            last++;
        }
        last++;

        return Double.parseDouble( ret );
    }

    public static int getInt( ) {
        String ret;

        ret = "";
        while( result.charAt( last ) != ' ' ){
            ret = ret + result.charAt( last );
            last++;
        }
        last++;

        return Integer.parseInt( ret );
    }

    @TypeConverter
    public static Level fromString( String res ) {
        result = res;
        last = 0;
        Level level;
        int levelNumber;
        int cnt;
        LevelData levelData;

        levelData = new LevelData();
        levelNumber = getInt();
        level = levelData.getLevels().get( levelNumber );

        level.getSpaceCraft().setX( getFloat() );
        level.getSpaceCraft().setY( getFloat() );
        level.getSpaceCraft().setFuelLevel( getInt() );
        level.getSpaceCraft().setVector( new Vector( getFloat() , getFloat() ) );

        cnt = getInt();
        for( int i = 0 ; i < cnt ; i++ ){
            level.getPlanets().get( i ).setX( getFloat() );
            level.getPlanets().get( i ).setY( getFloat() );
        }

        cnt = getInt();
        for( int i = 0 ; i < cnt ; i++ ){
            level.getFuels().get( i ).setX( getFloat() );
            level.getFuels().get( i ).setY( getFloat() );
            if( getInt() == 1 )
                level.getFuels().get( i ).setSelected( true );
            else
                level.getFuels().get( i ).setSelected( false );
        }

        cnt = getInt();
        for( int i = 0 ; i < cnt ; i++ ) {
            level.getQuestionBox().setX( getFloat() );
            level.getQuestionBox().setY( getFloat() );
            if( getInt() == 1 )
                level.getQuestionBox().setSelected( true );
            else
                level.getQuestionBox().setSelected( false );
        }

        if( getInt() == 1 )
            level.setIsLevelOver( true );
        else
            level.setIsLevelOver( false );

        if( getInt() == 1 )
            level.setIsCompleted( true );
        else
            level.setIsCompleted( false );

        return level;
    }

    @TypeConverter
    public static String fromLevel(Level level) {
        String res;

        res = "";

        res += level.getLevelNumber() + " ";

        res += level.getSpaceCraft().getX() + " " + level.getSpaceCraft().getY() + " " + level.getSpaceCraft().getRemainingFuel() + " " +
                level.getSpaceCraft().getVector().getXAxis() + " " + level.getSpaceCraft().getVector().getYAxis() + " ";

        res += level.getPlanets().size() + " ";
        for( int i = 0 ; i < level.getPlanets().size() ; i++ ){
            res += level.getPlanets().get( i ).getX() + " " + level.getPlanets().get( i ).getY() + " ";
        }

        res += level.getFuels().size() + " ";
        for( int i = 0 ; i < level.getFuels().size() ; i++ ){
            res += level.getFuels().get( i ).getX() + " " + level.getFuels().get( i ).getY() + " ";
            if( level.getFuelsSelected().get( i ) )
                res += 1 + " ";
            else
                res += 0 + " ";
        }

        res += 1 + " ";
        res += level.getQuestionBox().getX() + " " + level.getQuestionBox().getY() + " ";
        if( level.getQuestionSelected() )
            res += 1 + " ";
        else
            res += 0 + " ";

        if( level.isLevelOver() )
            res += 1 + " ";
        else
            res += 0 + " ";

        if( level.isCompleted() )
            res += 1 + " ";
        else
            res += 0 + " ";

        return res;
    }

}
