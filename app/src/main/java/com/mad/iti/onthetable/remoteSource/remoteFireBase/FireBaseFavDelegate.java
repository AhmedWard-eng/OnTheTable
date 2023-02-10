package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.mad.iti.onthetable.model.Meal;

import java.util.List;

public interface FireBaseFavDelegate {

    public void onSuccess(List<Meal> meals);

    public void onFailure(String message);

}
