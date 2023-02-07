package com.mad.iti.onthetable.ui.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentHomeBinding;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.RootMeal;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.home.presenter.HomeFragmentPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment {

    //    TextView homeTextView;
    private RecyclerView recyclerView;
    private TextView textViewMealName;
    private TextView textViewMealCountry;
    private RoundedImageView imageViewDishOfTheDay;

    private YouMightLikeAdapter adapter;
    private static final String TAG = "MealsRepo";
    private HomeFragmentPresenter homeFragmentPresenter;
    private Disposable disposableRandomMeal;
    private Disposable disposableYouMightLike;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentPresenter = HomeFragmentPresenter.getInstance(MealsRepo.getInstance());

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recycelrViewYouMightLikeHome);
        textViewMealName = view.findViewById(R.id.txtViewMealNameDishOfTheDay);
        textViewMealCountry = view.findViewById(R.id.textViewCountryDishOfTheDay);
        imageViewDishOfTheDay = view.findViewById(R.id.imageViewDishOfTheDay);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new YouMightLikeAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        getRandomMeal();
        getYouMightLikeMeals();

    }

    void getRandomMeal() {
        disposableRandomMeal = homeFragmentPresenter.getRandomMeal().subscribe((rootMeal, throwable) -> {
            Meal meal = rootMeal.meals.get(0);
            setMealIntoTheView(meal);
            Log.e(TAG, "getRandomMeal: " + throwable);
        });
    }

    void getYouMightLikeMeals() {
        disposableYouMightLike = homeFragmentPresenter.getYouMightLikeMeal().subscribe((rootMeal, throwable) -> {
            adapter.setMeals(rootMeal.meals);
            Log.e(TAG, "getRandomMeal: " + throwable);
        });
    }

    private void setMealIntoTheView(Meal meal) {
        textViewMealName.setText(meal.strMeal);
        textViewMealCountry.setText(meal.strArea);
        Glide.with(requireContext()).load(meal.strMealThumb).placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(imageViewDishOfTheDay);

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
}