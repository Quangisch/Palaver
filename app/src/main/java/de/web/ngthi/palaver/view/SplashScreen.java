package de.web.ngthi.palaver.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.view.friends.FriendsActivity;
import de.web.ngthi.palaver.view.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

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
