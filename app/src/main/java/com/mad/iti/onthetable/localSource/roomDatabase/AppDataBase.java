package com.mad.iti.onthetable.localSource.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;

@Database(entities = {Meal.class , MealPlanner.class} , version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance = null;
    public abstract MealDao mealDao();
    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"dbMeals").build();
        }
        return instance;
    }
}