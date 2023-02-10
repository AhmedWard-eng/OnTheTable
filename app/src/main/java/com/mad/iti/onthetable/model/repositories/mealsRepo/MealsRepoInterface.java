package com.mad.iti.onthetable.model.repositories.mealsRepo;

import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.RootMealPreview;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealsRepoInterface {

    public Single<RootIngredient> getRootIngredientObservable();
    public Single<RootCategory> getRootCategoryObservable();
    public Single<RootCuisine> getRootCuisineObservable();

    Single<RootMealPreview> getRootPreviewMealByIngredient(String id);
    Single<RootMealPreview> getRootPreviewMealByCategory(String id);
    Single<RootMealPreview> getRootPreviewMealByCountry(String id);

    public Single<RootMeal> getYouMightLikeMealsObservable();

    public Single<RootMeal> getRandomMealObservable();
    public Single<RootMeal> searchMealByName(String name);

    public Single<RootMeal> getMealById(String id);
}
