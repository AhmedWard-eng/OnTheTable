package com.mad.iti.onthetable.ui.weekplan.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.MealPlanner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class WeekPlannerAdapter extends RecyclerView.Adapter<WeekPlannerAdapter.ViewHolder> {

    private List<MealPlanner> mealPlanners;
    private OnRemoveClickListener onRemoveClickListener;
    private ONItemClickListener onItemClickListener;

    public WeekPlannerAdapter(List<MealPlanner> mealPlanners, OnRemoveClickListener onRemoveClickListener, ONItemClickListener onItemClickListener) {
        this.mealPlanners = mealPlanners;
        this.onRemoveClickListener = onRemoveClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    public void setMealPlanners(List<MealPlanner> mealPlanners) {
        this.mealPlanners = mealPlanners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeekPlannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_weekplan_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekPlannerAdapter.ViewHolder holder, int position) {
        holder.getTextViewTitle().setText(mealPlanners.get(position).strMeal);
        holder.getTextViewCountry().setText(mealPlanners.get(position).strArea);
        Glide.with(holder.itemView).load(mealPlanners.get(position).strMealThumb).into(holder.imageView);

        holder.getRow().setOnClickListener(v -> onItemClickListener.onClick(mealPlanners.get(holder.getAbsoluteAdapterPosition())));
        holder.getImageViewRemove().setOnClickListener(v -> onRemoveClickListener.onRemove(mealPlanners.get(holder.getAbsoluteAdapterPosition()),holder.getAbsoluteAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mealPlanners.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final RoundedImageView imageView;
        private final TextView textViewCountry;
        private final RoundedImageView imageViewRemove;
        private final CardView row;
        View view;

        public View getView() {
            return view;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.titleMeal_textView);
            textViewCountry = itemView.findViewById(R.id.countryMeal_textView);
            imageView = itemView.findViewById(R.id.meal_imageView);
            row = itemView.findViewById(R.id.cardViewWeekPlanRow);
            view = itemView;
            imageViewRemove = itemView.findViewById(R.id.icon_remove_item_week);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public RoundedImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewCountry() {
            return textViewCountry;
        }

        public CardView getRow() {
            return row;
        }

        public RoundedImageView getImageViewRemove() {
            return imageViewRemove;
        }
    }
}
