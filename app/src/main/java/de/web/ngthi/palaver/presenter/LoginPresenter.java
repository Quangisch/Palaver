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
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.Validator;
import de.web.ngthi.palaver.view.login.LoginState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject public DataRepository dataRepository;
    @Inject public RestController controller;
    @Inject public Validator validator;
    private UserService userService;
    private String username;

    private final String TAG = getClass().getSimpleName();

    public LoginPresenter(LoginContract.View view) {
        super(view);
        DaggerRestComponent.builder().build().inject(this);
        DaggerValidatorComponent.builder().build().inject(this);
        userService = controller.provideRetrofit().create(UserService.class);
    }

    @Override
    public void onUsernameInput(@NonNull String username, LoginState nextStateRequest) {
        Log.d(TAG, String.format("onUsernameInput(%s, %s)", username, nextStateRequest.toString()));
        if(userService != null)
            addDisposable(userService.validate(new ServerRequest.Builder().username(username).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(u -> updateState(u, username, nextStateRequest))
                .doOnError(u -> getView().showNetworkError())
                .subscribe());

    }

    private void updateState(ServerReply reply, String username, LoginState nextState) {
        Log.d(TAG, String.format("updateState(%s, %s, %s)", reply, username, nextState.toString()));
        if(nextState == LoginState.PASSWORD) {
            if(validator.isExistingUser(reply)) {
                getView().switchState(nextState, username);
                this.username = username;
            } else {
                getView().showNotExistingUserError();
            }
        } else if(nextState == LoginState.REGISTER) {
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
        addDisposable(userService.validate(new ServerRequest.Builder()
                    .username(username)
                    .password(password)
                    .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(u -> login(u, username, password))
                .doOnError(u -> getView().showNetworkError())
                .subscribe());
    }

    private void login(ServerReply reply, String username, String password) {
        if(reply.getMsgType() == 1) {
            getView().loginNow(username, password);
        } else {
            getView().showWrongPasswordError();
        }
    }

    @Override
    public void onRegisterInput(@NonNull String password, @NonNull String passwordRepeat) {
        Log.d(TAG, String.format("onRegisterInput(%s, %s)", password, passwordRepeat));
        if(!password.equals(passwordRepeat))
            getView().showPasswordRepeatError();
        else {
            addDisposable(userService.register(new ServerRequest.Builder()
                        .username(username)
                        .password(password)
                        .build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(u -> login(u, username, password))
                    .doOnError(u -> getView().showNetworkError())
                    .subscribe());
        }
    }

}
