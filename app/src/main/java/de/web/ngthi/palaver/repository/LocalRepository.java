package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.MockupData;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

@Module
public class LocalRepository implements IRepository {

    private LocalUser user;

    @Inject
    public LocalRepository() {
        Log.d(getClass().getSimpleName(), "=========CONSTRUCTOR=========");
    }

    @Override
    public void setLocalUser(@NonNull String username, @NonNull String password) {
        user = new LocalUser(username, password);
    }

    //TODO mockupData
    private LocalUser getLocalUser() {
        return user;
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username) {
        return Single.just(getLocalUser().getUsername().equals(username));
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username, @NonNull String password) {
        boolean matchingName = getLocalUser().getUsername().equals(username);
        boolean matchingPassword = getLocalUser().getPassword().equals(password);
        return Single.just(matchingName && matchingPassword);
    }

    @Override
    public Single<Boolean> isValidNewUser(@NonNull String username, @NonNull String password) {
        return Single.just(false);
    }

    @Override
    public Completable changePassword(@NonNull String newPassword) {
        return Completable.complete();
    }

    @Override
    public Completable refreshToken() {
        return Completable.complete();
    }

    @Override
    public Completable sendMessage(@NonNull String friend, @NonNull String message) {
        Message m = new Message(getLocalUser(), new User(friend), message, DateTime.now());
        getLocalUser().addMessage(friend, m);
        return Completable.complete();
    }

    @Override
    public Single<List<Message>> getMessagesFrom(@NonNull String friend) {
        return Single.just(getLocalUser().getSortedMessages(new User(friend)));
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime) {
        Message dtm = new Message(null, null, null, DateTime.parse(dateTime));
        List<Message> messages = getLocalUser().getSortedMessages(new User(friend));
        List<Message> messagesOffset = new LinkedList<>();
        for(Message m : messages)
            if(m.compareTo(dtm) > 0)
                messagesOffset.add(m);
        return Single.just(messagesOffset);
    }

    @Override
    public Completable addFriend(@NonNull String friend) {
        getLocalUser().addFriend(new User(friend));
        return Completable.complete();
    }

    @Override
    public Completable removeFriend(@NonNull String friend) {
        getLocalUser().removeFriend(new User(friend));
        return Completable.complete();
    }

    @Override
    public Single<List<User>> getFriendList() {
        List<User> friends = MockupData.getLocalUser().getSortedFriendList();
        return Single.just(friends);
    }
}
