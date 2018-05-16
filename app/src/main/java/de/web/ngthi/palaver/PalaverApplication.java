package de.web.ngthi.palaver;

import android.app.Application;
import android.content.Intent;

import org.joda.time.DateTime;

import java.util.Random;

import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import de.web.ngthi.palaver.view.login.LoginActivity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PalaverApplication extends Application {

    private LocalUser localUser;

    @Override
    public void onCreate() {
        super.onCreate();
//        initData();

//        DaggerRestComponent.builder().build().inject(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void initData() {
        LocalUser local = new LocalUser("Peter", "Password");
        User f1 = new User("Anna");
        local.addFriend(f1);

//        User f2 = new User("Anton");
//        local.addFriend(f2);

        DateTime date = new DateTime(2018, 10, 3, 5, 30, 56);
        String text = getString(R.string.large_text);
        String[] textFragments = text.split("\\r\\n|[\\r\\n]");
        Random r = new Random();
        for(int i = 0; i < textFragments.length; i++) {
            long offset = r.nextInt(1000000);
            int user = r.nextInt();
            date = date.plus(offset);
            Message m = new Message(user % 2 == 0 ? local : f1, user % 2 == 0 ? f1 : local, textFragments[i].replaceAll("\\t\\n", ""), date);
            local.addMessage(m);
        }

        setLocalUser(local);
    }
}
