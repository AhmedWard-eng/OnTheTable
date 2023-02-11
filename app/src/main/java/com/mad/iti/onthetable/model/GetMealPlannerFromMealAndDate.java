package com.mad.iti.onthetable.model;

public class GetMealPlannerFromMealAndDate {
    public static MealPlanner getMealPlanner(Meal meal, String date, int dayOfWeek) {
        MealPlanner mealPlanner = new MealPlanner();
        mealPlanner.idMeal = meal.idMeal;
        mealPlanner.date = date;
        mealPlanner.strMeal = meal.strMeal;
        mealPlanner.dayOfWeek = dayOfWeek;
        mealPlanner.strCategory = meal.strCategory;
        mealPlanner.strArea = meal.strArea;
        mealPlanner.strInstructions = meal.strInstructions;
        mealPlanner.strMealThumb = meal.strMealThumb;
        mealPlanner.strTags = meal.strTags;
        mealPlanner.strYoutube = meal.strYoutube;
        mealPlanner.strIngredient1 = meal.strIngredient1;
        mealPlanner.strIngredient2 = meal.strIngredient2;
        mealPlanner.strIngredient3 = meal.strIngredient3;
        mealPlanner.strIngredient4 = meal.strIngredient4;
        mealPlanner.strIngredient5 = meal.strIngredient5;
        mealPlanner.strIngredient6 = meal.strIngredient6;
        mealPlanner.strIngredient7 = meal.strIngredient7;
        mealPlanner.strIngredient8 = meal.strIngredient8;
        mealPlanner.strIngredient9 = meal.strIngredient9;
        mealPlanner.strIngredient10 = meal.strIngredient10;
        mealPlanner.strIngredient11 = meal.strIngredient11;
        mealPlanner.strIngredient12 = meal.strIngredient12;
        mealPlanner.strIngredient13 = meal.strIngredient13;
        mealPlanner.strIngredient14 = meal.strIngredient14;
        mealPlanner.strIngredient15 = meal.strIngredient15;
        mealPlanner.strIngredient16 = meal.strIngredient16;
        mealPlanner.strIngredient17 = meal.strIngredient17;
        mealPlanner.strIngredient18 = meal.strIngredient18;
        mealPlanner.strIngredient19 = meal.strIngredient19;
        mealPlanner.strIngredient20 = meal.strIngredient20;
        mealPlanner.strMeasure1 = meal.strMeasure1;
        mealPlanner.strMeasure2 = meal.strMeasure2;
        mealPlanner.strMeasure3 = meal.strMeasure3;
        mealPlanner.strMeasure4 = meal.strMeasure4;
        mealPlanner.strMeasure5 = meal.strMeasure5;
        mealPlanner.strMeasure6 = meal.strMeasure6;
        mealPlanner.strMeasure7 = meal.strMeasure7;
        mealPlanner.strMeasure8 = meal.strMeasure8;
        mealPlanner.strMeasure9 = meal.strMeasure9;
        mealPlanner.strMeasure10 = meal.strMeasure10;
        mealPlanner.strMeasure11 = meal.strMeasure11;
        mealPlanner.strMeasure12 = meal.strMeasure12;
        mealPlanner.strMeasure13 = meal.strMeasure13;
        mealPlanner.strMeasure14 = meal.strMeasure14;
        mealPlanner.strMeasure15 = meal.strMeasure15;
        mealPlanner.strMeasure16 = meal.strMeasure16;
        mealPlanner.strMeasure17 = meal.strMeasure17;
        mealPlanner.strMeasure18 = meal.strMeasure18;
        mealPlanner.strMeasure19 = meal.strMeasure19;
        mealPlanner.strMeasure20 = meal.strMeasure20;
        mealPlanner.id = date + "-" + meal.idMeal;
        return mealPlanner;
    }
}
