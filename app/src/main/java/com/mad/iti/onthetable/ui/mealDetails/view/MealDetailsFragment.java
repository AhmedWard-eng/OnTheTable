package com.mad.iti.onthetable.ui.mealDetails.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.formatters.FormatDateToString;
import com.mad.iti.onthetable.formatters.GetIdFromYoutubeUrl;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentMealDetailsBinding;
import com.mad.iti.onthetable.model.GetArrayFromMeal;
import com.mad.iti.onthetable.model.GetMealPlannerFromMealAndDate;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanInterface;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.mealDetails.presenter.MealsDetailsFragmentPresenter;
import com.mad.iti.onthetable.ui.mealDetails.presenter.MealsDetailsPresenterInterface;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;


public class MealDetailsFragment extends Fragment {


    FragmentMealDetailsBinding fragmentMealDetailsBinding;

    MealsDetailsPresenterInterface mealsDetailsPresenter;
    IngredientsAdapter ingredientsAdapter;

    ImageView imageViewADDToWeekPlanner;
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
        mealsDetailsPresenter = MealsDetailsFragmentPresenter.getInstance(MealsRepo.getInstance(), FavAndWeekPlanRepo.getInstance(requireContext()));
        // Inflate the layout for this fragment
        return fragmentMealDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yt = fragmentMealDetailsBinding.ytPlayer;
        imageViewADDToWeekPlanner = view.findViewById(R.id.imageViewAddToCalendarItemDetails);

//        getLifecycle().addObserver(yt);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        fragmentMealDetailsBinding.recyclerViewIngredientsItemDetails.setAdapter(ingredientsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        fragmentMealDetailsBinding.recyclerViewIngredientsItemDetails.setLayoutManager(linearLayoutManager);
        String id = MealDetailsFragmentArgs.fromBundle(requireArguments()).getMealId();

        getMeal(id);

    }

    private void openDatePicker(Meal meal) {
        DialogFragment newFragment = new DatePickerFragment(meal, mealsDetailsPresenter);
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
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
        imageViewADDToWeekPlanner.setOnClickListener((v) -> openDatePicker(meal));

        yt.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                String videoId = GetIdFromYoutubeUrl.getId(meal.strYoutube);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        fragmentMealDetailsBinding.imageViewAddToFavITemDetails.setOnClickListener(new View.OnClickListener() {
            boolean added = false;
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mealsDetailsPresenter.addFavMeal(meal, new OnAddingListener() {
                    @Override
                    public void onSuccess() {
                        added = true;
                        Log.i("testtt", "Click on Fav "+meal.strMeal);
                        Toast.makeText(getContext(),"Click on Fav "+meal.strMeal,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.i("testtt", "Click on Fav "+message);
                        Toast.makeText(getContext(),"Click on Fav "+message,Toast.LENGTH_SHORT).show();

                    }
                });
//                if(added){
//                    Toast.makeText(getContext(),"Click on Fav "+meal.strMeal,Toast.LENGTH_SHORT).show();
//                    fragmentMealDetailsBinding.imageViewAddToFavITemDetails.setImageTintList(ColorStateList.valueOf(R.color.active));
//                }
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Meal meal;
        MealsDetailsPresenterInterface mealsDetailsPresenter;

        public DatePickerFragment(Meal meal, MealsDetailsPresenterInterface mealsDetailsFragmentPresenter) {
            this.meal = meal;
            this.mealsDetailsPresenter = mealsDetailsFragmentPresenter;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            // Create a new instance of DatePickerDialog and return it
            return datePickerDialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            MealPlanner mealPlanner = GetMealPlannerFromMealAndDate.getMealPlanner(meal, FormatDateToString.getString(year, month, day), 0);
            mealsDetailsPresenter.addToWeekPlanner(mealPlanner, new OnAddingListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(view.getContext(), "added Successfully to your plan", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
            // Do something with the date chosen by the user
        }
    }

}