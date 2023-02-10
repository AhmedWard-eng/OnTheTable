package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class SearchByNamePresenter implements SearchByNamePresenterInterface{
    private static SearchByNamePresenter searchByNamePresenter;
    private final MealsRepoInterface mealsRepo;

    public static synchronized SearchByNamePresenter getInstance(MealsRepoInterface mealsRepo){
        if(searchByNamePresenter == null){
            searchByNamePresenter = new SearchByNamePresenter(mealsRepo);
        }
        return searchByNamePresenter;
    }

    private  SearchByNamePresenter(MealsRepoInterface mealsRepo){
        this.mealsRepo = mealsRepo;
    }


    @Override
    public Single<RootMeal> searchMealByName(String name) {
        return mealsRepo.searchMealByName(name);
    }

    @Override
    public Single<RootMeal> getMealById(String id) {
        return mealsRepo.getMealById(id);
    }
}
