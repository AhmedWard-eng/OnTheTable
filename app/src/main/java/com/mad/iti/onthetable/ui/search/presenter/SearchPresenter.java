package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMealPreview;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class SearchPresenter implements SearchPresenterInterface{

    private static SearchPresenter searchPresenter;
    private MealsRepoInterface mealsRepo;

    public static synchronized SearchPresenter getInstance(MealsRepoInterface mealsRepo){
        if(searchPresenter == null){
            searchPresenter = new SearchPresenter(mealsRepo);
        }
        return searchPresenter;
    }

    public SearchPresenter(MealsRepoInterface mealsRepo){
        this.mealsRepo = mealsRepo;
    }

    @Override
    public Single<RootIngredient> getIngredients() {
        return mealsRepo.getRootIngredientObservable();
    }

    @Override
    public Single<RootCategory> getCategory() {
        return mealsRepo.getRootCategoryObservable();
    }

    @Override
    public Single<RootCuisine> getCuisines() {
        return mealsRepo.getRootCuisineObservable();
    }

    @Override
    public Single<RootMealPreview> getMealByIngredient(String id) {
        return mealsRepo.getRootPreviewMealByIngredient(id);
    }

    @Override
    public Single<RootMealPreview> getMealByCountry(String id) {
        return mealsRepo.getRootPreviewMealByCountry(id);
    }

    @Override
    public Single<RootMealPreview> getMealByCategory(String id) {
        return mealsRepo.getRootPreviewMealByCategory(id);
    }
}
