package com.mad.iti.onthetable.ui.search.view.mealResult;

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
import com.mad.iti.onthetable.model.MealPreview;
import com.mad.iti.onthetable.ui.home.view.HomeFragmentDirections;
import com.mad.iti.onthetable.ui.search.view.category.OnCategoryClickListener;
import com.mad.iti.onthetable.ui.search.view.category.SearchCategoryAdapter;
import com.mad.iti.onthetable.ui.search.view.mealResult.SearchMealResultsFragmentDirections.ActionSearchMealResultsFragmentToMealDetailsFragment;

import java.util.List;

public class MealResultAdapter extends RecyclerView.Adapter<MealResultAdapter.ViewHolder>{
    private static final String TAG = "MealResultAdapter";
    private final Context context;
    private List<MealPreview> mealPreviewList;

    public List<MealPreview> getMealPreviewList() {
        return mealPreviewList;
    }

    public void setMealPreviewList(List<MealPreview> mealPreviewList) {
        this.mealPreviewList = mealPreviewList;
    }



    private OnClickMealResult listener;



    public MealResultAdapter(Context context, List<MealPreview> mealPreviewList , OnClickMealResult listener) {
        this.context = context;
        this.mealPreviewList = mealPreviewList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.preview_meal_row_layout,parent,false);
        MealResultAdapter.ViewHolder vh = new MealResultAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealNameTextView.setText(mealPreviewList.get(position).getStrMeal());
        Glide.with(context)
                .load(mealPreviewList.get(position).getStrMealThumb())
                .into(holder.mealImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(mealPreviewList.get(holder.getAbsoluteAdapterPosition()).idMeal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealPreviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mealNameTextView;
        ImageView mealImage;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            mealNameTextView = v.findViewById(R.id.titlePreviewMeal_textView);
            mealImage = v.findViewById(R.id.previewMeal_imageView);
            cardView = v.findViewById(R.id.previewMeal_cardView);
        }
    }
}
