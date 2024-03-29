package com.mad.iti.onthetable.ui.search.view.ingredient;

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

import com.bumptech.glide.Glide;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.SearchFragment;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections;
import com.mad.iti.onthetable.ui.search.view.country.AllCountriesFragmentDirections;
import com.mad.iti.onthetable.ui.search.view.ingredient.AllIngredientFragmentDirections.ActionAllIngredientFragmentToSearchMealResultsFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mad.iti.onthetable.ui.search.view.SearchFragmentDirections.ActionNavigationSearchToSearchMealResultsFragment;

import java.util.List;

public class SearchIngredientAdapter extends RecyclerView.Adapter<SearchIngredientAdapter.ViewHolder> {

    private final Context context;
    private List<Ingredient> ingredientList;

    private OnIngredientClickListener listener;

    public SearchIngredientAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    public List<Ingredient> getingredientList() {
        return ingredientList;
    }

    public void setingredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public SearchIngredientAdapter(Context context, List<Ingredient> ingredientList, OnIngredientClickListener listener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.ingredient_item_search_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        String name = ingredientList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient();
        holder.ingredientTextView.setText(ingredientList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient());
        Glide.with(holder.itemView.getContext()).load("https://www.themealdb.com/images/ingredients/" + name + "-Small.png").placeholder(R.drawable.breakfast).error(R.drawable.avocado_small).into(holder.ingredientImage);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;
        RoundedImageView ingredientImage;
        ConstraintLayout constraintLayout;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            ingredientTextView = v.findViewById(R.id.textViewIngredientNameItem);
            ingredientImage = v.findViewById(R.id.imageViewIngredientImageItem);
            constraintLayout = v.findViewById(R.id.row_ingredient_layout);
        }
    }
}
