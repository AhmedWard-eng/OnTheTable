package com.mad.iti.onthetable.ui.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Cuisine;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.country.OnCountryClickListener;


import java.util.List;

public class CountryInSearchAdapter extends RecyclerView.Adapter<CountryInSearchAdapter.ViewHolder> {

    private final Context context;
    private List<Cuisine> countryList;

    public void setCountryList(List<Cuisine> countryList) {
        this.countryList = countryList;
        notifyDataSetChanged();
    }

    private OnCountryClickListener listener;

    public CountryInSearchAdapter(Context context, List<Cuisine> countryList, OnCountryClickListener listener) {
        this.context = context;
        this.countryList = countryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryInSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.country_search_row_v2, parent, false);
        CountryInSearchAdapter.ViewHolder vh = new CountryInSearchAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryInSearchAdapter.ViewHolder holder, int position) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        String name = countryList.get(position).getStrArea();
        holder.countryTextView.setText(countryList.get(position).getStrArea());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCountry(name);
                checkSearchBy.setType(CheckSearchBy.country);
                checkSearchBy.setName(name);
                Log.i("Country", "onClick: " + name);


            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryTextView;
        ConstraintLayout constraintLayout;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;

            countryTextView = v.findViewById(R.id.country_TextView_search);
            Log.i("texxxxt", "TextView " + countryTextView);
            constraintLayout = v.findViewById(R.id.country_row_layout);
        }
    }

}

