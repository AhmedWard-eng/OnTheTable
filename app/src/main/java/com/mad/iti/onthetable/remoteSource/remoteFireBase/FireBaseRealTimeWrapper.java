package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;

import java.util.ArrayList;
import java.util.Iterator;

public class FireBaseRealTimeWrapper {

    private FirebaseDatabase database;
    private DatabaseReference referenceWeekPlanner;
    private DatabaseReference referenceFavorite;
    private AuthenticationFireBaseRepo authenticationFireBaseRepo;
    private static FireBaseRealTimeWrapper fireBaseRealTimeWrapper;

    public static synchronized FireBaseRealTimeWrapper getInstance() {
        if (fireBaseRealTimeWrapper == null) {
            fireBaseRealTimeWrapper = new FireBaseRealTimeWrapper();
        }
        return fireBaseRealTimeWrapper;
    }

    private FireBaseRealTimeWrapper() {
        this.database = FirebaseDatabase.getInstance();
        authenticationFireBaseRepo = AuthenticationFireBaseRepo.getInstance();
        if(authenticationFireBaseRepo.isAuthenticated()){
            this.referenceFavorite = database.getReference().child("users").child(authenticationFireBaseRepo.getUser().getUid()).child("favoritesMeal");
            this.referenceWeekPlanner = database.getReference().child("users").child(authenticationFireBaseRepo.getUser().getUid()).child("weekPlannerMeal");
        }
    }

    public boolean addToFav(Meal meal) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            String key = referenceFavorite.push().getKey();
            referenceFavorite.child(key).setValue(meal);
            return true;
        } else {
            return false;
        }
    }
    public boolean addToWeekPlanner(MealPlanner mealPlanner) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            String key = referenceFavorite.push().getKey();
            referenceFavorite.child(key).setValue(mealPlanner);
            return true;
        } else {
            return false;
        }
    }

    public void getFavMeals(FireBaseFavDelegate fireBaseFavDelegate){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Meal> meals = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    meals.add(snapshot.getValue(Meal.class));
                }
                fireBaseFavDelegate.onSuccess(meals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireBaseFavDelegate.onFailure(databaseError.toException().toString());
            }
        };
        referenceFavorite.addValueEventListener(postListener);
    }

    public void getWeekPlanner(FireBasePlannerDelegate fireBasePlannerDelegate){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MealPlanner> mealPlanners = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mealPlanners.add(snapshot.getValue(MealPlanner.class));
                }
                fireBasePlannerDelegate.onSuccess(mealPlanners);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireBasePlannerDelegate.onFailure(databaseError.toException().toString());
            }
        };
        referenceWeekPlanner.addValueEventListener(postListener);
    }
}
