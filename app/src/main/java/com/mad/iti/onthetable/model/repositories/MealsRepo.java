package com.mad.iti.onthetable.model.repositories;

import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.remoteSource.remoteAPI.APIClientInterface;
import com.mad.iti.onthetable.remoteSource.remoteAPI.RetrofitClient;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepo implements MealsRepoInterface {
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

            youMightLikeMealsSingle = retrofitClient.getYouMightLikeMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return youMightLikeMealsSingle;
    }

    public Single<RootMeal> getRandomMealObservable() {
        return randomMealSingle;
    }

    public void enqueueRandomMealCall(){
        randomMealSingle = retrofitClient.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



    public Single<RootMeal> searchMealByName(String name) {
        return retrofitClient.searchMealByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<RootMeal> getMealById(String id) {
        return retrofitClient.getMealById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
