package com.mad.iti.onthetable.ui.registeration.singup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.iti.onthetable.R;


public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.buttonSignUp).setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}