package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.RootMealPreview;

import io.reactivex.rxjava3.core.Single;

public interface SearchPresenterInterface {

    Single<RootIngredient> getIngredients();
    Single<RootCategory> getCategory();
    Single<RootCuisine> getCuisines();

    Single<RootMealPreview> getMealByIngredient(String id);
    Single<RootMealPreview> getMealByCountry(String id);
    Single<RootMealPreview> getMealByCategory(String id);
}
