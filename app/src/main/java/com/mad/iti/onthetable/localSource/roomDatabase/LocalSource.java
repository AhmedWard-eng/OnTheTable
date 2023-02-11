package com.mad.iti.onthetable.localSource.roomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;

import java.util.List;

public interface LocalSource {

    LiveData<List<Meal>> getAllFavoriteMeals();

    void insertMealToFavorite(Meal meal);

    void deleteMealFromFavorite(Meal product);

    LiveData<List<MealPlanner>> getAllMealsFromPlannerAtDate(String date);

//    void deleteMealFromPlanner(String date,String mealId);

    @Delete
    void deleteMealFromPlanner(MealPlanner mealPlanner);

    @Insert
    void insertMealIntoPlanner(MealPlanner mealPlanner);
}
