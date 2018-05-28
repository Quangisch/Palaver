package de.web.ngthi.palaver;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import de.web.ngthi.palaver.di.DaggerDataRepositoryComponent;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.IRepository;
import de.web.ngthi.palaver.view.friends.FriendsActivity;
import de.web.ngthi.palaver.view.login.LoginActivity;

public class PalaverApplication extends Application {

    @Inject
    public DataRepository repository;
    private SharedPreferences sharedPref;

    public PalaverApplication() {
        Log.d("APPLICATION CONSTRUCTOR", "==============constructor===============");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerDataRepositoryComponent.create().inject(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        initLocalUser();
        saveLocalUserData("Peter32", "pw31");


        Class c = LoginActivity.class;
        if(hasLocalUserData())
            c = FriendsActivity.class;

//        Testing
//        c = MessageActivity.class;

        Intent intent = new Intent(this, c);

//        intent.putExtra(getString(R.string.intent_friend_message), getLocalUser().getSortedFriendList().get(0).getUsername());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


        startActivity(intent);
    }

    public void initLocalUser() {
        if(hasLocalUserData()) {
            String username = sharedPref.getString(getString(R.string.saved_username_key), null);
            String password = sharedPref.getString(getString(R.string.saved_password_key), null);
            repository.setLocalUser(username, password);
        }
    }

    public IRepository getRepository() {
        return repository;
    }

    public String getLocalUsername() {
        return sharedPref.getString(getString(R.string.saved_username_key), null);
    }

    public String getLocalPassword() {
        return sharedPref.getString(getString(R.string.saved_password_key), null);
    }

    public boolean hasLocalUserData() {
        return sharedPref.contains(getString(R.string.saved_username_key)) && sharedPref.contains(getString(R.string.saved_password_key));
    }

    public void saveToSharedPreferences(String key, String value) {
        sharedPref.edit().putString(key, value).apply();
    }

    public void saveLocalUserData(@NonNull String username, @NonNull String password) {
        saveToSharedPreferences(getString(R.string.saved_username_key), username);
        saveToSharedPreferences(getString(R.string.saved_password_key), password);
    }

    public void clearLocalUserData() {
        sharedPref.edit().clear().apply();
    }
}
