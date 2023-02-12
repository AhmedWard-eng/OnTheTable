package com.mad.iti.onthetable.ui.mealDetails.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;

import io.reactivex.rxjava3.core.Single;

public interface MealsDetailsPresenterInterface {
    Single<RootMeal> getMeal(String id);


    void addToWeekPlanner(MealPlanner mealPlanner, OnAddingListener onAddingListener);

    public void addFavMeal(Meal meal , OnAddingListener onAddingListener);
    public LiveData<Meal> getMealFromFavById(String id);

    LiveData<MealPlanner> getMealFromWeekPlanFavById(String id);
}
