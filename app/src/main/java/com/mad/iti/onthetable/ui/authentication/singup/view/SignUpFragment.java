package com.mad.iti.onthetable.ui.authentication.singup.view;

import android.app.Activity;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mad.iti.onthetable.MainActivity;
import com.mad.iti.onthetable.R;
import com.mad.iti.onthetable.databinding.FragmentSignUpBinding;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenter;
import com.mad.iti.onthetable.ui.authentication.singup.presenter.SignUpPresenterInterface;

import java.util.Objects;
import java.util.concurrent.Executor;


public class SignUpFragment extends Fragment implements SignUpViewInterface {

    FragmentSignUpBinding binding;
    SignUpPresenterInterface signUpPresenter;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

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

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);

        binding.googleSignIN.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            someActivityResultLauncher.launch(intent);
        });

        firebaseAuth = FirebaseAuth.getInstance();


        super.onViewCreated(view, savedInstanceState);
    }

    private void signUpWithGoogle() {
        signUpPresenter.signUpWithGoogle();
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
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);
    }

    @Override
    public void OnFailure(String message) {
        binding.textInputLayoutEmailSignUP.setError(message);
        binding.textInputLayoutPassWord.setError(message);
        binding.confirmPasswordInputLayout.setError(message);
    }

    @Override
    public void onSuccessGoogle() {
        Toast.makeText(requireContext(), "SignUp with google successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        binding.getRoot().getContext().startActivity(intent);
    }

    @Override
    public void OnFailureGoogle(String message) {
        Toast.makeText(requireContext(), "SignUp with google Failed!", Toast.LENGTH_SHORT).show();
    }



    private void displayToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() ==  Activity.RESULT_OK) {
                        Intent data = result.getData();
//                        doSomeOperations();
                        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                        // check condition
                        if (signInAccountTask.isSuccessful()) {
                            // When google sign in successful initialize string
                            String s = "Google sign in successful";
                            // Display Toast
                            displayToast(s);
                            // Initialize sign in account
                            try {
                                // Initialize sign in account
                                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                                // Check condition
                                if (googleSignInAccount != null) {
                                    // When sign in account is not equal to null initialize auth credential
                                    AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                                    // Check credential
                                    firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener(requireActivity(), new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            displayToast("Firebase authentication successful");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            displayToast("Authentication Failed :" + e.getMessage());
                                        }
                                    });
                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
}