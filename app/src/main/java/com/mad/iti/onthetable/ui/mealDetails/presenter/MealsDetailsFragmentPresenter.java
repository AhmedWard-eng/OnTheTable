package com.mad.iti.onthetable.ui.mealDetails.presenter;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanInterface;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class MealsDetailsFragmentPresenter implements MealsDetailsPresenterInterface{

    private static MealsDetailsFragmentPresenter mealsDetailsFragmentPresenter;
    private MealsRepoInterface mealsRepo;

    private FavAndWeekPlanInterface favAndWeekPlanRepo ;
    public static synchronized MealsDetailsFragmentPresenter getInstance(MealsRepoInterface mealsRepo, FavAndWeekPlanInterface favAndWeekPlanRepo){
        if(mealsDetailsFragmentPresenter == null){
            mealsDetailsFragmentPresenter = new MealsDetailsFragmentPresenter(mealsRepo,favAndWeekPlanRepo);
        }
        return mealsDetailsFragmentPresenter;
    }

    private MealsDetailsFragmentPresenter(MealsRepoInterface mealsRepo, FavAndWeekPlanInterface favAndWeekPlanRepo){
        this.mealsRepo = mealsRepo;
        this.favAndWeekPlanRepo = favAndWeekPlanRepo;
    }


    @Override
    public Single<RootMeal> getMeal(String id) {
        return mealsRepo.getMealById(id);
    }

    @Override
    public void addToWeekPlanner(MealPlanner mealPlanner, OnAddingListener onAddingListener) {
        favAndWeekPlanRepo.addToPlanner(mealPlanner,onAddingListener);
    }


}
