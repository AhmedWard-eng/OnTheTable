package com.mad.iti.onthetable.ui.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepoInterface;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;

import java.util.List;

public class FavoritePresenter implements FavoritePresenterInterface{
    private static FavoritePresenter favoritePresenter;
    private FavAndWeekPlanRepo favRepo;

    public static synchronized FavoritePresenter getInstance(FavAndWeekPlanRepo favRepo){
        if(favoritePresenter == null){
            favoritePresenter = new FavoritePresenter(favRepo);
        }
        return favoritePresenter;
    }

    public FavoritePresenter(FavAndWeekPlanRepo favRepo){
        this.favRepo = favRepo;
    }

    @Override
    public LiveData<List<Meal>> getFavoriteMeals() {
        return favRepo.getFavMealsLiveData();
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        favRepo.deleteMealFromFavorites(meal);

    }

    @Override
    public void getMealDetailsById(String id) {

    }
}
