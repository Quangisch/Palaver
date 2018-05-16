package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.di.DaggerRestComponent;
import de.web.ngthi.palaver.di.DaggerValidatorComponent;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import de.web.ngthi.palaver.service.Validator;
import de.web.ngthi.palaver.view.login.LoginActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    @Inject public RestController restController;
    @Inject public Validator validator;
    public UserService userService;
    private String username;


    public LoginPresenter(LoginContract.View view) {
        super(view);
        DaggerRestComponent.builder().build().inject(this);
        DaggerValidatorComponent.builder().build().inject(this);
        userService = restController.provideRetrofit().create(UserService.class);
    }

    @Override
    public void onUsernameInput(@NonNull String username, LoginActivity.State nextStateRequest) {
        Log.d(TAG, String.format("onUsernameInput(%s, %s)", username, nextStateRequest.toString()));
        if(userService != null)
            addDisposable(userService.validate(new ServerRequest.Builder().username(username).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(u -> updateState(u, username, nextStateRequest))
                .doOnError(u -> getView().showNetworkError())
                .subscribe(System.out::println));

    }

    private void updateState(ServerReply reply, String username, LoginActivity.State nextState) {
        Log.d(TAG, String.format("updateState(%s, %s)", username, nextState.toString()));
        if(nextState == LoginActivity.State.PASSWORD) {
            if(validator.isExistingUser(reply)) {
                getView().switchState(nextState, username);
                this.username = username;
            } else {
                getView().showNotExistingUserError();
            }
        }
        if(nextState == LoginActivity.State.REGISTER) {
            if(!validator.isExistingUser(reply)) {
                getView().switchState(nextState, username);
                this.username = username;
            } else {
                getView().showUserAlreadyExistsError();
            }
        }
    }


    @Override
    public void onPasswordInput(@NonNull String password) {
        Log.d(TAG, String.format("onPasswordInput(%s)", password));
        addDisposable(userService.validate(new ServerRequest.Builder().localUser(username, password).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(u -> login(u, password))
                .doOnError(u -> getView().showNetworkError())
                .subscribe(System.out::println));
    }

    private void login(ServerReply reply, String password) {
        if(reply.getMsgType() == 0) {
            getView().loginNow();
        } else {
            getView().showWrongPasswordError();
        }
    }

    @Override
    public void onRegisterInput(@NonNull String password, @NonNull String passwordRepeat) {
        if(!password.equals(passwordRepeat))
            getView().showPasswordRepeatError();
        else {
            addDisposable(userService.register(new ServerRequest.Builder().localUser(username, password).build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(u -> login(u, password))
                    .doOnError(u -> getView().showNetworkError())
                    .subscribe(System.out::println));
        }
        Log.d(TAG, String.format("onRegisterInput(%s)", password, passwordRepeat));
    }

}
