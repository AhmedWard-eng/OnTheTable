package com.mad.iti.onthetable.ui.mealDetails.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanInterface;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;

import io.reactivex.rxjava3.core.Single;

public class MealsDetailsFragmentPresenter implements MealsDetailsPresenterInterface  {

    private static MealsDetailsFragmentPresenter mealsDetailsFragmentPresenter;
    private MealsRepoInterface mealsRepo;
    private FavAndWeekPlanInterface dataBaseRepo;
    //OnAddingListener onAddingListener;
    public static synchronized MealsDetailsFragmentPresenter getInstance(MealsRepoInterface mealsRepo,FavAndWeekPlanInterface dataBaseRepo){
        if(mealsDetailsFragmentPresenter == null){
            mealsDetailsFragmentPresenter = new MealsDetailsFragmentPresenter(mealsRepo , dataBaseRepo);
        }
        return mealsDetailsFragmentPresenter;
    }

    private MealsDetailsFragmentPresenter(MealsRepoInterface mealsRepo ,FavAndWeekPlanInterface dataBaseRepo){
        this.mealsRepo = mealsRepo;
        this.dataBaseRepo = dataBaseRepo;
    }


    @Override
    public Single<RootMeal> getMeal(String id) {
        return mealsRepo.getMealById(id);
    }

    @Override
    public void addFavMeal(Meal meal , OnAddingListener onAddingListener) {
        dataBaseRepo.addToFavorites(meal , onAddingListener);
    }

    @Override
    public void addToWeekPlanner(MealPlanner mealPlanner, OnAddingListener onAddingListener) {
        dataBaseRepo.addToPlanner(mealPlanner,onAddingListener);
    }

    @Override
    public LiveData<Meal> getMealFromFavById(String id){
        return dataBaseRepo.getMealFromFavById(id);
    }

    @Override
    public LiveData<MealPlanner> getMealFromWeekPlanFavById(String id){
        return dataBaseRepo.getMealFromWeekPlanById(id);
    }


}
