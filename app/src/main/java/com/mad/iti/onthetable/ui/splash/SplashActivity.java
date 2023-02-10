package com.mad.iti.onthetable.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.registeration.RegsiterationActivity;
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
                    startActivity(intent);
                } else if (isAuthenticated) {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, RegsiterationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

        MealsRepo mealsRepo = MealsRepo.getInstance();
        mealsRepo.getYouMightLikeMealsObservable();
        mealsRepo.getRandomMealObservable();
        mealsRepo.getRootIngredientObservable();
    }
}