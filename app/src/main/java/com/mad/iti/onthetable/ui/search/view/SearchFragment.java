package com.mad.iti.onthetable.ui.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.RootCategory;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.home.presenter.HomeFragmentPresenter;
import com.mad.iti.onthetable.ui.home.view.YouMightLikeAdapter;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;
import com.mad.iti.onthetable.ui.search.view.category.OnCategoryClickListener;
import com.mad.iti.onthetable.ui.search.view.category.SearchCategoryAdapter;
import com.mad.iti.onthetable.ui.search.view.country.OnCountryClickListener;
import com.mad.iti.onthetable.ui.search.view.country.SearchCountryAdapter;
import com.mad.iti.onthetable.ui.search.view.ingredient.OnIngredientClickListener;
import com.mad.iti.onthetable.ui.search.view.ingredient.SearchIngredientAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements OnIngredientClickListener , OnCountryClickListener , OnCategoryClickListener {

    private RecyclerView ingredientRecyclerView;
    private SearchIngredientAdapter ingredientAdapter;

    private RecyclerView categoryRecyclerView;
    private SearchCategoryAdapter categoryAdapter;

    private RecyclerView countryRecyclerView;
    private SearchCountryAdapter countryAdapter;

    private SearchPresenter searchPresenter;

    TextView viewAllCountries , viewAllIngredients;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchPresenter = SearchPresenter.getInstance(MealsRepo.getInstance());

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewAllCountries = view.findViewById(R.id.country_viewAll_textView2);
        viewAllIngredients = view.findViewById(R.id.ingredient_viewAll_textView);
        viewAllCountries.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_allCountriesFragment);
        });

        viewAllIngredients.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_allIngredientFragment);
        });

        ingredientRecyclerView = view.findViewById(R.id.ingredients_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingredientAdapter = new SearchIngredientAdapter(getContext() , new ArrayList<>(),this , "HomeSearch");
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
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        countryRecyclerView = view.findViewById(R.id.counties_recyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        countryRecyclerView.setLayoutManager(layoutManager2);
        countryAdapter = new SearchCountryAdapter(getContext() , new ArrayList<>(),this,"HomeSearch");
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

        categoryRecyclerView = view.findViewById(R.id.categories_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new SearchCategoryAdapter(getContext() , new ArrayList<>(),this);
        searchPresenter.getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootCategory>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootCategory rootCategory) {
                        categoryAdapter.setCategoryList(rootCategory.categories);
                        categoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        categoryRecyclerView.setAdapter(categoryAdapter);

    }

    @Override
    public void onClickItem(String id) {

    }
}