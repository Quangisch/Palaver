package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.mvp.model.LocalUser;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import io.reactivex.Single;

@Module
public class LocalRepository implements IRepository {

    //TODO: SQLITE synchronize
    private static final String TAG = LocalRepository.class.getSimpleName();
    private LocalUser user;

    @Inject
    public LocalRepository() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    public void setLocalUser(@NonNull String username, @NonNull String password) {
        user = new LocalUser(username, password);
    }

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
    public Single<ServerReplyType> changePassword(@NonNull String newPassword) {
        return Single.just(ServerReplyType.USER_PASSWORD_OK);
    }

    @Override
    public Single<ServerReplyType> refreshToken(String token) {
        return Single.just(ServerReplyType.USER_PUSHTOKEN_OK);
    }

    @Override
    public Single<ServerReplyType> sendMessage(@NonNull String friend, @NonNull String message) {
        Message m = new Message(getLocalUser(), new User(friend), message, DateTime.now(), Message.Status.PENDING);
        getLocalUser().addMessage(friend, m);
        return Single.just(ServerReplyType.MESSAGE_SEND_OK);
    }

    @Override
    public Single<List<Message>> getMessagesFrom(@NonNull String friend) {
        return Single.just(getLocalUser().getSortedMessages(new User(friend)));
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime) {
        Message dtm = new Message(null, null, null, DateTime.parse(dateTime), Message.Status.SENT);
        List<Message> messages = getLocalUser().getSortedMessages(new User(friend));
        List<Message> messagesOffset = new LinkedList<>();
        for(Message m : messages)
            if(m.compareTo(dtm) > 0)
                messagesOffset.add(m);
        return Single.just(messagesOffset);
    }

    @Override
    public Single<ServerReplyType> addFriend(@NonNull String friend) {
        getLocalUser().addFriend(new User(friend));
        return Single.just(ServerReplyType.FRIENDS_ADD_OK);
    }

    @Override
    public Single<ServerReplyType> removeFriend(@NonNull String friend) {
        getLocalUser().removeFriend(new User(friend));
        return Single.just(ServerReplyType.FRIENDS_REMOVE_OK);
    }

    @Override
    public Single<List<User>> getFriendList() {
        List<User> friends = getLocalUser().getSortedFriendList();
        return Single.just(friends);
    }
}
