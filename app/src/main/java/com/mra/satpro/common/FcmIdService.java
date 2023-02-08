package com.mra.satpro.common;

import android.util.Log;

/*import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mra.satpro.utilerias.Constantes;

public class FcmIdService extends FirebaseInstanceIdService {

    private static final String TAG = "mFirebaseIIDService";
    private static final String SUBSCRIBE_TO = "partidaNueva";

    public FcmIdService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();


        String token = FirebaseInstanceId.getInstance().getToken();
        Constantes.TOKEN = token;
        // Once the token is generated, subscribe to topic with the userId
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
        Log.i(TAG, "onTokenRefresh completed with token: " + token);
    }

    private void sendRegistrationToServer(String refreshToken) {
        Log.e("refreshToken", refreshToken);
    }

}*/
