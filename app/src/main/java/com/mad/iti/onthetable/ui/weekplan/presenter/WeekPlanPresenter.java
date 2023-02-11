package com.mad.iti.onthetable.ui.weekplan.presenter;

import androidx.lifecycle.LiveData;

import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.ui.weekplan.view.OnRemoveClickListener;

import java.util.List;

public class WeekPlanPresenter implements WeekPlanPresenterInterface{
    private FavAndWeekPlanRepo favAndWeekPlanRepoRepo;

    private static WeekPlanPresenter weekPlanPresenter;


    public static synchronized WeekPlanPresenter getInstance(FavAndWeekPlanRepo favAndWeekPlanRepoRepo){
        if(weekPlanPresenter == null){
            weekPlanPresenter = new WeekPlanPresenter(favAndWeekPlanRepoRepo);
        }
        return weekPlanPresenter;
    }

    private WeekPlanPresenter(FavAndWeekPlanRepo favAndWeekPlanRepoRepo) {
        this.favAndWeekPlanRepoRepo = favAndWeekPlanRepoRepo;
    }

    @Override
    public LiveData<List<MealPlanner>> getMealPlannerByDate(String date) {
        return favAndWeekPlanRepoRepo.getAllMealsFromPlannerAtDate(date);
    }

    @Override
    public void removeItemFromPlanner(MealPlanner mealPlanner) {
        favAndWeekPlanRepoRepo.deleteMealFromPlanner(mealPlanner);
    }
}
