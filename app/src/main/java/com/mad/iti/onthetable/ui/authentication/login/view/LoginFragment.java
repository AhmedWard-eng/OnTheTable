package com.mad.iti.onthetable.ui.authentication.login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentLoginBinding;
import com.mad.iti.onthetable.ui.authentication.login.presenter.LoginPresenter;
import com.mad.iti.onthetable.ui.authentication.login.presenter.LoginPresenterInterface;


public class LoginFragment extends Fragment implements LoginViewInterface {


    FragmentLoginBinding binding;

    LoginPresenterInterface loginPresenter;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        loginPresenter = LoginPresenter.getInstance(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonLogin.setOnClickListener(v -> {
            login();
        });

        binding.textViewGoToSignUP.setOnClickListener(v -> {
            Navigation.findNavController(view).clearBackStack(R.id.signUpFragment);
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void login() {
        if (checkValidation()) {
            String userName = binding.TextInputEditTextEmailLogin.getText().toString();
            String pass = binding.TextInputEditTextPassLogin.getText().toString();
            loginPresenter.login(userName, pass);
        }
    }

    private boolean checkValidation() {
        if (binding.TextInputEditTextEmailLogin.getText() != null && !binding.TextInputEditTextEmailLogin.getText().toString().isEmpty() && binding.TextInputEditTextPassLogin.getText() != null && !binding.TextInputEditTextPassLogin.getText().toString().isEmpty())
            return true;
        else {
            binding.textInputLayoutEmailLogin.setError("fill all data");
            binding.textInputLayoutPassLogin.setError("fill all data");
            return false;
        }
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Toast.makeText(binding.getRoot().getContext(), "LoggedIn Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);

    }

    @Override
    public void OnFailure(String message) {

        binding.textInputLayoutEmailLogin.setError(message);
        binding.textInputLayoutPassLogin.setError(message);
    }
}