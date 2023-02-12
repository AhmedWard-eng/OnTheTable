package com.mad.iti.onthetable.ui.home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.localSource.roomDatabase.LocalSource;
import com.mad.iti.onthetable.localSource.roomDatabase.LocalSourceRoom;
import com.mad.iti.onthetable.model.Status;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.ui.authentication.AuthenticationActivity;
import com.mad.iti.onthetable.ui.home.view.HomeFragmentDirections.ActionNavigationHomeToMealDetailsFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.GridWithTwoMealAdapter;
import com.mad.iti.onthetable.ui.home.presenter.HomeFragmentPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment implements OnClickListener {

    //    TextView homeTextView;
    private RecyclerView recyclerView;
    private TextView textViewMealName;
    private TextView textViewMealCountry;
    private RoundedImageView imageViewDishOfTheDay;

    private GridWithTwoMealAdapter adapter;
    private static final String TAG = "MealsRepo";
    private HomeFragmentPresenter homeFragmentPresenter;
    CardView cardView;
    private Disposable disposableRandomMeal;
    private Disposable disposableYouMightLike;
    LocalSource localSource;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentPresenter = HomeFragmentPresenter.getInstance(MealsRepo.getInstance());
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recycelrViewYouMightLikeHome);
        textViewMealName = view.findViewById(R.id.txtViewMealNameDishOfTheDay);
        textViewMealCountry = view.findViewById(R.id.textViewCountryDishOfTheDay);
        imageViewDishOfTheDay = view.findViewById(R.id.imageViewDishOfTheDay);
        cardView = view.findViewById(R.id.cardViewRandomMeal);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new GridWithTwoMealAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        localSource = LocalSourceRoom.getInstance(requireContext());


        getRandomMeal();
        getYouMightLikeMeals();

    }

    void getRandomMeal() {
        disposableRandomMeal = homeFragmentPresenter.getRandomMeal().subscribe((rootMeal, throwable) -> {
            if (rootMeal != null && rootMeal.meals != null) {
                Meal meal = rootMeal.meals.get(0);
                setMealIntoTheView(meal);


            }
            Log.e(TAG, "getRandomMeal: homeFragment");
        });
    }

    void getYouMightLikeMeals() {
        disposableYouMightLike = homeFragmentPresenter.getYouMightLikeMeal().subscribe((rootMeal, throwable) -> {
            if (rootMeal != null && rootMeal.meals != null) {
                adapter.setMeals(rootMeal.meals);
                Log.e(TAG, "getRandomMeal: " + throwable);
            }
        });
    }

    private void setMealIntoTheView(Meal meal) {
        textViewMealName.setText(meal.strMeal);
        textViewMealCountry.setText(meal.strArea);
        Glide.with(requireContext()).load(meal.strMealThumb).placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(imageViewDishOfTheDay);
        cardView.setOnClickListener(v -> {
            ActionNavigationHomeToMealDetailsFragment action = HomeFragmentDirections.actionNavigationHomeToMealDetailsFragment(meal.idMeal, Status.ONLINE.toString(), false);
            Navigation.findNavController(v).navigate(action);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.disposableRandomMeal != null && disposableRandomMeal.isDisposed()) {
            disposableRandomMeal.dispose();
        }
        if (this.disposableYouMightLike != null && disposableYouMightLike.isDisposed()) {
            disposableYouMightLike.dispose();
        }
    }

    @Override
    public void onClick(Meal meal) {
        HomeFragmentDirections.ActionNavigationHomeToMealDetailsFragment action = HomeFragmentDirections.actionNavigationHomeToMealDetailsFragment(meal.idMeal, Status.ONLINE.toString(), false);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemLogout = menu.findItem(R.id.logout);

        MenuItem itemLogIn = menu.findItem(R.id.login);
        if (!AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
            itemLogout.setVisible(false);
            itemLogIn.setVisible(true);
        }else{
            itemLogout.setVisible(true);
            itemLogIn.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                new AlertDialog.Builder(requireContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AuthenticationFireBaseRepo.getInstance().logout();
                                FavAndWeekPlanRepo.getInstance(requireContext().getApplicationContext()).deleteAllWeekPlan();
                                FavAndWeekPlanRepo.getInstance(requireContext().getApplicationContext()).deleteAllFav();
//                                Toast.makeText(this, "LoggedOut successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(requireActivity(), AuthenticationActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                return true;
            case R.id.login:
                startActivity(new Intent(requireActivity(),AuthenticationActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

}