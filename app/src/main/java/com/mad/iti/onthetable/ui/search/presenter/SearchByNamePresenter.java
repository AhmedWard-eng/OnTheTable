package com.mad.iti.onthetable.ui.search.presenter;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanInterface;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class SearchByNamePresenter implements SearchByNamePresenterInterface {
    private static SearchByNamePresenter searchByNamePresenter;
    private final MealsRepoInterface mealsRepo;

    FavAndWeekPlanInterface favAndWeekPlanRepo;

    public static synchronized SearchByNamePresenter getInstance(MealsRepoInterface mealsRepo, FavAndWeekPlanInterface favAndWeekPlanRepo) {
        if (searchByNamePresenter == null) {
            searchByNamePresenter = new SearchByNamePresenter(mealsRepo, favAndWeekPlanRepo);
        }
        return searchByNamePresenter;
    }

    private SearchByNamePresenter(MealsRepoInterface mealsRepo, FavAndWeekPlanInterface favAndWeekPlanRepo) {
        this.mealsRepo = mealsRepo;
        this.favAndWeekPlanRepo = favAndWeekPlanRepo;
    }


    @Override
    public Single<RootMeal> searchMealByName(String name) {
        return mealsRepo.searchMealByName(name);
    }

    @Override
    public Single<RootMeal> randomMeals() {
        return mealsRepo.getYouMightLikeMealsObservable();
    }

    @Override
    public Single<RootMeal> getMealById(String id) {
        return mealsRepo.getMealById(id);
    }

    @Override
    public void addMealToPlan(MealPlanner mealPlanner, OnAddingListener onAddingListener) {
        favAndWeekPlanRepo.addToPlanner(mealPlanner, onAddingListener);

    }
}
