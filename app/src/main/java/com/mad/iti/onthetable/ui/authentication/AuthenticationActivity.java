package com.mad.iti.onthetable.ui.authentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.mad.iti.onthetable.R;

public class AuthenticationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiteration);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_content_regsiteration);
//        NavigationUI.(this,navController);

    }


}