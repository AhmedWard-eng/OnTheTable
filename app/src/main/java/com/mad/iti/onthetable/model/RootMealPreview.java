package com.mad.iti.onthetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootMealPreview {
    @SerializedName("meals")
    public ArrayList<MealPreview> mealPreviews;
}
