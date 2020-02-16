package com.example.hakan.keplersjourney;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * AppDataBase Class
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */

@Database(entities = {User.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract UserDao userDao();

    public static AppDataBase getInstance(Context context){
        if( instance == null ) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "userDataBase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
}