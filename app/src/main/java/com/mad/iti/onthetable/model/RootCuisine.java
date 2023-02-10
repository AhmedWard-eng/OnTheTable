package com.mad.iti.onthetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootCuisine {
    @SerializedName("meals")
    public ArrayList<Cuisine> cuisines;

    public RootCuisine(){

    }

    public ArrayList<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }
}
