package com.mad.iti.onthetable.ui.home.presenter;

import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenterInterface {
    Single<RootMeal> getRandomMeal();

    Single<RootMeal> getYouMightLikeMeal();
}
