package com.mad.iti.onthetable;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorite, R.id.navigation_weekPlan, R.id.navigation_menu).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.searchByNameFragment) {
                myToolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.mealDetailsFragment) {
                myToolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.allIngredientFragment) {
                myToolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.allCountriesFragment) {
                myToolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.searchMealResultsFragment) {
                myToolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
            } else {
                myToolbar.setVisibility(View.VISIBLE);
                navView.setVisibility(View.VISIBLE);
            }
        });

        if (AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
            FavAndWeekPlanRepo.getInstance(this.getApplicationContext()).refreshMeals();
            FavAndWeekPlanRepo.getInstance(this.getApplicationContext()).refreshMeals();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }


}