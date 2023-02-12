package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;

import io.reactivex.rxjava3.core.Single;

public interface SearchByNamePresenterInterface {

    public Single<RootMeal> searchMealByName(String name);

    public Single<RootMeal> randomMeals();

    public Single<RootMeal> getMealById(String id);

    void addMealToPlan(MealPlanner mealPlanner, OnAddingListener onAddingListener);
}
