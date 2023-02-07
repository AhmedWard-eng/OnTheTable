package com.mad.iti.onthetable.model.repositories;

import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.remoteSource.remoteAPI.APIClientInterface;
import com.mad.iti.onthetable.remoteSource.remoteAPI.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepo implements MealsRepoInterface{
    private static final String TAG = "MealsRepo";
    private APIClientInterface retrofitClient;
    private Single<RootIngredient> rootIngredientSingle;

    private Single<RootMeal> youMightLikeMealsSingle;


    private Single<RootMeal> randomMealSingle;

    private static MealsRepo instance;

    private MealsRepo() {
        this.retrofitClient = RetrofitClient.getInstance();

    }

    public static synchronized MealsRepo getInstance() {
        if (instance == null) {
            instance = new MealsRepo();
        }
        return instance;
    }

    public Single<RootIngredient> getRootIngredientObservable() {
        if (rootIngredientSingle == null) {
            rootIngredientSingle = retrofitClient.getIngredients().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootIngredientSingle;
    }

    public Single<RootMeal> getYouMightLikeMealsObservable() {
        if (youMightLikeMealsSingle == null) {
            youMightLikeMealsSingle = retrofitClient.getYouMightLikeMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return youMightLikeMealsSingle;
    }

    public Single<RootMeal> getRandomMealObservable() {
        if (randomMealSingle == null) {
            randomMealSingle = retrofitClient.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return randomMealSingle;
    }


}
