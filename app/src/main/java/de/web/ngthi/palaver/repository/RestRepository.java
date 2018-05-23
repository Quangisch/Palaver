package de.web.ngthi.palaver.repository;

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
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;

@Module
public class RestRepository implements IRepository {

    private LocalUser user;

    private UserService userService;
    private FriendsService friendsService;
    private MessageService messageService;

    @Inject
    public RestRepository(RestController controller) {
        Retrofit retrofit = controller.provideRetrofit();
        userService = controller.provideUserService(retrofit);
        friendsService = controller.provideFriendService(retrofit);
        messageService = controller.provideMessageService(retrofit);
    }

    @Override
    public void setLocalUser(LocalUser user) {
        this.user = user;
    }

    public Single<Boolean> isValidUser(String username) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .build();
        return Single.fromCallable(() -> isExistingUser(userService.validate(request).blockingGet()));
    }

    public Single<Boolean> isValidUser(String username, String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> isExistingUser(userService.validate(request).blockingGet()));
    }

    public Single<Boolean> isValidNewUser(String username, String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        return Single.fromCallable(() -> isExistingUser(userService.register(request).blockingGet()));
    }

    private ServerRequest.Builder localUser() {
        return new ServerRequest.Builder()
                .username(user.getUsername())
                .password(user.getPassword());
    }

    @Override
    public Completable changePassword(String newPassword) {
        ServerRequest request = localUser().newPassword(newPassword).build();
        return Completable.fromCallable(() -> userService.changePassword(request).blockingGet());
    }

    @Override
    public Completable refreshToken() {
        ServerRequest request = localUser().token().build();
        return Completable.fromCallable(() -> userService.refreshToken(request).blockingGet());
    }

    @Override
    public Completable sendMessage(String recipient, String message) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .data(message)
                .mimetype()
                .build();
        return Completable.fromCallable(() -> messageService.sendMessage(request).blockingGet());
    }

    @Override
    public Single<List<Message>> getMessagesFrom(String recipient) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .build();
        return Single.fromCallable(() -> ServerDataMapper.mapToMessages(messageService.getMessages(request).blockingGet()));
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(String recipient, String offset) {
        ServerRequest request = localUser()
                .recipient(recipient)
                .offset(offset)
                .build();
        return Single.fromCallable(() -> ServerDataMapper.mapToMessages(messageService.getMessages(request).blockingGet()));

    }

    @Override
    public Completable addFriend(String friend) {
        ServerRequest request = localUser()
                .friend(friend)
                .build();
        return Completable.fromCallable(() -> friendsService.addFriend(request).blockingGet());
    }

    @Override
    public Completable removeFriend(String friend) {
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
}
