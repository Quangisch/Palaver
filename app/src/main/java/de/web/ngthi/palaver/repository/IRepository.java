package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;

import java.util.List;

import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IRepository {

    void setLocalUser(LocalUser user);

    //user
    Single<Boolean> isValidUser(@NonNull String username);
    Single<Boolean> isValidUser(String username, @NonNull String password);
    Single<Boolean> isValidNewUser(@NonNull String username, @NonNull String password);
    Completable changePassword(@NonNull String newPassword);
    Completable refreshToken();

    //message
    Completable sendMessage(@NonNull String friend, @NonNull String message);
    Single<List<Message>> getMessagesFrom(@NonNull String friend);
    Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime);

    //friend
    Completable addFriend(@NonNull String friend);
    Completable removeFriend(@NonNull String friend);
    Single<List<User>> getFriendList();
}
