package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;

import java.util.List;

import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import io.reactivex.Single;

public interface IRepository {

    void setLocalUser(@NonNull String username, @NonNull String password);

    //user
    Single<Boolean> isValidUser(@NonNull String username);
    Single<Boolean> isValidUser(@NonNull String username, @NonNull String password);
    Single<Boolean> isValidNewUser(@NonNull String username, @NonNull String password);
    Single<ServerReplyType> changePassword(@NonNull String newPassword);
    Single<ServerReplyType> refreshToken(String token);
    //message
    Single<ServerReplyType> sendMessage(@NonNull String friend, @NonNull String message);
    Single<List<Message>> getMessagesFrom(@NonNull String friend);
    Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime);

    //friend
    Single<ServerReplyType> addFriend(@NonNull String friend);
    Single<ServerReplyType> removeFriend(@NonNull String friend);
    Single<List<User>> getFriendList();
}
