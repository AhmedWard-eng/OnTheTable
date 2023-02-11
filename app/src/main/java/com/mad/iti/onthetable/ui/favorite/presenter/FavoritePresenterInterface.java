package com.mad.iti.onthetable.ui.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.Meal;

import java.util.List;

public interface FavoritePresenterInterface {

    LiveData<List<Meal>> getFavoriteMeals();
    void deleteMealFromFav(Meal meal);
    void getMealDetailsById(String id);
}
