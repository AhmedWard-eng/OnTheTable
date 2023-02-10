package com.mad.iti.onthetable;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mad.iti.onthetable.databinding.ActivityMainBinding;
import com.mad.iti.onthetable.model.repositories.MealsRepo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorite,R.id.navigation_weekPlan,R.id.navigation_menu).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        MealsRepo mealsRepo = MealsRepo.getInstance();
        mealsRepo.getRootIngredientObservable().subscribe((rootIngredient, throwable) -> {
            Log.i(TAG, "Observer onCreate: "+rootIngredient.ingredients.get(0));
            Log.e(TAG, "Observer onCreate: "+throwable );
        });

    }

}