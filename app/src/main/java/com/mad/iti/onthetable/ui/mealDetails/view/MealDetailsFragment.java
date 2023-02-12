package com.mad.iti.onthetable.ui.mealDetails.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.InternetConnection;
import com.mad.iti.onthetable.formatters.FormatDateToString;
import com.mad.iti.onthetable.formatters.GetIdFromYoutubeUrl;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentMealDetailsBinding;
import com.mad.iti.onthetable.model.GetArrayFromMeal;
import com.mad.iti.onthetable.model.MealPlannerAndMealConverter;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.Status;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.OnAddingListener;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.authentication.AuthenticationActivity;
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

    InternetConnection internetConnection;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentMealDetailsBinding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        mealsDetailsPresenter = MealsDetailsFragmentPresenter.getInstance(MealsRepo.getInstance(), FavAndWeekPlanRepo.getInstance(requireContext()));
        internetConnection = new InternetConnection(requireContext());

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
        String status = MealDetailsFragmentArgs.fromBundle(requireArguments()).getStatus();
        boolean isWeekPlanner = MealDetailsFragmentArgs.fromBundle(requireArguments()).getIsWeekPlanner();
        if (status.equals(Status.ONLINE.toString())) {
            getMealFromNetwork(id);
        } else {
            if (!isWeekPlanner) getMealFromLocal(id);
            else getMealPlanner(id);
        }

    }

    private void getMealPlanner(String id) {
        mealsDetailsPresenter.getMealFromWeekPlanFavById(id).observe(getViewLifecycleOwner(), mealPlanner -> {
            setMealToTheView(MealPlannerAndMealConverter.getMealFromMealPlanner(mealPlanner));
        });
    }

    private void getMealFromLocal(String id) {
        mealsDetailsPresenter.getMealFromFavById(id).observe(getViewLifecycleOwner(), this::setMealToTheView);
        fragmentMealDetailsBinding.imageViewAddToFavITemDetails.setImageResource(R.drawable.baseline_favorite_48);

    }

    private void openDatePicker(Meal meal) {
        DialogFragment newFragment = new DatePickerFragment(meal, mealsDetailsPresenter);
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    }

    private void getMealFromNetwork(String id) {
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
        imageViewADDToWeekPlanner.setOnClickListener((v) -> {
            if(internetConnection.isConnectedMobile() || internetConnection.isConnectedWifi()){
                if(AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
                    openDatePicker(meal);
                }else{
                    openGotoSignUpDialogue();
                }
            }else {
                Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
            }
        });

        yt.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                String videoId = GetIdFromYoutubeUrl.getId(meal.strYoutube);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        fragmentMealDetailsBinding.imageViewAddToFavITemDetails.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(internetConnection.isConnectedMobile() || internetConnection.isConnectedWifi()){
                    if (AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
                        mealsDetailsPresenter.addFavMeal(meal, new OnAddingListener() {
                            @Override
                            public void onSuccess() {
                                Log.i("testtt", "Click on Fav " + meal.strMeal);
                                fragmentMealDetailsBinding.imageViewAddToFavITemDetails.setImageResource(R.drawable.baseline_favorite_48);
                                Toast.makeText(getContext(), meal.strMeal + " " + v.getContext().getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String message) {
                                Log.i("testtt", "Click on Fav " + message);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        openGotoSignUpDialogue();
                    }
                }else {
                    Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void openGotoSignUpDialogue() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.Sign_Up_for_more_Feature)
                .setMessage(R.string.goto_signUp_message)
                .setPositiveButton(R.string.singup, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(requireActivity(), AuthenticationActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
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

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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
            return datePickerDialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            MealPlanner mealPlanner = MealPlannerAndMealConverter.getMealPlannerFromMealAndDate(meal, FormatDateToString.getString(year, month, day), 0);
            mealsDetailsPresenter.addToWeekPlanner(mealPlanner, new OnAddingListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(view.getContext(), R.string.added_successfully_to_your_plan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}