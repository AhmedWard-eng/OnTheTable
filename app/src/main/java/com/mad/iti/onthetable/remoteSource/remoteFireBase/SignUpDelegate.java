package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpDelegate {
    void onSuccess(FirebaseUser user);
   void onFailure(String message);
}
