package com.mad.iti.onthetable.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.authentication.AuthenticationActivity;
import com.mad.iti.onthetable.ui.startPart.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private static final String TAG = "SplashActivity";

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPreferences = getSharedPreferences("mySp", MODE_PRIVATE);
                boolean isFirstTime = mSharedPreferences.getBoolean("firstTime", true);
                //// TODO: 2/7/2023   make sharedPrefToShowIfIsAuth
                boolean isAuthenticated = false;
                if (isFirstTime) {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("firstTime", false);
                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME_OUT);

        MealsRepo mealsRepo = MealsRepo.getInstance();
        mealsRepo.getRandomMealObservable();
        mealsRepo.getRootIngredientObservable();
        mealsRepo.getYouMightLikeMealsObservable();
    }
}