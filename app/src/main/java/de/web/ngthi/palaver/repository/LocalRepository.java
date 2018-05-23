package de.web.ngthi.palaver.repository;

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
    }

    @Override
    public void setLocalUser(LocalUser user) {
        this.user = user;
    }

    @Override
    public Single<Boolean> isValidUser(String username) {
        return Single.just(user.getUsername().equals(username));
    }

    @Override
    public Single<Boolean> isValidUser(String username, String password) {
        boolean matchingName = user.getUsername().equals(username);
        boolean matchingPassword = user.getPassword().equals(password);
        return Single.just(matchingName && matchingPassword);
    }

    @Override
    public Single<Boolean> isValidNewUser(String username, String password) {
        return Single.just(false);
    }

    @Override
    public Completable changePassword(String newPassword) {
        return Completable.complete();
    }

    @Override
    public Completable refreshToken() {
        return Completable.complete();
    }

    @Override
    public Completable sendMessage(String friend, String message) {
        Message m = new Message(user, new User(friend), message, DateTime.now());
        user.addMessage(m);
        return Completable.complete();
    }

    @Override
    public Single<List<Message>> getMessagesFrom(String friend) {
        return Single.just(user.getSortedMessages(new User(friend)));
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(String friend, String dateTime) {
        Message dtm = new Message(null, null, null, DateTime.parse(dateTime));
        List<Message> messages = user.getSortedMessages(new User(friend));
        List<Message> messagesOffset = new LinkedList<>();
        for(Message m : messages)
            if(m.compareTo(dtm) > 0)
                messagesOffset.add(m);
        return Single.just(messagesOffset);
    }

    @Override
    public Completable addFriend(String friend) {
        user.addFriend(new User(friend));
        return Completable.complete();
    }

    @Override
    public Completable removeFriend(String friend) {
        user.removeFriend(new User(friend));
        return Completable.complete();
    }

    @Override
    public Single<List<User>> getFriendList() {
        List<User> friends = MockupData.getLocalUser().getSortedFriendList();
        return Single.just(friends);
    }
}
