package com.mad.iti.onthetable.remoteSource.remoteAPI;


import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;

public interface APIClientInterface {

    Single<RootIngredient> getIngredients();

    Single<RootMeal> getRandomMeal();

    Single<RootMeal> getYouMightLikeMeal();

    Single<RootMeal> searchMealByName(String name);

    Single<RootMeal> getMealById(String id);

}
