package com.mad.iti.onthetable.ui.search.view.country;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Cuisine;
import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.model.RootCuisine;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.CountryInSearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllCountriesFragment extends Fragment implements OnCountryClickListener{
    private static final String TAG = "AllCountriesFragment";
    private RecyclerView countryRecyclerView;
    private CountryInSearchAdapter countryAdapter;
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchByCountry = view.findViewById(R.id.searchFilterByCountry_editText);

        countryRecyclerView = view.findViewById(R.id.searchFilterByCountry_recyclerView);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        countryRecyclerView.setLayoutManager(staggeredGridLayoutManager);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        countryRecyclerView.setLayoutManager(gridLayoutManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        countryRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryInSearchAdapter(getContext() , new ArrayList<>(),this );
        getCuisines();

        countryRecyclerView.setAdapter(countryAdapter);

    }

    private void getCuisines() {
        searchPresenter.getCuisines().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootCuisine>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootCuisine rootCuisine) {
                        countryAdapter.setCountryList(rootCuisine.cuisines);
                        searchForCuisines(rootCuisine.cuisines);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }


    private void searchForCuisines(ArrayList<Cuisine> cuisines) {


        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        searchByCountry.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                emitter.onNext(s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                })
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: "+Thread.currentThread().getName());
                        List<Cuisine> cuisineList = cuisines.stream().filter(cuisine ->
                                cuisine.strArea.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: "+cuisineList.size());
                        countryAdapter.setCountryList(cuisineList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    @Override
    public void onClickCountry(String country) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        checkSearchBy.setType(CheckSearchBy.country);
        checkSearchBy.setName(country);

       AllCountriesFragmentDirections.ActionAllCountriesFragmentToSearchMealResultsFragment action = AllCountriesFragmentDirections
                .actionAllCountriesFragmentToSearchMealResultsFragment(checkSearchBy);
        Navigation.findNavController(requireView()).navigate(action);
    }
}