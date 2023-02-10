package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;

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





}
