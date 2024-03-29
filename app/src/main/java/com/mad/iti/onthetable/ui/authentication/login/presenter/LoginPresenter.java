package com.mad.iti.onthetable.ui.authentication.login.presenter;

import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationFireBaseRepo;
import com.mad.iti.onthetable.model.repositories.authRepo.AuthenticationRepo;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.ForgetPassDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignInDelegate;
import com.mad.iti.onthetable.ui.authentication.login.view.LoginViewInterface;

public class LoginPresenter implements LoginPresenterInterface , SignInDelegate , ForgetPassDelegate {

    private AuthenticationRepo authenticationRepo;
    private LoginViewInterface loginViewInterface;

    private static LoginPresenter loginPresenter;
    public static LoginPresenter getInstance(LoginViewInterface loginViewInterface) {
        if (loginPresenter == null){
            loginPresenter = new LoginPresenter(loginViewInterface);
        }
        return loginPresenter;
    }
    private LoginPresenter(LoginViewInterface loginViewInterface){
        authenticationRepo = AuthenticationFireBaseRepo.getInstance();
        this.loginViewInterface = loginViewInterface;
    }

    @Override
    public void login(String email, String pass) {
        authenticationRepo.signIn(email,pass,this);
    }

    @Override
    public void forgotPassword(String email) {
        authenticationRepo.resetPassword(email,this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        loginViewInterface.onSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        loginViewInterface.OnFailure(message);
    }

    @Override
    public void onSuccessPass() {
        loginViewInterface.onSuccessPass();
    }

}
