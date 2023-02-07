package com.mad.iti.onthetable.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    //    TextView homeTextView;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        homeTextView = view.findViewById(R.id.text_home);
//        homeTextView.setOnClickListener(v->{
//        });
        recyclerView = view.findViewById(R.id.recycelrViewYouMightLikeHome);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        YouMightLikeAdapter adapter = new YouMightLikeAdapter();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}