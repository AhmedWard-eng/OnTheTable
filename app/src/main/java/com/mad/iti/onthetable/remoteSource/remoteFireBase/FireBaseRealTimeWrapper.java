package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;

import java.util.ArrayList;

public class FireBaseRealTimeWrapper {
    private static final String TAG = "FavAndWeekPlanRepo";
    private FirebaseDatabase database;
    private DatabaseReference referenceWeekPlanner;
    private DatabaseReference referenceFavorite;
    private AuthenticationFireBaseRepo authenticationFireBaseRepo;
    private static FireBaseRealTimeWrapper fireBaseRealTimeWrapper;

//    public static synchronized FireBaseRealTimeWrapper getInstance() {
//        if (fireBaseRealTimeWrapper == null) {
//            fireBaseRealTimeWrapper = new FireBaseRealTimeWrapper();
//        }
//        return fireBaseRealTimeWrapper;
//    }

    public FireBaseRealTimeWrapper() {
        this.database = FirebaseDatabase.getInstance();
        authenticationFireBaseRepo = AuthenticationFireBaseRepo.getInstance();
        if (authenticationFireBaseRepo.isAuthenticated()) {
            this.referenceFavorite = database.getReference().child("users").child(authenticationFireBaseRepo.getUser().getUid()).child("favoritesMeal");
            this.referenceWeekPlanner = database.getReference().child("users").child(authenticationFireBaseRepo.getUser().getUid()).child("weekPlannerMeal");
        }
    }

    public void addToFav(Meal meal, FireBaseAddingDelegate fireBaseAddingDelegate) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            referenceFavorite.child(meal.idMeal).setValue(meal)
                    .addOnCompleteListener(task -> fireBaseAddingDelegate.onSuccess())
                    .addOnFailureListener(e -> fireBaseAddingDelegate.onFailure(e.toString()));
        }
    }

    public void addToWeekPlanner(MealPlanner mealPlanner, FireBaseAddingDelegate fireBaseAddingDelegate) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            String key = referenceWeekPlanner.push().getKey();
            referenceWeekPlanner.child(mealPlanner.id).setValue(mealPlanner).addOnCompleteListener(task -> fireBaseAddingDelegate.onSuccess()).addOnFailureListener(e -> fireBaseAddingDelegate.onFailure(e.toString()));

        }
    }

    public void removeMealFromFav(String mealId, FireBaseRemovingDelegate fireBaseRemovingDelegate) {
        referenceFavorite.child(mealId).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fireBaseRemovingDelegate.onSuccess();
                    }
                }).start();
                //fireBaseRemovingDelegate.onSuccess();
            }
        });
    }


    public void removeMealFromPlanner(String id, FireBaseRemovingDelegate fireBaseRemovingDelegate) {
        referenceWeekPlanner.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                fireBaseRemovingDelegate.onSuccess();
            }
        });
    }


    public void getFavMeals(FireBaseFavDelegate fireBaseFavDelegate) {
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
        Log.d(TAG, "getFavMeals: " + database.getReference().child("users").child(authenticationFireBaseRepo.getUser().getUid()).child("favoritesMeal").getKey());
        referenceFavorite.addValueEventListener(postListener);
    }

    public void getWeekPlanner(FireBasePlannerDelegate fireBasePlannerDelegate) {
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
