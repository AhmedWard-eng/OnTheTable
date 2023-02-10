package com.mad.iti.onthetable.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Meal {
    @PrimaryKey
    public String idMeal;
    public String strMeal;

    @Ignore
    public Object strDrinkAlternate;
    public String strCategory;
    public String strArea;
    public String strInstructions;
    public String strMealThumb;
    public Object strTags;
    public String strYoutube;
    public String strIngredient1;
    public String strIngredient2;
    public String strIngredient3;
    public String strIngredient4;
    public String strIngredient5;
    public String strIngredient6;
    public String strIngredient7;
    public String strIngredient8;
    public String strIngredient9;
    public String strIngredient10;
    public String strIngredient11;
    public String strIngredient12;
    public String strIngredient13;
    public String strIngredient14;
    public String strIngredient15;
    public String strIngredient16;
    public String strIngredient17;
    public String strIngredient18;
    public String strIngredient19;
    public String strIngredient20;
    public String strMeasure1;
    public String strMeasure2;
    public String strMeasure3;
    public String strMeasure4;
    public String strMeasure5;
    public String strMeasure6;
    public String strMeasure7;
    public String strMeasure8;
    public String strMeasure9;
    public String strMeasure10;
    public String strMeasure11;
    public String strMeasure12;
    public String strMeasure13;
    public String strMeasure14;
    public String strMeasure15;
    public String strMeasure16;
    public String strMeasure17;
    public String strMeasure18;
    public String strMeasure19;
    public String strMeasure20;
    @Ignore
    public String strSource;

    @Ignore
    public Object strImageSource;

    @Ignore
    public Object strCreativeCommonsConfirmed;

    @Ignore
    public Object dateModified;
}
