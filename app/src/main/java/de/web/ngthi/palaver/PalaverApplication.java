package de.web.ngthi.palaver;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import javax.inject.Inject;

import de.web.ngthi.palaver.di.DaggerDataRepositoryComponent;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.IRepository;

public class PalaverApplication extends MultiDexApplication {

    private static final String TAG = PalaverApplication.class.getSimpleName();

    @Inject
    public DataRepository repository;
    private SharedPreferences sharedPref;
    private static PalaverApplication instance;

    public PalaverApplication() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DaggerDataRepositoryComponent.create().inject(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        initLocalUser();
    }

    public void initLocalUser() {
        if(hasLocalUserData()) {
            String username = sharedPref.getString(getString(R.string.saved_username_key), null);
            String password = sharedPref.getString(getString(R.string.saved_password_key), null);
            if(username != null && password != null)
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
        Log.d(TAG, String.format("savedToSharedPreferences->%s:%s", key, value));
        sharedPref.edit().putString(key, value).apply();
    }

    public void saveLocalUserData(@NonNull String username, @NonNull String password) {
        saveToSharedPreferences(getString(R.string.saved_username_key), username);
        saveToSharedPreferences(getString(R.string.saved_password_key), password);
        initLocalUser();
    }

    public void clearLocalUserData() {
        sharedPref.edit().clear().apply();
    }

    //usage in network/service/MyFirebaseInstanceIDService.class -> Dagger Injection?
    public static PalaverApplication getInstance() {
        return instance;
    }
}
