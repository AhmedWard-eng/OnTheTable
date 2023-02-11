package com.mad.iti.onthetable.ui.search.view;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.Status;
import com.mad.iti.onthetable.model.repositories.mealsRepo.MealsRepo;
import com.mad.iti.onthetable.ui.GridWithTwoMealAdapter;
import com.mad.iti.onthetable.ui.home.view.OnClickListener;
import com.mad.iti.onthetable.ui.search.presenter.SearchByNamePresenter;
import com.mad.iti.onthetable.ui.search.presenter.SearchByNamePresenterInterface;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;


public class SearchByNameFragment extends Fragment implements OnClickListener {

    private static final String TAG = "SearchByNameFragment";
    EditText editTextSearch;
    RecyclerView recyclerView;
    Disposable disposable;
    SearchByNamePresenterInterface searchByNamePresenter;

    private GridWithTwoMealAdapter adapter;

    public SearchByNameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchByNamePresenter = SearchByNamePresenter.getInstance(MealsRepo.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextSearch = view.findViewById(R.id.searchByName_editText);
        recyclerView = view.findViewById(R.id.searchByName_recyclerView);
        editTextSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextSearch, InputMethodManager.SHOW_IMPLICIT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new GridWithTwoMealAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        searchWithTextWitcher();

    }

    private void searchWithTextWitcher() {

        Observable<String> observable = Observable.create((ObservableOnSubscribe<String>) emitter -> editTextSearch.addTextChangedListener(new TextWatcher() {
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
        })).debounce(300, TimeUnit.MILLISECONDS);

        observable.subscribe(this::searchMeal);

    }

    private void searchMeal(String s) {
        Log.d(TAG, "searchMeal: ");
        if (!s.isEmpty()) {
            disposable = searchByNamePresenter.searchMealByName(s).subscribe((rootMeal, throwable) -> {
                if (rootMeal != null && rootMeal.meals != null) adapter.setMeals(rootMeal.meals);
                else {
                    adapter.setMeals(new ArrayList<>());
                    Log.e(TAG, "rootMeal.meals is null");
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onClick(String id) {
        SearchByNameFragmentDirections.ActionSearchByNameFragmentToMealDetailsFragment action = SearchByNameFragmentDirections.actionSearchByNameFragmentToMealDetailsFragment(id, Status.ONLINE.toString());
        Navigation.findNavController(requireView()).navigate(action);
    }
}