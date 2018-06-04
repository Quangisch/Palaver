package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import de.web.ngthi.palaver.network.controller.FriendsService;
import de.web.ngthi.palaver.network.controller.MessageService;
import de.web.ngthi.palaver.network.controller.RestController;
import de.web.ngthi.palaver.network.controller.UserService;
import de.web.ngthi.palaver.network.dto.ServerDataMapper;
import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.network.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.Retrofit;

@Module
public class RestRepository implements IRepository {

    private static final String TAG = RestRepository.class.getSimpleName();

    private String username;
    private String password;

    private UserService userService;
    private FriendsService friendsService;
    private MessageService messageService;

    @Inject
    public RestRepository(RestController controller) {
        Log.d(TAG, "==============constructor==============");
        Retrofit retrofit = controller.provideRetrofit();
        userService = controller.provideUserService(retrofit);
        friendsService = controller.provideFriendService(retrofit);
        messageService = controller.provideMessageService(retrofit);
    }

    @Override
    public void setLocalUser(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    private ServerRequest.Builder localUser() {
        return new ServerRequest.Builder()
                .username(username)
                .password(password);
    }

    public Single<ServerReplyType> isValidUser(@NonNull String username) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(userService.validate(request).blockingGet()));
    }

    public Single<ServerReplyType> isValidUser(@NonNull String username, @NonNull String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(userService.validate(request).blockingGet()));
    }

    public Single<ServerReplyType> registerNewUser(@NonNull String username, @NonNull String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(userService.register(request).blockingGet()));
    }

    @Override
    public Single<ServerReplyType> changePassword(@NonNull String newPassword) {
        ServerRequest request = localUser().newPassword(newPassword).build();
        return Single.fromCallable(() -> ServerReplyType.getType(userService.changePassword(request).blockingGet()));
    }

    @Override
    public Single<ServerReplyType> refreshToken(String token) {
        ServerRequest request = localUser().token(token).build();
        return Single.fromCallable(() -> ServerReplyType.getType(userService.refreshToken(request).blockingGet()));
    }

    @Override
    public Single<ServerReplyType> sendMessage(@NonNull String recipient, @NonNull String message) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .data(message)
                .mimetype()
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(messageService.sendMessage(request).blockingGet()));
    }

    @Override
    public Single<List<Message>> getMessagesFrom(@NonNull String recipient) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .build();
        return Single.fromCallable(() -> ServerDataMapper.mapToMessages(messageService.getMessages(request).blockingGet()));
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(@NonNull String recipient, @NonNull String offset) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .offset(offset)
                .build();
        return Single.fromCallable(() -> ServerDataMapper.mapToMessages(messageService.getMessagesOffset(request).blockingGet()));

    }

    @Override
    public Single<ServerReplyType> addFriend(@NonNull String friend) {
        ServerRequest request = localUser()
                .friend(friend)
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(friendsService.addFriend(request).blockingGet()));
    }

    @Override
    public Single<ServerReplyType> removeFriend(@NonNull String friend) {
        ServerRequest request = localUser()
                .friend(friend)
                .build();
        return Single.fromCallable(() -> ServerReplyType.getType(friendsService.removeFriend(request).blockingGet()));
    }

    public Single<List<User>> getFriendList() {
        ServerRequest request = localUser().build();
        return Single.fromCallable(() -> ServerDataMapper.mapToFriends(friendsService.getFriends(request).blockingGet()));
    }
}
