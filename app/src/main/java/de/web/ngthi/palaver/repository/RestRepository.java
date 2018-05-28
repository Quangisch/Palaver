package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.controller.FriendsService;
import de.web.ngthi.palaver.controller.MessageService;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.dto.ServerDataMapper;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerReplyType;
import de.web.ngthi.palaver.dto.ServerRequest;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Module
public class RestRepository implements IRepository {

    private String username;
    private String password;

    private UserService userService;
    private FriendsService friendsService;
    private MessageService messageService;

    @Inject
    public RestRepository(RestController controller) {
        Log.d(getClass().getSimpleName(), "=========CONSTRUCTOR=========");
//        DaggerRestComponent.create().inject(this);
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

    public Single<Boolean> isValidUser(@NonNull String username) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .build();
        return Single.fromCallable(() -> isExistingUser(userService.validate(request).blockingGet()));
    }

    public Single<Boolean> isValidUser(@NonNull String username, @NonNull String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> isValidUser(userService.validate(request).blockingGet()));
    }

    public Single<Boolean> isValidNewUser(@NonNull String username, @NonNull String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> isExistingUser(userService.register(request).blockingGet()));
    }

    private ServerRequest.Builder localUser() {
        return new ServerRequest.Builder()
                .username(username)
                .password(password);
    }

    @Override
    public Completable changePassword(@NonNull String newPassword) {
        ServerRequest request = localUser().newPassword(newPassword).build();
        return Completable.fromCallable(() -> userService.changePassword(request).blockingGet());
    }

    @Override
    public Completable refreshToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        ServerRequest request = localUser().token(token).build();
        userService.refreshToken(request)
                .subscribeOn(Schedulers.computation())
                .subscribe()
                .dispose();
        return Completable.complete();
    }

    @Override
    public Completable sendMessage(@NonNull String recipient, @NonNull String message) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .data(message)
                .mimetype()
                .build();
        return Completable.fromCallable(() -> messageService.sendMessage(request).blockingGet());
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
        return Single.fromCallable(() -> ServerDataMapper.mapToMessages(messageService.getMessages(request).blockingGet()));

    }

    @Override
    public Completable addFriend(@NonNull String friend) {
        ServerRequest request = localUser()
                .friend(friend)
                .build();
        return Completable.fromCallable(() -> friendsService.addFriend(request).blockingGet());
    }

    @Override
    public Completable removeFriend(@NonNull String friend) {
        ServerRequest request = localUser()
                .friend(friend)
                .build();
        return Completable.fromCallable(() -> friendsService.removeFriend(request).blockingGet());
    }

    public Single<List<User>> getFriendList() {
        ServerRequest request = localUser().build();

        return Single.fromCallable(() -> ServerDataMapper.mapToFriends(friendsService.getFriends(request).blockingGet()));

    }

    private boolean isExistingUser(ServerReply reply) {
        return ServerReplyType.isType(ServerReplyType.USER_VALIDATE_OK, reply)
                || ServerReplyType.isType(ServerReplyType.USER_VALIDATE_FAILED_PASSWORD, reply);
    }

    private boolean isValidUser(ServerReply reply) {
        return ServerReplyType.isType(ServerReplyType.USER_VALIDATE_OK, reply);
    }
}
