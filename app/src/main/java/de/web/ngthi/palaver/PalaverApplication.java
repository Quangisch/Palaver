package de.web.ngthi.palaver;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.view.friends.FriendsActivity;
import de.web.ngthi.palaver.view.message.MessageActivity;

public class PalaverApplication extends Application {

    private static PalaverApplication instance;
    private LocalUser localUser;
    private SharedPreferences sharedPref;


    public PalaverApplication() {
        setLocalUser(MockupData.getLocalUser());
        Log.d("APPLICATION CONSTRUCTOR", "==============constructor===============");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Class c = FriendsActivity.class;
        Intent intent = new Intent(this, c);
        if(c == MessageActivity.class) {
            String friendName = getLocalUser().getSortedFriendList().get(0).getUsername();
            intent.putExtra(getString(R.string.intent_friend_message), friendName);
        }

        startActivity(intent);
    }

    public void saveToSharedPreferences(String key, String value) {
        sharedPref.edit().putString(key, value).apply();
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public void setLocalUser(String username, String password) {
        saveToSharedPreferences(getString(R.string.saved_username_key), username);
        saveToSharedPreferences(getString(R.string.saved_password_key), password);
        setLocalUser(new LocalUser(username, password));
    }

    public LocalUser getLocalUser() {
        return localUser;
    }

    public void clearLocalUser() {
        localUser = null;
        sharedPref.edit().clear().commit();
    }

    public static PalaverApplication getInstance() {
        return instance;
    }
}
