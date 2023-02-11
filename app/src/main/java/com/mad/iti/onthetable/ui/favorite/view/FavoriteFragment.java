package com.mad.iti.onthetable.ui.favorite.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.favorite.presenter.FavoritePresenter;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnClickFavoriteMeal{

    RecyclerView favMealRecyclerView;
    FavAdapter favAdapter;
    FavoritePresenter favoritePresenter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritePresenter = FavoritePresenter.getInstance(FavAndWeekPlanRepo.getInstance(getContext()));

        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        favMealRecyclerView = view.findViewById(R.id.favoriteMeals_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        favMealRecyclerView.setLayoutManager(gridLayoutManager);
        favAdapter = new FavAdapter(getContext() , new ArrayList<>() ,this);
        favMealRecyclerView.setAdapter(favAdapter);
        favoritePresenter.getFavoriteMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favAdapter.setFavMealsListList(meals);
                favAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClickItem(String id) {
        Log.d("TAG", "onClick: item id= "+id);
        com.mad.iti.onthetable.ui.favorite.view.FavoriteFragmentDirections.ActionNavigationFavoriteToMealDetailsFragment action =
                FavoriteFragmentDirections.actionNavigationFavoriteToMealDetailsFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onClickDeleteItem(Meal meal) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to delete this meal?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Toast.makeText(getContext(), "Removvvve", Toast.LENGTH_SHORT).show();
                        favoritePresenter.deleteMealFromFav(meal);
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }
}