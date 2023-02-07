package com.mad.iti.onthetable.remoteSource.remoteAPI;


import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface APIClientInterface {

    Single<RootIngredient> getIngredients();

    Single<RootMeal> getRandomMeal();

    Single<RootMeal> getYouMightLikeMeal();
    
}
