package com.mad.iti.onthetable.ui.mealDetails.presenter;

import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface MealsDetailsPresenterInterface {
    Single<RootMeal> getMeal(String id);
}
