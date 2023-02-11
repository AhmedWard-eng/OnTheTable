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
                    new Thread(() -> dataBase.mealDao().InsertAllMealsToFavorite(meals)).start();
                    Log.d(TAG, "onSuccess: ");
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataBase.mealDao().insertMealToFavorite(meal);

                    }
                }).start();
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
                new Thread(() -> dataBase.mealDao().insertMealIntoPlanner(mealPlanner)).start();
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
        firebase.removeMealFromPlanner(mealPlanner.id, () -> new Thread(() -> dataBase.mealDao().deleteMealFromPlanner(mealPlanner)).start());
    }

    @Override
    public void deleteMealFromFavorites(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebase.removeMealFromFav(meal.idMeal, () -> dataBase.mealDao().deleteMealFromFavorite(meal));
            }
        }).start();

    }

    @Override
    public void deleteAllFav() {
        new Thread(() -> {
            dataBase.mealDao().deleteAllFav();
        }).start();
    }

    @Override
    public void deleteAllWeekPlan() {
        new Thread(() -> {
            dataBase.mealDao().deleteAllWeekPlan();
        }).start();
    }

    @Override
    public LiveData<Meal> getMealById(String idMeal) {
        return dataBase.mealDao().getMealById(idMeal);
    }

}
