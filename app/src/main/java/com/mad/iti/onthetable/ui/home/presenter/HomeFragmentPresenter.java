package com.mad.iti.onthetable.ui.home.presenter;

import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.model.repositories.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class HomeFragmentPresenter implements HomePresenterInterface{

    private static HomeFragmentPresenter homeFragmentPresenter;
    private MealsRepoInterface mealsRepo;
    public static synchronized HomeFragmentPresenter getInstance(MealsRepoInterface mealsRepo){
        if(homeFragmentPresenter == null){
            homeFragmentPresenter = new HomeFragmentPresenter(mealsRepo);
        }
        return homeFragmentPresenter;
    }

    private  HomeFragmentPresenter(MealsRepoInterface mealsRepo){
        this.mealsRepo = mealsRepo;
    }

    @Override
    public Single<RootMeal> getRandomMeal() {
        return mealsRepo.getRandomMealObservable();
    }

    @Override
    public Single<RootMeal> getYouMightLikeMeal() {
        return mealsRepo.getYouMightLikeMealsObservable();
    }
}
