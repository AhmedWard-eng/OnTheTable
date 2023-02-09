package com.mad.iti.onthetable.remoteSource.remoteAPI;

import com.mad.iti.onthetable.model.Category;
import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPreview;
import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.RootMealPreview;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitMealsAPI {



    @GET("list.php?i=list")
    public Single<RootIngredient> getIngredients();
    @GET("categories.php")
    public Single<RootCategory> getCategories();
    @GET("list.php?a=list")
    public Single<RootCuisine> getCuisines();



    @GET("filter.php")
    public Single<RootMealPreview> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    public Single<RootMealPreview> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    public Single<RootMealPreview> getMealsByCuisine(@Query("a") String cuisine);

    @GET("search.php")
    public Single<RootMeal>searchByName(@Query("s") String mealName);
    @GET("lookup.php")
    public Single<RootMeal> getMealById(@Query("i") String id);
    @GET("random.php")
    public Single<RootMeal> getRandomMeal();


}
