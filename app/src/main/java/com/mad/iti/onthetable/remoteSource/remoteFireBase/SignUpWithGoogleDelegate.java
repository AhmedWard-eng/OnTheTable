package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpWithGoogleDelegate {
    void onSuccessGoogle();
    void onFailureGoogle(String message);
}
