package com.mad.iti.onthetable.ui.menu.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;

public class MenuFragment extends Fragment {

    ImageView logoutImage;
    TextView logoutTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutImage = view.findViewById(R.id.logout_imageView_menu);

        logoutTextView = view.findViewById(R.id.logout_textView_menu);
        logoutTextView.setOnClickListener(v -> {
            logOut();
        });
        logoutImage.setOnClickListener(v -> {
            logOut();
        });

    }

    private void logOut() {
        AuthenticationFireBaseRepo.getInstance().logout();
        Toast.makeText(requireContext(), "LoggedOut successfully", Toast.LENGTH_SHORT).show();
    }
}