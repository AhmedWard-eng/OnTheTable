package com.mad.iti.onthetable.localSource.roomDatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.Meal;

import java.util.List;

public class LocalSourceRoom implements LocalSource{

    private MealDao dao;
    private static LocalSourceRoom localSource = null;
    private LiveData<List<Meal>> favoriteMeals;

    private LocalSourceRoom(Context context){
        AppDataBase appDataBase = AppDataBase.getInstance(context.getApplicationContext());
        dao = appDataBase.mealDao();
        favoriteMeals = dao.getAllFavoriteMeals();
    }

    public static LocalSourceRoom getInstance(Context context){
        if(localSource == null){
            localSource = new LocalSourceRoom(context);
        }
        return localSource;
    }

    @Override
    public LiveData<List<Meal>> getAllFavoriteMeals() {
        return favoriteMeals;
    }

    @Override
    public void insertMealToFavorite(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMealToFavorite(meal);
            }
        }).start();

    }

    @Override
    public void deleteMealFromFavorite(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMealFromFavorite(meal);
            }
        }).start();

    }
}
