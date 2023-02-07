package com.mad.iti.onthetable.ui.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;

public class YouMightLikeAdapter extends RecyclerView.Adapter<YouMightLikeAdapter.ViewHolder> {
    @NonNull
    @Override
    public YouMightLikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YouMightLikeAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText("Traditional French omelette");
        holder.imageView.setImageResource(R.drawable.breakfast);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private ImageView addToFavoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

        public ImageView getAddToFavoriteButton() {
            return addToFavoriteButton;
        }
    }
}
