package de.web.ngthi.palaver.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import de.web.ngthi.palaver.mvp.contract.LoginContract;
import de.web.ngthi.palaver.mvp.view.login.LoginState;
import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.repository.IRepository;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private String username;

    public LoginPresenter(LoginContract.View view, IRepository repository) {
        super(view, repository);
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    public void onUsernameInput(@NonNull String username, LoginState nextStateRequest) {
        Log.d(TAG, String.format("onUsernameInput(%s, %s)", username, nextStateRequest.toString()));
        addDisposable(getRepository().isValidUser(username)
                .compose(applySchedulers())
                .subscribe(u -> updateState(u, username, nextStateRequest)));
    }

    private void updateState(ServerReplyType replyType, String username, LoginState nextState) {
        Log.d(TAG, String.format("updateState(%b, %s, %s)", replyType, username, nextState.toString()));
        if(username.length() < 3) {
            getView().showUsernameTooShort();
        } else {
            switch(replyType) {
                case USER_VALIDATE_FAILED_USERNAME:
                    if(nextState.equals(LoginState.REGISTER)) {
                        this.username = username;
                        getView().switchState(nextState, username);
                    } else if(nextState.equals(LoginState.PASSWORD)){
                        getView().showNotExistingUserError();
                    }
                    break;

                case USER_VALIDATE_FAILED_PASSWORD:
                    if(nextState.equals(LoginState.PASSWORD)) {
                        this.username = username;
                        getView().switchState(nextState, username);
                    } else if(nextState.equals(LoginState.REGISTER)){
                        getView().showUserAlreadyExistsError();
                    }
                    break;
            }
        }

    }


    @Override
    public void onPasswordInput(@NonNull String password) {
        addDisposable(getRepository().isValidUser(username, password)
                .compose(applySchedulers())
                .subscribe(u -> login(u, username, password), this::showNetworkError));
    }

    private void login(ServerReplyType replyType, String username, String password) {
        switch(replyType) {
            case USER_VALIDATE_OK:
            case USER_REGISTER_OK:
                getRepository().setLocalUser(username, password);
                getView().loginNow(username, password);
                break;

            case USER_VALIDATE_FAILED_SHORT:
                getView().showPasswordTooShort();
                break;

            case USER_VALIDATE_FAILED_USERNAME:
                getView().showUserAlreadyExistsError();
                break;

            case USER_VALIDATE_FAILED_PASSWORD:
            default:
                getView().showWrongPasswordError();
                break;

        }
    }

    @Override
    public void onRegisterInput(@NonNull String password, @NonNull String passwordRepeat) {
        Log.d(TAG, String.format("onRegisterInput(%s, %s)", password, passwordRepeat));
        if(!password.equals(passwordRepeat))
            getView().showPasswordRepeatError();
        else {
            addDisposable(getRepository().registerNewUser(username, password)
                    .compose(applySchedulers())
                    .subscribe(u -> login(u, username, password), this::showNetworkError));
        }
    }

}
