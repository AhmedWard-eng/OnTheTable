package com.mad.iti.onthetable.ui.authentication.singup.presenter;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationRepo;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpWithGoogleDelegate;
import com.mad.iti.onthetable.ui.authentication.singup.view.SignUpViewInterface;

public class SignUpPresenter implements SignUpPresenterInterface, SignUpDelegate, SignUpWithGoogleDelegate {

    private AuthenticationRepo authenticationRepo;
    private SignUpViewInterface signUpViewInterface;

    private static SignUpPresenter signUpPresenter;

    public static SignUpPresenter getInstance(SignUpViewInterface signUpViewInterface) {
        if (signUpPresenter == null) {
            signUpPresenter = new SignUpPresenter(signUpViewInterface);
        }
        return signUpPresenter;
    }

    private SignUpPresenter(SignUpViewInterface signUpViewInterface) {
        authenticationRepo = AuthenticationFireBaseRepo.getInstance();
        this.signUpViewInterface = signUpViewInterface;
    }

    @Override
    public void signUp(String email, String pass) {
        authenticationRepo.signUp(email, pass, this);
    }

    @Override
    public void signUpWithGoogle(AuthCredential authCredential) {
        authenticationRepo.signUpWithGoogle(authCredential, this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        signUpViewInterface.onSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        signUpViewInterface.OnFailure(message);
    }

    @Override
    public void onSuccessGoogle() {
        signUpViewInterface.onSuccessGoogle();
    }

    @Override
    public void onFailureGoogle(String message) {
        signUpViewInterface.OnFailure(message);
    }
}
