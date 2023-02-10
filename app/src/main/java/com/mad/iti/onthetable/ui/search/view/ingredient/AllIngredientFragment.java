package com.mad.iti.onthetable.ui.search.view.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllIngredientFragment extends Fragment implements OnIngredientClickListener{

    private RecyclerView ingredientRecyclerView;
    private SearchIngredientAdapter ingredientAdapter;
    private SearchPresenter searchPresenter;

    private EditText searchByIngredient;
    private Observable filterObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = SearchPresenter.getInstance(MealsRepo.getInstance());


        return inflater.inflate(R.layout.fragment_all_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchByIngredient = view.findViewById(R.id.searchFilterByIngredient_editText);

        List<Ingredient> namesOfIngredient = new ArrayList<>();
        ingredientRecyclerView = view.findViewById(R.id.searchFilterByIngredient_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        ingredientRecyclerView.setLayoutManager(gridLayoutManager);
        ingredientAdapter = new SearchIngredientAdapter(getContext() , new ArrayList<>(),this , "AllIngredient");
        searchPresenter.getIngredients().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootIngredient>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootIngredient rootIngredient) {
                        ingredientAdapter.setingredientList(rootIngredient.ingredients);
                        ingredientAdapter.notifyDataSetChanged();
                        //namesOfIngredient = rootIngredient.ingredients;
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }

    @Override
    public void onClickItem(String id) {

    }
}