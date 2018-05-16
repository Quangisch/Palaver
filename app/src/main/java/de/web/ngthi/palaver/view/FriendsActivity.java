package de.web.ngthi.palaver.view;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.di.DaggerAppComponent;

public class FriendsActivity extends Activity {

    @Inject
    public PalaverApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        DaggerAppComponent.builder().build().inject(this);

    }
}
