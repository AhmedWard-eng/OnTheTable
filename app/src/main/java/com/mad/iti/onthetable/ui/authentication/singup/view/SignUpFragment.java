package com.mad.iti.onthetable.ui.authentication.singup.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentSignUpBinding;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenter;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenterInterface;


public class SignUpFragment extends Fragment implements SignUpViewInterface {

    FragmentSignUpBinding binding;
    SignUpPresenterInterface signUpPresenter;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "SignUpFragment";

    TextView textViewSkip;
    Context context;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        textViewSkip = binding.skipTextViewSignup;

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        binding.textInputEditTextPassSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPassContainNumber(s.toString())) {
                    binding.textInputEditTextPassSignUp.setError(getString(R.string.minmum_one_number));
                } else if (!isPassContainSpecialChar(s.toString())) {
                    binding.textInputEditTextPassSignUp.setError(getString(R.string.minimum_one_special_symbol));
                } else if (!isPassContainUpperCase(s.toString())) {
                    binding.textInputEditTextPassSignUp.setError(getString(R.string.minimum_one_uppercase));
                } else if (!isPassLengthGT8(s.toString())) {
                    binding.textInputEditTextPassSignUp.setError(getString(R.string.At_least__8_character));
                } else {
                    binding.textInputEditTextPassSignUp.setError(null);
                }
            }
        });


        binding.textInputEditTextEmailSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(s.toString())) {
                    binding.textInputEditTextEmailSignUp.setError(getString(R.string.please_enter_valid_email));
                } else {
                    binding.textInputEditTextEmailSignUp.setError(null);
                }
            }
        });
        binding.textInputEditTextConfirmPassSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!checkPassEquality()) {
                    binding.textInputEditTextConfirmPassSignUp.setError(getString(R.string.confirm_password_doesnt_match_password));
                } else {
                    binding.textInputEditTextConfirmPassSignUp.setError(null);
                }
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);

        binding.googleSignIN.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            someActivityResultLauncher.launch(intent);
        });

        firebaseAuth = FirebaseAuth.getInstance();


        super.onViewCreated(view, savedInstanceState);
    }


    private void signUpWithGoogle() {
    }

    private void signUp() {
        String userName = binding.textInputEditTextEmailSignUp.getText().toString();
        String pass = binding.textInputEditTextPassSignUp.getText().toString();
        if (isAllDataFilled() && checkValidation(userName, pass)) {
            signUpPresenter.signUp(userName, pass);
        }
    }

    private boolean checkValidation(String userName, String pass) {
        return isValidEmail(userName) && isPassLengthGT8(pass) && isPassContainNumber(pass) && isPassContainSpecialChar(pass) && isPassContainUpperCase(pass) && checkPassEquality();
    }

    private boolean isAllDataFilled() {
        Log.d(TAG, "isAllDataFilled: ");
        if (binding.textInputEditTextEmailSignUp.getText() != null && !binding.textInputEditTextEmailSignUp.getText().toString().isEmpty() && binding.textInputEditTextConfirmPassSignUp.getText() != null && !binding.textInputEditTextConfirmPassSignUp.getText().toString().isEmpty() && binding.textInputEditTextPassSignUp.getText() != null && !binding.textInputEditTextPassSignUp.getText().toString().isEmpty() )
            return true;
        else {
            binding.textViewMessage.setText(R.string.please_fill_all_data);
            return false;
        }
    }

    private boolean checkPassEquality() {
        return binding.textInputEditTextConfirmPassSignUp.getText().toString().equals(binding.textInputEditTextPassSignUp.getText().toString());
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Toast.makeText(binding.getRoot().getContext(), R.string.signup_successfully, Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);
    }

    @Override
    public void OnFailure(String message) {
        binding.textViewMessage.setText(message);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + context);
        this.context = context;
    }

    @Override
    public void onSuccessGoogle() {
        Toast.makeText(context, context.getString(R.string.sign_up_with_google_successfully), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);
    }

    @Override
    public void OnFailureGoogle(String message) {
        Toast.makeText(requireContext(),context.getString( R.string.sign_up_with_google_failed), Toast.LENGTH_SHORT).show();
    }


    private void displayToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (signInAccountTask.isSuccessful()) {
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            signUpPresenter.signUpWithGoogle(authCredential);
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    public boolean isPassContainSpecialChar(String pass) {
        String specialCharRegex = ".*[@#!$%^&+=].*";
        String UpperCaseRegex = ".*[A-Z].*";
        String NumberRegex = ".*[0-9].*";
        return pass.matches(specialCharRegex);
    }

    public boolean isPassContainUpperCase(String pass) {
        String UpperCaseRegex = ".*[A-Z].*";
        return pass.matches(UpperCaseRegex);
    }

    public boolean isPassContainNumber(String pass) {
        String NumberRegex = ".*[0-9].*";
        return pass.matches(NumberRegex);
    }


    public boolean isPassLengthGT8(String pass) {
        return pass.length() >= 8;
    }


    private boolean isValidEmail(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}