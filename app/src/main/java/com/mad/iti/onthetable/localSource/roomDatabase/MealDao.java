package com.mad.iti.onthetable.localSource.roomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;

import java.util.List;

@Dao
public interface MealDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<Meal>> getAllFavoriteMeals();



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFavorite(Meal meal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertAllMealsToFavorite(List<Meal> meals);

    @Query("SELECT * FROM favorite where idMeal =:idMeal")
    LiveData<Meal> getMealFromFavById(String idMeal);

    @Delete
    void deleteMealFromFavorite(Meal product);

    @Query("delete From favorite")
    void deleteAllFav();

    @Query("delete From weekPlan")
    void deleteAllWeekPlan();

    @Query("select * from weekPlan  where date = :date")
    LiveData<List<MealPlanner>> getAllMealsFromPlannerAtDate(String date);

    @Query("delete from weekPlan where date = :date & idMeal = :mealId")
    void deleteMealFromPlanner(String date, String mealId);



    @Query("SELECT * FROM weekPlan where id =:id")
    LiveData<MealPlanner> getMealFromWeekPlanById(String id);
    @Delete
    void deleteMealFromPlanner(MealPlanner mealPlanner);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertMealIntoPlanner(MealPlanner mealPlanner);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertAllMealsIntoPlanner(List<MealPlanner> mealPlanners);

}
