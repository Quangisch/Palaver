package de.web.ngthi.palaver.network.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import de.web.ngthi.palaver.PalaverApplication;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        PalaverApplication.getInstance().getRepository().refreshToken(refreshedToken);
    }
}
