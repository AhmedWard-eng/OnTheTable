package com.mad.iti.onthetable.ui.mealDetails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.GetIdFromYoutubeUrl;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentMealDetailsBinding;
import com.mad.iti.onthetable.model.GetArrayFromMeal;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.mealDetails.presenter.MealsDetailsFragmentPresenter;
import com.mad.iti.onthetable.ui.mealDetails.presenter.MealsDetailsPresenterInterface;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;


public class MealDetailsFragment extends Fragment {


    FragmentMealDetailsBinding fragmentMealDetailsBinding;

    MealsDetailsPresenterInterface mealsDetailsPresenter;
    IngredientsAdapter ingredientsAdapter;
    YouTubePlayerView yt;

    Disposable disposable;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMealDetailsBinding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        mealsDetailsPresenter = MealsDetailsFragmentPresenter.getInstance(MealsRepo.getInstance());
        // Inflate the layout for this fragment
        return fragmentMealDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yt = fragmentMealDetailsBinding.ytPlayer;
//        getLifecycle().addObserver(yt);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        fragmentMealDetailsBinding.recyclerViewIngredientsItemDetails.setAdapter(ingredientsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        fragmentMealDetailsBinding.recyclerViewIngredientsItemDetails.setLayoutManager(linearLayoutManager);
        String id = MealDetailsFragmentArgs.fromBundle(requireArguments()).getMealId();

        getMeal(id);
    }

    private void getMeal(String id) {
        disposable = mealsDetailsPresenter.getMeal(id).subscribe((rootMeal, throwable) -> {
            if (rootMeal != null) {
                List<Meal> meals = rootMeal.meals;

                if (meals != null) {
                    Meal meal = meals.get(0);
                    setMealToTheView(meal);
                }
            }
        });
    }

    private void setMealToTheView(Meal meal) {
        fragmentMealDetailsBinding.txtViewMealNameItemDetails.setText(meal.strMeal);
        fragmentMealDetailsBinding.textViewMealCateItemDetails.setText(meal.strCategory);
        fragmentMealDetailsBinding.textViewMealCountryItemDetails.setText(meal.strArea);
        fragmentMealDetailsBinding.textViewProcedures.setText(formatText(meal.strInstructions));
        Glide.with(requireContext()).load(meal.strMealThumb).placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(fragmentMealDetailsBinding.mealImage);
        ingredientsAdapter.setList(GetArrayFromMeal.getArrayList(meal));

        yt.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                String videoId = GetIdFromYoutubeUrl.getId(meal.strYoutube);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
//        yt.getYouTubePlayerWhenReady(youTubePlayer -> {
//
//            youTubePlayer.cueVideo(,0);
//            // do stuff with it
//        });
    }

    private String formatText(String strInstructions) {

        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        yt.release();
    }
}