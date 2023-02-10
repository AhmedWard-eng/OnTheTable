package com.mad.iti.onthetable.model.repositories.dataRepo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.localSource.roomDatabase.AppDataBase;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseAddingDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseFavDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBasePlannerDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseRealTimeWrapper;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseRemovingDelegate;

import java.util.List;

public class FavAndWeekPlanRepo implements FavAndWeekPlanInterface {

    private static final String TAG = "FavAndWeekPlanRepo";

    private AppDataBase dataBase;
    private FireBaseRealTimeWrapper firebase;
    private static FavAndWeekPlanRepo repo;

    public static synchronized FavAndWeekPlanRepo getInstance(Context context) {
        if (repo == null) {
            repo = new FavAndWeekPlanRepo(context);
        }
        return repo;
    }

    private FavAndWeekPlanRepo(Context context) {
        firebase = FireBaseRealTimeWrapper.getInstance();
        dataBase = AppDataBase.getInstance(context);
    }

    @Override
    public void refreshPlanner() {

        firebase.getWeekPlanner(new FireBasePlannerDelegate() {
            @Override
            public void onSuccess(List<MealPlanner> mealPlanners) {
                new Thread(() -> {
                    dataBase.mealDao().InsertAllMealsIntoPlanner(mealPlanners);
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public void refreshMeals() {
        firebase.getFavMeals(new FireBaseFavDelegate() {
            @Override
            public void onSuccess(List<Meal> meals) {
                new Thread(() -> {
                    dataBase.mealDao().InsertAllMealsToFavorite(meals);
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public LiveData<List<Meal>> getFavMealsLiveData() {
        return dataBase.mealDao().getAllFavoriteMeals();
    }

    @Override
    public LiveData<List<MealPlanner>> getAllMealsFromPlannerAtDate(String date) {
        return dataBase.mealDao().getAllMealsFromPlannerAtDate(date);

    }

    @Override
    public void addToFavorites(Meal meal, OnAddingListener onAddingListener) {
        firebase.addToFav(meal, new FireBaseAddingDelegate() {
            @Override
            public void onSuccess() {
                dataBase.mealDao().insertMealToFavorite(meal);
                onAddingListener.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                onAddingListener.onFailure(message);
            }
        });
    }

    @Override
    public void addToPlanner(MealPlanner mealPlanner, OnAddingListener onAddingListener) {
        firebase.addToWeekPlanner(mealPlanner, new FireBaseAddingDelegate() {
            @Override
            public void onSuccess() {
                dataBase.mealDao().insertMealIntoPlanner(mealPlanner);
                onAddingListener.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                onAddingListener.onFailure(message);
            }
        });
    }

    @Override
    public void deleteMealFromPlanner(MealPlanner mealPlanner) {
        firebase.removeMealFromPlanner(mealPlanner.id, () -> dataBase.mealDao().deleteMealFromPlanner(mealPlanner));
    }

    @Override
    public void deleteMealFromFavorites(Meal meal) {
        firebase.removeMealFromFav(meal.idMeal, () -> dataBase.mealDao().deleteMealFromFavorite(meal));
    }

    @Override
    public void deleteAllFav() {
        dataBase.mealDao().deleteAllFav();
    }

    @Override
    public void deleteAllWeekPlan() {
        dataBase.mealDao().deleteAllWeekPlan();
    }

}
