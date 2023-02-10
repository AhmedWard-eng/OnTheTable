package com.mad.iti.onthetable.ui.mealDetails.presenter;

import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class MealsDetailsFragmentPresenter implements MealsDetailsPresenterInterface{

    private static MealsDetailsFragmentPresenter mealsDetailsFragmentPresenter;
    private MealsRepoInterface mealsRepo;
    public static synchronized MealsDetailsFragmentPresenter getInstance(MealsRepoInterface mealsRepo){
        if(mealsDetailsFragmentPresenter == null){
            mealsDetailsFragmentPresenter = new MealsDetailsFragmentPresenter(mealsRepo);
        }
        return mealsDetailsFragmentPresenter;
    }

    private MealsDetailsFragmentPresenter(MealsRepoInterface mealsRepo){
        this.mealsRepo = mealsRepo;
    }


    @Override
    public Single<RootMeal> getMeal(String id) {
        return mealsRepo.getMealById(id);
    }
}
