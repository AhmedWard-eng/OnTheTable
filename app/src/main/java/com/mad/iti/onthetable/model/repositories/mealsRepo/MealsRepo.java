package com.mad.iti.onthetable.model.repositories.mealsRepo;

import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.RootMealPreview;
import com.mad.iti.onthetable.remoteSource.remoteAPI.APIClientInterface;
import com.mad.iti.onthetable.remoteSource.remoteAPI.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepo implements MealsRepoInterface {
    private static final String TAG = "MealsRepo";
    private APIClientInterface retrofitClient;
    private Single<RootIngredient> rootIngredientSingle;
    private Single<RootCategory> rootCategorySingle;
    private Single<RootCuisine> rootCuisineSingle;
    private Single<RootMealPreview> rootMealPreviewCuisineSingle;
    private Single<RootMealPreview> rootMealPreviewIngredientSingle;
    private Single<RootMealPreview> rootMealPreviewCategorySingle;
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

    @Override
    public Single<RootCategory> getRootCategoryObservable() {
        if (rootCategorySingle == null) {
            rootCategorySingle = retrofitClient.getAllCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootCategorySingle;
    }

    @Override
    public Single<RootCuisine> getRootCuisineObservable() {
        if (rootCuisineSingle == null) {
            rootCuisineSingle = retrofitClient.getAllCuisines().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootCuisineSingle;
    }

    @Override
    public Single<RootMealPreview> getRootPreviewMealByIngredient(String id) {
        rootMealPreviewIngredientSingle = retrofitClient.getMealsByIngredient(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return rootMealPreviewIngredientSingle;
    }

    @Override
    public Single<RootMealPreview> getRootPreviewMealByCategory(String id) {
        rootMealPreviewCategorySingle = retrofitClient.getMealsByCategory(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return rootMealPreviewCategorySingle;
    }

    @Override
    public Single<RootMealPreview> getRootPreviewMealByCountry(String id) {
        rootMealPreviewCuisineSingle = retrofitClient.getMealsByCountry(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return rootMealPreviewCuisineSingle;
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

    @Override
    public Single<RootMeal> searchMealByName(String name) {
        return retrofitClient.searchMealByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<RootMeal> getMealById(String id) {
        return retrofitClient.getMealById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
