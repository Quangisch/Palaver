package de.web.ngthi.palaver;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import de.web.ngthi.palaver.view.friends.FriendsActivity;
import de.web.ngthi.palaver.view.message.MessageActivity;

@Singleton
public class PalaverApplication extends Application {

    private LocalUser localUser;
    private SharedPreferences sharedPref;

    @Inject
    public PalaverApplication() {
        //TODO no singleton!!!
        setLocalUser(MockupData.getLocalUser());
        Log.d("APPLICATION CONSTRUCTOR", "constructor");
    }

    @Override
    public void onCreate() {
        super.onCreate();

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

    public void removeFromSharedPreferences(String key) {
        sharedPref.edit().remove(key).apply();
    }


    public String getFromSharedPreferences(String key) {
        return sharedPref.getString(key, null);
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public LocalUser getLocalUser() {
        return localUser;
    }

}
