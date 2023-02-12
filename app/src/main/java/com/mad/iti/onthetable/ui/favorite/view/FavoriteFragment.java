package com.mad.iti.onthetable.ui.favorite.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.InternetConnection;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Meal;
import com.mad.iti.onthetable.model.Status;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;
import com.mad.iti.onthetable.ui.favorite.presenter.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnClickFavoriteMeal{

    RecyclerView favMealRecyclerView;
    FavAdapter favAdapter;
    FavoritePresenter favoritePresenter;

    private InternetConnection internetConnection;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritePresenter = FavoritePresenter.getInstance(FavAndWeekPlanRepo.getInstance(getContext()));
        internetConnection = new InternetConnection(requireContext());
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
        com.mad.iti.onthetable.ui.favorite.view.FavoriteFragmentDirections.ActionNavigationFavoriteToMealDetailsFragment action =
                FavoriteFragmentDirections.actionNavigationFavoriteToMealDetailsFragment(id, Status.OFFLINE.toString(),false);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onClickDeleteItem(Meal meal) {
        if (internetConnection.isConnectedWifi() || internetConnection.isConnectedMobile()){
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.Delete_Meal)
                    .setMessage(R.string.Are_you_sure_you_want_to_delete_this_meal)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            favoritePresenter.deleteMealFromFav(meal);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
        }





    }
}