package com.mad.iti.onthetable.remoteSource.remoteAPI;

import android.util.Log;

import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;

import java.util.List;
import java.util.Random;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements APIClientInterface {
    // TODO: 2/3/2023 singleton

    private static final String TAG = "MealsRepo";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static RetrofitClient retrofitClient;
    private RetrofitMealsAPI retrofitMealsAPI;

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava3CallAdapterFactory.create()).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        retrofitMealsAPI = retrofit.create(RetrofitMealsAPI.class);
    }


    @Override
    public Single<RootIngredient> getIngredients() {

        Log.i(TAG, "Observer CallAPi: ");
        return retrofitMealsAPI.getIngredients();
    }

    @Override
    public Single<RootMeal> getRandomMeal() {
        Log.d(TAG, "getRandomMeal: ");
        return retrofitMealsAPI.getRandomMeal();
    }

    @Override
    public Single<RootMeal> getYouMightLikeMeal() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'A');
        return retrofitMealsAPI.searchByName(String.valueOf(c));
    }
}
