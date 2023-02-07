package com.mad.iti.onthetable.ui.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class YouMightLikeAdapter extends RecyclerView.Adapter<YouMightLikeAdapter.ViewHolder> {

    List<Meal> meals;

    public YouMightLikeAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public YouMightLikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YouMightLikeAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(meals.get(position).strMeal);
        Glide.with(holder.getView().getContext()).load(meals.get(position).strMealThumb).placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private ImageView addToFavoriteButton;
        View  view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imageView_item_main);
            textViewName = itemView.findViewById(R.id.textView_meal_title_item_main);
            addToFavoriteButton = itemView.findViewById(R.id.icon_fav_item_main);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public View getView() {
            return view;
        }

        public ImageView getAddToFavoriteButton() {
            return addToFavoriteButton;
        }
    }
}
