package com.mad.iti.onthetable.model.repositories.authRepo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.FireBaseAuthWrapper;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignInDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpWithGoogleDelegate;

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
        fireBaseAuthWrapper.getAuth().signInWithEmailAndPassword(email, pass).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signInDelegate.onFailure(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signInDelegate.onSuccess(authResult.getUser());
                    }
                });

    }

    @Override
    public void signUp(String email, String pass, SignUpDelegate signUpDelegate) {
        fireBaseAuthWrapper.getAuth().createUserWithEmailAndPassword(email, pass)

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpDelegate.onFailure(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signUpDelegate.onSuccess(authResult.getUser());
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

    @Override
    public void signUpWithGoogle(SignUpWithGoogleDelegate signUpWithGoogleDelegate) {

    }

    public boolean isAuthenticated() {
        return AuthenticationFireBaseRepo.getInstance().getUser() != null;
    }
}
