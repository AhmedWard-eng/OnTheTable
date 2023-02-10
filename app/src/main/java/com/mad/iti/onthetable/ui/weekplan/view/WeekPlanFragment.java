package com.mad.iti.onthetable.ui.weekplan.view;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.mad.iti.onthetable.R;

import java.util.Calendar;


public class WeekPlanFragment extends Fragment {

    CalendarView calendarView;
    Button button;
    RecyclerView recyclerView;
    private static final String TAG = "WeekPlanFragment";


    public WeekPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_WEEK,1);
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendarView.setFirstDayOfWeek(7);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(view.getDate());
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(year,month,dayOfMonth);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Log.d(TAG, "onSelectedDayChange: "+calendar1.get(Calendar.DAY_OF_WEEK));
            }
        });
    }
}