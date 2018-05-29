package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import de.web.ngthi.palaver.Configuration;
import de.web.ngthi.palaver.repository.IRepository;
import de.web.ngthi.palaver.view.login.LoginState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private String username;

    private final String TAG = "=="+getClass().getSimpleName()+"==";

    public LoginPresenter(LoginContract.View view, IRepository repository) {
        super(view, repository);
    }

    @Override
    public void onUsernameInput(@NonNull String username, LoginState nextStateRequest) {
        Log.d(TAG, String.format("onUsernameInput(%s, %s)", username, nextStateRequest.toString()));
        if(username.length() < Configuration.MIN_USERNAME_LENGTH)
            getView().showUsernameTooShort();
        else if(username.length() > Configuration.MAX_USERNAME_LENGTH)
            getView().showUsernameTooLong();
        else {
            addDisposable(getRepository().isValidUser(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
            addDisposable(getRepository().isValidUser(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(u -> login(u, username, password), this::showNetworkError));
        }
    }

    private void login(boolean validLogin, String username, String password) {
        if(validLogin) {
            getRepository().setLocalUser(username, password);
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
            addDisposable(getRepository().isValidNewUser(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(u -> login(u, username, password), this::showNetworkError));
        }
    }

}
