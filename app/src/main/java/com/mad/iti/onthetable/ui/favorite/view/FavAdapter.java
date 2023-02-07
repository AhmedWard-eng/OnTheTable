package com.mad.iti.onthetable.ui.favorite.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.ui.home.view.YouMightLikeAdapter;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_meal, parent, false);
        return new ViewHolder(v);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
