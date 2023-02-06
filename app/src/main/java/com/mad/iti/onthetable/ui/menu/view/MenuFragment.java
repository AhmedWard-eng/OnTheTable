package com.mad.iti.onthetable.ui.menu.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.iti.onthetable.R;

public class MenuFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}