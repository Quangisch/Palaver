package de.web.ngthi.palaver.repository;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.controller.FriendsService;
import de.web.ngthi.palaver.controller.MessageService;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.Retrofit;

@Module
public class DataRepository {

    private RestController controller;
    private Validator validator;
    private PalaverApplication application;

    private UserService userService;
    private FriendsService friendsService;
    private MessageService messageService;

    @Inject
    public DataRepository (PalaverApplication application, RestController controller, Validator validator) {
        this.application = application;
        this.controller = controller;
        this.validator = validator;

        Retrofit retrofit = controller.provideRetrofit();
        userService = controller.provideUserService(retrofit);
        friendsService = controller.provideFriendService(retrofit);
        messageService = controller.provideMessageService(retrofit);
    }

    public Single<Boolean> isExistingUser(String username) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .build();
        ServerReply reply = userService.validate(request).blockingGet();
        return Single.just(validator.isExistingUser(reply));
    }

    public Single<Boolean> isValidLogin(String username, String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        ServerReply reply = userService.validate(request).blockingGet();
        return Single.just(validator.isExistingUser(reply));
    }

    public Single<Boolean> isValidRegister(String username, String password) {
        ServerRequest request = new ServerRequest.Builder()
                .username(username)
                .password(password)
                .build();
        ServerReply reply = userService.register(request).blockingGet();
        return Single.just(validator.isExistingUser(reply));
    }

}
