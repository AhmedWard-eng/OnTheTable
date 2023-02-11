package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.mad.iti.onthetable.model.MealPlanner;

import java.util.List;

public interface FireBasePlannerDelegate {


    public void onSuccess(List<MealPlanner> mealPlanners);

    public void onFailure(String message);
}
