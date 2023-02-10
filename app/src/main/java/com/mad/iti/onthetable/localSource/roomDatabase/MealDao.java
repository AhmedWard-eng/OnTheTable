package com.mad.iti.onthetable.localSource.roomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mad.iti.onthetable.model.Meal;

import java.util.List;
@Dao
public interface MealDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<Meal>> getAllFavoriteMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFavorite(Meal meal);

    @Delete
    void deleteMealFromFavorite(Meal product);


}
