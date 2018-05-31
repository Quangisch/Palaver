package de.web.ngthi.palaver.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.view.friends.FriendsActivity;
import de.web.ngthi.palaver.mvp.view.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();

    public SplashScreen() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        PalaverApplication application = (PalaverApplication) getApplication();

        Class startingActivity = application.hasLocalUserData() ? FriendsActivity.class : LoginActivity.class;
        Intent intent = new Intent(this, startingActivity);
        startActivity(intent);
    }
}
