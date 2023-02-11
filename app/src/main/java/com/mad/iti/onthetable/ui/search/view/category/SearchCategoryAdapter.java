package com.mad.iti.onthetable.ui.search.view.category;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Category;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections.ActionNavigationSearchToSearchMealResultsFragment;


import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>{

    private final Context context;
    private List<Category> categoryList;


    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    private final OnCategoryClickListener listener;



    public SearchCategoryAdapter(Context context, List<Category> categoryList , OnCategoryClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.category_search_row_layout,parent,false);
        SearchCategoryAdapter.ViewHolder vh = new SearchCategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = categoryList.get(position).getStrCategory();
        holder.categoryTextView.setText(categoryList.get(position).getStrCategory());
        Glide.with(context)
                .load(categoryList.get(position).getStrCategoryThumb())
                .into(holder.categoryImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCategory(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTextView;
        ImageView categoryImage;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            categoryTextView = v.findViewById(R.id.cateroryName_textView_card);
            categoryImage = v.findViewById(R.id.category_imageView_card);
            cardView = v.findViewById(R.id.category_cardView_search);
        }
    }
}
