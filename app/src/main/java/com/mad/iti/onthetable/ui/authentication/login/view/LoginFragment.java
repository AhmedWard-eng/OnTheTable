package com.mad.iti.onthetable.ui.authentication.login.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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

    TextView textViewSkip;

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
        binding.buttonLogin.setOnClickListener(v -> login());

        textViewSkip = binding.skipTextViewLogin;
        textViewSkip.setOnClickListener(v -> goToMainActivity());

        binding.textViewGoToSignUP.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.TextInputEditTextEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidEmail(s.toString())){
                    binding.TextInputEditTextEmailLogin.setError("Please enter valid email");
                }else{
                    binding.TextInputEditTextEmailLogin.setError(null);
                }
            }
        });

        binding.textViewForgotPassLoginFr.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Forgot Password");

            final View customLayout = getLayoutInflater().inflate(R.layout.forgot_pass_dialog, null);
            builder.setView(customLayout);

            builder.setPositiveButton("Send", (dialog, which) -> {
                EditText editText = customLayout.findViewById(R.id.fogot_password_editText);
                loginPresenter.forgotPassword(editText.getText().toString());
            });
            builder.setNegativeButton( android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void login() {
        String userName = binding.TextInputEditTextEmailLogin.getText().toString();
        String pass = binding.TextInputEditTextPassLogin.getText().toString();
        if ( checkValidation() && isValidEmail(userName)) {
            loginPresenter.login(userName, pass);
        }
    }

    private boolean checkValidation() {
        if (binding.TextInputEditTextEmailLogin.getText() != null && !binding.TextInputEditTextEmailLogin.getText().toString().isEmpty() && binding.TextInputEditTextPassLogin.getText() != null && !binding.TextInputEditTextPassLogin.getText().toString().isEmpty())
            return true;
        else {
            binding.textViewMessageLogin.setText(R.string.please_fill_all_data);
            return false;
        }
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Toast.makeText(binding.getRoot().getContext(), "LoggedIn Successfully", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void OnFailure(String message) {

        binding.textViewMessageLogin.setText(message);
    }

    @Override
    public void onSuccessPass() {
        Log.i("send email", "onSuccessPass:email has been sent");
        Toast.makeText(getContext(), R.string.send_email_toast, Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);
    }

    private boolean isValidEmail(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}