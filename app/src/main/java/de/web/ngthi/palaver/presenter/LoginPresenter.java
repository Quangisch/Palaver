package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import de.web.ngthi.palaver.Configuration;
import de.web.ngthi.palaver.di.DaggerDataRepositoryComponent;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.view.login.LoginState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject public DataRepository dataRepository;
    private String username;

    private final String TAG = getClass().getSimpleName();

    public LoginPresenter(LoginContract.View view) {
        super(view);
        DaggerDataRepositoryComponent.create().inject(this);
    }

    @Override
    public void onUsernameInput(@NonNull String username, LoginState nextStateRequest) {
        Log.d(TAG, String.format("onUsernameInput(%s, %s)", username, nextStateRequest.toString()));
        if(username.length() < Configuration.MIN_USERNAME_LENGTH)
            getView().showPasswordTooShort();
        else if(username.length() > Configuration.MAX_USERNAME_LENGTH)
            getView().showPasswordTooLong();
        else {
            addDisposable(dataRepository.isValidUser(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(u -> getView().showNetworkError())
                    .subscribe(u -> updateState(u, username, nextStateRequest)));
        }
    }

    private void updateState(boolean existingUsername, String username, LoginState nextState) {
        Log.d(TAG, String.format("updateState(%b, %s, %s)", existingUsername, username, nextState.toString()));
        if(nextState == LoginState.PASSWORD) {
            if(existingUsername) {
                getView().switchState(nextState, username);
                this.username = username;
            } else {
                getView().showNotExistingUserError();
            }
        } else if(nextState == LoginState.REGISTER) {
            if(!existingUsername) {
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
        if(password.length() < Configuration.MIN_PASSWORD_LENGTH)
            getView().showPasswordTooShort();
        else if(password.length() > Configuration.MAX_PASSWORD_LENGTH)
            getView().showPasswordTooLong();
        else {
            addDisposable(dataRepository.isValidUser(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(u -> getView().showNetworkError())
                    .subscribe(u -> login(u, username, password)));
        }
    }

    private void login(boolean validLogin, String username, String password) {
        if(validLogin) {
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
            addDisposable(dataRepository.isValidNewUser(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(u -> getView().showNetworkError())
                    .subscribe(u -> login(u, username, password)));
        }
    }

}
