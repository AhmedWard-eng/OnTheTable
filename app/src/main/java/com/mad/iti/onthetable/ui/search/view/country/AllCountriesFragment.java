package com.mad.iti.onthetable.ui.search.view.country;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;
import com.mad.iti.onthetable.ui.search.view.ingredient.OnIngredientClickListener;
import com.mad.iti.onthetable.ui.search.view.ingredient.SearchIngredientAdapter;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllCountriesFragment extends Fragment implements OnCountryClickListener{

    private RecyclerView countryRecyclerView;
    private SearchCountryAdapter countryAdapter;
    private SearchPresenter searchPresenter;
    private EditText searchByCountry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = SearchPresenter.getInstance(MealsRepo.getInstance());
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchByCountry = view.findViewById(R.id.searchFilterByCountry_editText);

        countryRecyclerView = view.findViewById(R.id.searchFilterByCountry_recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        countryRecyclerView.setLayoutManager(staggeredGridLayoutManager);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        countryRecyclerView.setLayoutManager(gridLayoutManager);
        countryAdapter = new SearchCountryAdapter(getContext() , new ArrayList<>(),this , "AllCountry");
        searchPresenter.getCuisines().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootCuisine>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootCuisine rootCuisine) {
                        countryAdapter.setCountryList(rootCuisine.cuisines);
                        countryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        countryRecyclerView.setAdapter(countryAdapter);

    }

    @Override
    public void onClickItem(String country) {

    }
}