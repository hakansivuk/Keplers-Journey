package com.example.hakan.keplersjourney;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Converter Interface
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */

@Dao
public interface UserDao {
    @Insert
    public void insertUser( User user );

    @Update
    public void updateUser( User user );

    @Delete
    public void deleteUser( User user );

    @Query("DELETE FROM User")
    public void deleteAll();

    @Query("SELECT * FROM User WHERE active = 1")
    public List< User > getOnGame();

    @Query("SELECT * FROM User WHERE active = 0 ORDER BY score DESC LIMIT 5")
    public List< User > getCompleted();

    @Query("SELECT * FROM User WHERE name = :userName")
    public User getUser( String userName );

    @Query("SELECT * FROM User WHERE EXISTS (SELECT * FROM User WHERE name = :userName)")
    public User isExist( String userName );
}
