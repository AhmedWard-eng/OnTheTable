package com.mad.iti.onthetable.ui.search.view.country;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Cuisine;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections;
import com.mad.iti.onthetable.ui.search.view.country.AllCountriesFragmentDirections.ActionAllCountriesFragmentToSearchMealResultsFragment;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections.ActionNavigationSearchToSearchMealResultsFragment;


import java.util.List;

public class SearchCountryAdapter extends RecyclerView.Adapter<SearchCountryAdapter.ViewHolder>{

    private final Context context;
    private List<Cuisine> countryList;
    private String fromFragment;

    public List<Cuisine> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Cuisine> countryList) {
        this.countryList = countryList;
    }

    private OnCountryClickListener listener;

    public SearchCountryAdapter(Context context, List<Cuisine> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    public SearchCountryAdapter(Context context, List<Cuisine> countryList , OnCountryClickListener listener , String fromFragment) {
        this.context = context;
        this.countryList = countryList;
        this.listener = listener;
        this.fromFragment = fromFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.country_search_row_layout,parent,false);
        SearchCountryAdapter.ViewHolder vh = new SearchCountryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        String name = countryList.get(position).getStrArea();
        holder.countryTextView.setText(countryList.get(position).getStrArea());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(name);
                checkSearchBy.setType(CheckSearchBy.country);
                checkSearchBy.setName(name);
                Log.i("Country", "onClick: " + name);
                if(fromFragment == "AllCountry"){
                    ActionAllCountriesFragmentToSearchMealResultsFragment action = AllCountriesFragmentDirections
                            .actionAllCountriesFragmentToSearchMealResultsFragment(checkSearchBy);
                    Navigation.findNavController(v).navigate(action);
                }else if(fromFragment == "HomeSearch"){
                    ActionNavigationSearchToSearchMealResultsFragment action = SearchFragmentDirections
                            .actionNavigationSearchToSearchMealResultsFragment(checkSearchBy);
                    Navigation.findNavController(v).navigate(action);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
