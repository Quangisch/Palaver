package de.web.ngthi.palaver;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import de.web.ngthi.palaver.view.login.LoginActivity;
import de.web.ngthi.palaver.view.message.MessageActivity;
import lombok.Getter;
import lombok.Setter;

@Module
public class PalaverApplication extends Application {

    private LocalUser localUser;
    private SharedPreferences sharedPref;

    @Inject
    public PalaverApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = new Intent(this, LoginActivity.class);
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

    private void initData() {
        LocalUser local = new LocalUser("Peter", "Password");

        List<User> friends = new LinkedList<>();
        friends.add(new User("Anna"));
        friends.add(new User("Anton"));
        friends.add(new User("Otto"));
        friends.add(new User("Lisa"));
        friends.add(new User("Ray"));

        for(User f : friends)
            local.addFriend(f);

        DateTime date = new DateTime(2018, 10, 3, 5, 30, 56);
        String text = getString(R.string.large_text);
        String[] textFragments = text.split("\\r\\n|[\\r\\n]");
        Random r = new Random();
        for(int i = 0; i < textFragments.length; i++) {
            long offset = r.nextInt(1000000);
            int user = r.nextInt();
            date = date.plus(offset);
            int friendIndex = r.nextInt(friends.size());
            User friend = local.getSortedFriendList().get(friendIndex);
            Message m = new Message(user % 2 == 0 ? local : friend, user % 2 == 0 ? friend : local, textFragments[i].replaceAll("\\t\\n", ""), date);
            local.addMessage(m);
        }

        setLocalUser(local);
    }
}
