package com.mad.iti.onthetable.model.repositories.authRepo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseAuthWrapper;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignInDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpDelegate;

public class AuthenticationFireBaseRepo implements AuthenticationRepo {

    private static final String TAG = "AuthenticationFireBase";

    private FireBaseAuthWrapper fireBaseAuthWrapper;
    private static AuthenticationFireBaseRepo authenticationFireBaseRepo;

    public static synchronized AuthenticationFireBaseRepo getInstance() {
        if (authenticationFireBaseRepo == null) {
            authenticationFireBaseRepo = new AuthenticationFireBaseRepo();
        }
        return authenticationFireBaseRepo;
    }

    private AuthenticationFireBaseRepo() {
        this.fireBaseAuthWrapper = FireBaseAuthWrapper.getInstance();
    }

    @Override
    public void signIn(String email, String pass, SignInDelegate signInDelegate) {
        fireBaseAuthWrapper.getAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signInDelegate.onSuccess(task.getResult().getUser());
                } else {
                    // If sign in fails, display a message to the user.
                    signInDelegate.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void signUp(String email, String pass, SignUpDelegate signUpDelegate) {
        fireBaseAuthWrapper.getAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signUpDelegate.onSuccess(task.getResult().getUser());
                } else {
                    // If sign in fails, display a message to the user.
                    signUpDelegate.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void logout() {
        Log.d(TAG, "logout: ");
        fireBaseAuthWrapper.logout();
    }

    @Override
    public FirebaseUser getUser() {
        return fireBaseAuthWrapper.getCurrentUser();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return fireBaseAuthWrapper.getAuth();
    }

    public boolean isAuthenticated() {
        return AuthenticationFireBaseRepo.getInstance().getUser() != null;
    }
}
