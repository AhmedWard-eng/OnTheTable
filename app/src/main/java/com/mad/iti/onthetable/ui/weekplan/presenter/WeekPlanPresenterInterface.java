package com.mad.iti.onthetable.ui.weekplan.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.ui.weekplan.view.OnRemoveClickListener;

import java.util.List;

public interface WeekPlanPresenterInterface {

    LiveData<List<MealPlanner>> getMealPlannerByDate(String date);

   void  removeItemFromPlanner(MealPlanner mealPlanner);
}
