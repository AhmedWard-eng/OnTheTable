package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface SearchByNamePresenterInterface {

    public Single<RootMeal> searchMealByName(String name);

    public Single<RootMeal> getMealById(String id);
}
