package com.mad.iti.onthetable.ui.favorite.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Cuisine;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.ui.search.view.country.OnCountryClickListener;
import com.mad.iti.onthetable.ui.search.view.country.SearchCountryAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>{

    private final Context context;
    private List<Meal> favMealsList;

    public void setFavMealsListList(List<Meal> favMealsList) {
        this.favMealsList = favMealsList;
    }

    private OnClickFavoriteMeal listener;

    public FavAdapter(Context context, List<Meal> favMealsList , OnClickFavoriteMeal listener ) {
        this.context = context;
        this.favMealsList = favMealsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_fav_meal,parent,false);
        FavAdapter.ViewHolder vh = new FavAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealNameTextView.setText(favMealsList.get(position).strMeal);
        Glide.with(context)
                .load(favMealsList.get(position).strMealThumb)
                .into(holder.mealImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(favMealsList.get(holder.getAbsoluteAdapterPosition()).idMeal);
            }
        });

        holder.deleteMealIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDeleteItem(favMealsList.get(holder.getAbsoluteAdapterPosition()).idMeal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favMealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mealNameTextView;
        RoundedImageView mealImage;
        RoundedImageView deleteMealIcon;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;

            mealNameTextView = v.findViewById(R.id.textView_meal_title_item_fav);
            mealImage = v.findViewById(R.id.imageView_item_fav);
            deleteMealIcon = v.findViewById(R.id.icon_remove_item_fav);
            cardView = v.findViewById(R.id.cardViewRandomMeal);
        }
    }

}
