package com.mad.iti.onthetable.model.repositories;

import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealsRepoInterface {

    public Single<RootIngredient> getRootIngredientObservable();

    public Single<RootMeal> getYouMightLikeMealsObservable();

    public Single<RootMeal> getRandomMealObservable();
}
