package com.mad.iti.onthetable.localSource.roomDatabase;

import androidx.lifecycle.LiveData;
import com.mad.iti.onthetable.model.Meal;

import java.util.List;

public interface LocalSource {

    LiveData<List<Meal>> getAllFavoriteMeals();

    void insertMealToFavorite(Meal meal);

    void deleteMealFromFavorite(Meal product);
}
