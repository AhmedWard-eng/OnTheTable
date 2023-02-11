package com.mad.iti.onthetable.ui.weekplan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.formatters.FormatDateToString;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.ui.weekplan.presenter.WeekPlanPresenter;
import com.mad.iti.onthetable.ui.weekplan.presenter.WeekPlanPresenterInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeekPlanFragment extends Fragment implements ONItemClickListener, OnRemoveClickListener {

    CalendarView calendarView;
    Button button;
    RecyclerView recyclerView;

    WeekPlannerAdapter weekPlannerAdapter;

    private WeekPlanPresenterInterface weekPlanPresenter;
    private static final String TAG = "WeekPlanFragment";


    public WeekPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        weekPlanPresenter = WeekPlanPresenter.getInstance(FavAndWeekPlanRepo.getInstance(requireContext()));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weekPlannerAdapter = new WeekPlannerAdapter(new ArrayList<>(), this, this);
        calendarView = view.findViewById(R.id.calendarView);
        establishCalendarView(calendarView);
        recyclerView = view.findViewById(R.id.mealWeekPlan_recyclerView_weekplan);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(weekPlannerAdapter);
        getMealsPlanner(FormatDateToString.getString(new Date()));


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "debug onSelectedDayChange: " + FormatDateToString.getString(year, month, dayOfMonth));
                getMealsPlanner(FormatDateToString.getString(year, month, dayOfMonth));
            }
        });
    }

    private void getMealsPlanner(String dateString) {
        weekPlanPresenter.getMealPlannerByDate(dateString).observe(getViewLifecycleOwner(), new Observer<List<MealPlanner>>() {
            @Override
            public void onChanged(List<MealPlanner> mealPlanners) {
                Log.d(TAG, "debug onChanged: " + mealPlanners.size());
                weekPlannerAdapter.setMealPlanners(mealPlanners);
            }
        });
    }

    private void establishCalendarView(CalendarView calendarView) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendarView.setFirstDayOfWeek(7);
    }

    @Override
    public void onClick(MealPlanner mealPlanner) {
    }

    @Override
    public void onRemove(MealPlanner mealPlanner, int position) {
        weekPlanPresenter.removeItemFromPlanner(mealPlanner);
        weekPlannerAdapter.notifyItemRemoved(position);
    }
}