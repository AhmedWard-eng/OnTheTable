package com.mad.iti.onthetable.remoteSource.remoteAPI;


import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.RootMealPreview;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;

public interface APIClientInterface {

    Single<RootIngredient> getIngredients();
    Single<RootCategory> getAllCategories();
    Single<RootCuisine> getAllCuisines();

    Single<RootMealPreview> getMealsByIngredient(String id);
    Single<RootMealPreview> getMealsByCategory(String id);
    Single<RootMealPreview> getMealsByCountry(String id);


    Single<RootMeal> getRandomMeal();

    Single<RootMeal> getYouMightLikeMeal();

    Single<RootMeal> searchMealByName(String name);

    Single<RootMeal> getMealById(String id);

}
