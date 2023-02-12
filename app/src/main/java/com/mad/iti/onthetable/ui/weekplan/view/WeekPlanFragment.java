package com.mad.iti.onthetable.ui.weekplan.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.mad.iti.onthetable.model.FragmentType;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.ui.authentication.AuthenticationActivity;
import com.mad.iti.onthetable.ui.weekplan.view.WeekPlanFragmentDirections.ActionNavigationWeekPlanToSearchByNameFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.formatters.FormatDateToString;
import com.mad.iti.onthetable.model.MealPlanner;
import com.mad.iti.onthetable.model.Status;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.ui.weekplan.presenter.WeekPlanPresenter;
import com.mad.iti.onthetable.ui.weekplan.presenter.WeekPlanPresenterInterface;

import org.checkerframework.checker.units.qual.C;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeekPlanFragment extends Fragment implements ONItemClickListener, OnRemoveClickListener, OnClickCalendar {
    private static Calendar calendar = Calendar.getInstance();
    CalendarView calendarView;
    RecyclerView recyclerView;

    WeekPlannerAdapter weekPlannerAdapter;
    String date;

    Button btnAddMeal;

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
        weekPlannerAdapter = new WeekPlannerAdapter(new ArrayList<>(), this, this , this);
        calendarView = view.findViewById(R.id.calendarView);
        establishCalendarView(calendarView);
        recyclerView = view.findViewById(R.id.mealWeekPlan_recyclerView_weekplan);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(weekPlannerAdapter);

        date = FormatDateToString.getString(new Date());

        btnAddMeal = view.findViewById(R.id.addMeal_button_weekplan);

        btnAddMeal.setOnClickListener(v -> {
            if(AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
                ActionNavigationWeekPlanToSearchByNameFragment action = WeekPlanFragmentDirections.actionNavigationWeekPlanToSearchByNameFragment(FragmentType.PLANNER.toString());
                action.setDate(date);
                Navigation.findNavController(requireView()).navigate(action);
            }else {
                openGotoSignUpDialogue();
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                date = FormatDateToString.getString(year, month, dayOfMonth);
                removeButtonWhenTimeIsLessThanToday(year, month,dayOfMonth);
                getMealsPlanner(FormatDateToString.getString(year, month, dayOfMonth));
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

    private void removeButtonWhenTimeIsLessThanToday(int year, int month, int dayOfMonth) {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.set(Calendar.HOUR,0);
        calendarDay.set(Calendar.MINUTE,0);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(year,month,dayOfMonth);
        if(calendarDay.getTimeInMillis() > calendar1.getTimeInMillis()){
            btnAddMeal.setVisibility(View.GONE);
        }else {
            btnAddMeal.setVisibility(View.VISIBLE);
        }
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
        calendarView.setDate(calendar.getTime().getTime());
        getMealsPlanner(FormatDateToString.getString(calendar.getTime()));
        calendarView.setFirstDayOfWeek(7);
    }


    @Override
    public void onClick(MealPlanner mealPlanner) {
        WeekPlanFragmentDirections.ActionNavigationWeekPlanToMealDetailsFragment action = WeekPlanFragmentDirections.actionNavigationWeekPlanToMealDetailsFragment(mealPlanner.id, Status.OFFLINE.toString(), true);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onRemove(MealPlanner mealPlanner, int position) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.Delete_Meal)
                .setMessage(R.string.Are_you_sure_you_want_to_delete_this_meal)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    weekPlanPresenter.removeItemFromPlanner(mealPlanner);
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onClickCalendar(MealPlanner mealPlanner) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE , mealPlanner.strMeal);
        if(intent.resolveActivity(getContext().getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(getContext(), "Failed to add to calender", Toast.LENGTH_SHORT).show();
        }
    }
}