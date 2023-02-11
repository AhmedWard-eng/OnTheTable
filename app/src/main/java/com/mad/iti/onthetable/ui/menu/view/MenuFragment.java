package com.mad.iti.onthetable.ui.menu.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.dataRepo.FavAndWeekPlanRepo;

public class MenuFragment extends Fragment {

    ImageView logoutImage;
    TextView logoutTextView;

    Group favGroup , weekPlanGroup , logoutGroup;

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

        favGroup = view.findViewById(R.id.favmeal_group);
        weekPlanGroup = view.findViewById(R.id.weekplan_group);
        logoutGroup = view.findViewById(R.id.logout_group);
        int refIds[] = logoutGroup.getReferencedIds();
        for (int id : refIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logOut();
                }
            });
        }

        int weekPlanrefIds[] = weekPlanGroup.getReferencedIds();
        for (int id : weekPlanrefIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_menu_to_navigation_weekPlan);
                }
            });
        }

        int favMealrefIds[] = favGroup.getReferencedIds();
        for (int id : favMealrefIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_menu_to_navigation_favorite);
                }
            });
        }


    }

    private void logOut() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AuthenticationFireBaseRepo.getInstance().logout();
                        FavAndWeekPlanRepo.getInstance(requireContext().getApplicationContext()).deleteAllWeekPlan();
                        FavAndWeekPlanRepo.getInstance(requireContext().getApplicationContext()).deleteAllFav();
                        Toast.makeText(requireContext(), "LoggedOut successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_navigation_menu_to_authenticationActivity);

                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }
}