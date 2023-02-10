package com.mad.iti.onthetable.ui.authentication.singup.view;

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
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentSignUpBinding;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenter;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenterInterface;


public class SignUpFragment extends Fragment implements SignUpViewInterface {

    FragmentSignUpBinding binding;
    SignUpPresenterInterface signUpPresenter;

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
        signUpPresenter = SignUpPresenter.getInstance(this);
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonSignUp.setOnClickListener(v -> signUp());

        binding.textViewGoToLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment);
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void signUp() {
        if (checkValidation()) {
            String userName = binding.textInputEditTextEmailSignUp.getText().toString();
            String pass = binding.textInputEditTextPassSignUp.getText().toString();
            signUpPresenter.signUp(userName, pass);
        }
    }

    private boolean checkValidation() {
        if (binding.textInputEditTextEmailSignUp.getText() != null
                && !binding.textInputEditTextEmailSignUp.getText().toString().isEmpty()
                && binding.textInputEditTextConfirmPassSignUp.getText() != null
                && !binding.textInputEditTextConfirmPassSignUp.getText().toString().isEmpty()
                && binding.textInputEditTextPassSignUp.getText() != null
                && !binding.textInputEditTextPassSignUp.getText().toString().isEmpty()
                && checkPassEquality())
            return true;
        else {
            binding.textInputLayoutEmailSignUP.setError("fill all data");
            binding.textInputLayoutPassWord.setError("fill all data");
            binding.confirmPasswordInputLayout.setError("fill all data");
            return false;
        }
    }

    private boolean checkPassEquality() {
        return binding.textInputEditTextConfirmPassSignUp.getText().toString().equals(binding.textInputEditTextPassSignUp.getText().toString());
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Toast.makeText(requireContext(), R.string.signup_successfully, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_loginFragment);
    }

    @Override
    public void OnFailure(String message) {
        binding.textInputLayoutEmailSignUP.setError(message);
        binding.textInputLayoutPassWord.setError(message);
        binding.confirmPasswordInputLayout.setError(message);
    }
}