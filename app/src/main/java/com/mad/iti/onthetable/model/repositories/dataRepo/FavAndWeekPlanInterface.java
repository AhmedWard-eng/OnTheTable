package com.mad.iti.onthetable.model.repositories.dataRepo;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;

import java.util.List;

public interface FavAndWeekPlanInterface {

    void refreshPlanner();
    void refreshMeals();

   LiveData<List<Meal>> getFavMealsLiveData();

    LiveData<List<MealPlanner>> getAllMealsFromPlannerAtDate(String date);

    void addToFavorites(Meal meal, OnAddingListener onAddingListener);

    void addToPlanner(MealPlanner mealPlanner,OnAddingListener onAddingListener);

    void deleteMealFromPlanner(MealPlanner mealPlanner);

    void deleteMealFromFavorites(Meal meal);
    void deleteAllFav();
    void deleteAllWeekPlan();

    LiveData<Meal> getMealById(String idMeal);

}
