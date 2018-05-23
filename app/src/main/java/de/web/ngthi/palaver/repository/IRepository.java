package de.web.ngthi.palaver.repository;

import java.util.List;

import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IRepository {

    void setLocalUser(LocalUser user);

    //user
    Single<Boolean> isValidUser(String username);
    Single<Boolean> isValidUser(String username, String password);
    Single<Boolean> isValidNewUser(String username, String password);
    Completable changePassword(String newPassword);
    Completable refreshToken();

    //message
    Completable sendMessage(String friend, String message);
    Single<List<Message>> getMessagesFrom(String friend);
    Single<List<Message>> getMessageFromOffset(String friend, String dateTime);

    //friend
    Completable addFriend(String friend);
    Completable removeFriend(String friend);
    Single<List<User>> getFriendList();
}
