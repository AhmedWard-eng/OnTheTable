package com.mad.iti.onthetable.ui.search.view.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Ingredient;
import com.mad.iti.onthetable.model.RootIngredient;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.search.presenter.SearchPresenter;
import com.mad.iti.onthetable.ui.search.view.CheckSearchBy;

import java.util.ArrayList;
import java.util.List;
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

public class AllIngredientFragment extends Fragment implements OnIngredientClickListener {

    private RecyclerView ingredientRecyclerView;
    private SearchIngredientAdapter ingredientAdapter;
    private SearchPresenter searchPresenter;

    private EditText searchByIngredient;
    private Observable filterObservable;
    private static final String TAG = "AllIngredientFragment";

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
        ingredientAdapter = new SearchIngredientAdapter(getContext(), new ArrayList<>(), this);

        getAllIngredients();

        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }

    private void searchForIngredients(ArrayList<Ingredient> ingredients) {

         Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        searchByIngredient.addTextChangedListener(new TextWatcher() {
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
                 .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: "+Thread.currentThread().getName());
                        List<Ingredient> ingredientList = ingredients.stream().filter(ingredient ->
                            ingredient.strIngredient.contains(s)
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: "+ingredientList.size());
                        ingredientAdapter.setingredientList(ingredientList);
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

    private void getAllIngredients() {
        searchPresenter.getIngredients()
                .subscribe(new SingleObserver<RootIngredient>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootIngredient rootIngredient) {
                        searchForIngredients(rootIngredient.ingredients);
                        ingredientAdapter.setingredientList(rootIngredient.ingredients);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void onClickItem(String name) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        checkSearchBy.setType(CheckSearchBy.ingredient);
        checkSearchBy.setName(name);
        com.mad.iti.onthetable.ui.search.view.ingredient.AllIngredientFragmentDirections.ActionAllIngredientFragmentToSearchMealResultsFragment action = AllIngredientFragmentDirections
                .actionAllIngredientFragmentToSearchMealResultsFragment(checkSearchBy);
        Navigation.findNavController(requireView()).navigate(action);
    }
}