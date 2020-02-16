package com.example.hakan.keplersjourney;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * User Class
 * @author Yusuf Nevzat Sengun
 * @version 12.05.2018
 */
@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String name;

    private double score;
    private int level;
    private int active;
    private Level currentLevel;

    public User(String name, double score, int level, int active , Level currentLevel ) {
        this.name = name;
        this.score = score;
        this.level = level;
        this.active = active;
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore( double score ){
        this.score = score;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel( int level ){
        this.level = level;
    }

    public int getActive(){
        return active;
    }

    public void setActive( int active ){
        this.active = active;
    }
}
