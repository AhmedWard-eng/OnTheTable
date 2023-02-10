package com.mad.iti.onthetable.ui.search.view.mealResult;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.RootMealPreview;
import com.mad.iti.onthetable.model.repositories.MealsRepo;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;
import com.mad.iti.onthetable.ui.search.view.ingredient.OnIngredientClickListener;
import com.mad.iti.onthetable.ui.search.view.ingredient.SearchIngredientAdapter;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchMealResultsFragment extends Fragment {

    TextView resultTextView;

    private RecyclerView previewMealRecyclerView;
    private MealResultAdapter mealResultAdapter;

    SearchPresenter searchPresenter;

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
        return inflater.inflate(R.layout.fragment_search_meal_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckSearchBy checkSearchBy = SearchMealResultsFragmentArgs.fromBundle(getArguments()).getResultMeal();
        Log.i("Mealll Result", "onViewCreated: "+checkSearchBy.getName());

        resultTextView = view.findViewById(R.id.searchResult_textView);
        resultTextView.setText(resultTextView.getText()+" "+checkSearchBy.getName());
        previewMealRecyclerView = view.findViewById(R.id.mealResults_recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        previewMealRecyclerView.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        previewMealRecyclerView.setLayoutManager(gridLayoutManager);
        mealResultAdapter = new MealResultAdapter(getContext() , new ArrayList<>());

        if(checkSearchBy.getType() == CheckSearchBy.ingredient){
            checkSearchBy = SearchMealResultsFragmentArgs.fromBundle(getArguments()).getResultMeal();
            searchPresenter.getMealByIngredient(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMealPreview>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMealPreview rootMealPreview) {
                            mealResultAdapter.setMealPreviewList(rootMealPreview.mealPreviews);
                            mealResultAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }else if(checkSearchBy.getType() == CheckSearchBy.country){
            searchPresenter.getMealByCountry(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMealPreview>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMealPreview rootMealPreview) {
                            mealResultAdapter.setMealPreviewList(rootMealPreview.mealPreviews);
                            mealResultAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

        }else if(checkSearchBy.getType() == CheckSearchBy.category){
            searchPresenter.getMealByCategory(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMealPreview>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMealPreview rootMealPreview) {
                            mealResultAdapter.setMealPreviewList(rootMealPreview.mealPreviews);
                            mealResultAdapter.notifyDataSetChanged();
                            previewMealRecyclerView.setAdapter(mealResultAdapter);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }

        previewMealRecyclerView.setAdapter(mealResultAdapter);
    }
}