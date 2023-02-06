package com.mad.iti.onthetable.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

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
                mSharedPreferences = getSharedPreferences("mySp",MODE_PRIVATE);
                boolean isFirstTime = mSharedPreferences.getBoolean("firstTime" , true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}