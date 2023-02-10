package com.mad.iti.onthetable.model;

import java.util.ArrayList;

public class GetArrayFromMeal {


    public static ArrayList<IngredientWithMeasure> getArrayList(Meal meal) {
        ArrayList<IngredientWithMeasure> list = new ArrayList<>();
        makeArrayFromMeal(meal, list);
        return list;
    }

    private static void makeArrayFromMeal(Meal meal, ArrayList<IngredientWithMeasure> list) {
        addToArray(meal.strIngredient1,meal.strMeasure1,list);
        addToArray(meal.strIngredient2,meal.strMeasure2,list);
        addToArray(meal.strIngredient3,meal.strMeasure3,list);
        addToArray(meal.strIngredient4,meal.strMeasure4,list);
        addToArray(meal.strIngredient5,meal.strMeasure5,list);
        addToArray(meal.strIngredient6,meal.strMeasure6,list);
        addToArray(meal.strIngredient7,meal.strMeasure7,list);
        addToArray(meal.strIngredient8,meal.strMeasure8,list);
        addToArray(meal.strIngredient9,meal.strMeasure9,list);
        addToArray(meal.strIngredient10,meal.strMeasure10,list);
        addToArray(meal.strIngredient11,meal.strMeasure11,list);
        addToArray(meal.strIngredient12,meal.strMeasure12,list);
        addToArray(meal.strIngredient13,meal.strMeasure13,list);
        addToArray(meal.strIngredient14,meal.strMeasure14,list);
        addToArray(meal.strIngredient15,meal.strMeasure15,list);
        addToArray(meal.strIngredient16,meal.strMeasure16,list);
        addToArray(meal.strIngredient17,meal.strMeasure17,list);
        addToArray(meal.strIngredient18,meal.strMeasure18,list);
        addToArray(meal.strIngredient19,meal.strMeasure19,list);
        addToArray(meal.strIngredient20,meal.strMeasure20,list);
        }

    private static void addToArray(String strIngredient, String strMeasure, ArrayList<IngredientWithMeasure> list) {
        if (strIngredient != null && !strIngredient.isEmpty()) {
            String measure;
            if(strMeasure == null){
                measure = "";
            }else{
                measure = strMeasure;
            }

            list.add(new IngredientWithMeasure(strIngredient, measure));
        }
    }


}
