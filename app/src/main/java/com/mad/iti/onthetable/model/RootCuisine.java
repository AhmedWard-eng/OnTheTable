package com.mad.iti.onthetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootCuisine {
    @SerializedName("meals")
    public ArrayList<Cuisine> cuisines;
}
