package com.mad.iti.onthetable.ui.mealDetails.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.IngredientWithMeasure;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<IngredientWithMeasure> ingredientWithMeasures;


    public IngredientsAdapter(List<IngredientWithMeasure> list) {
        ingredientWithMeasures = list;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item_meal_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        holder.getTextViewIngredientMeasure().setText(ingredientWithMeasures.get(position).getIngredientMeasure());
        holder.getTextViewIngredientName().setText(ingredientWithMeasures.get(position).getIngredientName());
        Glide.with(holder.getView().getContext()).load("https://www.themealdb.com/images/ingredients/" + ingredientWithMeasures.get(position).getIngredientName() + "-Small.png").placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(holder.roundedImageView);


    }

    @Override
    public int getItemCount() {
        return ingredientWithMeasures.size();
    }

    public void setList(ArrayList<IngredientWithMeasure> arrayList) {
        ingredientWithMeasures = arrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIngredientName;
        TextView textViewIngredientMeasure;
        RoundedImageView roundedImageView;
        View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIngredientName = itemView.findViewById(R.id.textViewIngredientNameItem_mealDetails);

            textViewIngredientMeasure = itemView.findViewById(R.id.textViewIngredientMeasureItem);

            roundedImageView = itemView.findViewById(R.id.imageViewIngredientImageItem_mealDetails);
            view = itemView;
        }

        public TextView getTextViewIngredientName() {
            return textViewIngredientName;
        }

        public TextView getTextViewIngredientMeasure() {
            return textViewIngredientMeasure;
        }

        public RoundedImageView getRoundedImageView() {
            return roundedImageView;
        }

        public View getView() {
            return view;
        }
    }
}
