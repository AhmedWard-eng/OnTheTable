package com.mad.iti.onthetable.model.repositories.dataRepo;

import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseFavDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBasePlannerDelegate;

public interface FavAndWeekPlanInterface {

    public void getWeekPlanner(FireBasePlannerDelegate fireBasePlannerDelegate);
    public void getFavMeals(FireBaseFavDelegate fireBaseFavDelegate);
}
