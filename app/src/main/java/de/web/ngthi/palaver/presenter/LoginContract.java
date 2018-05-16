package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;

import de.web.ngthi.palaver.view.login.LoginActivity;

public interface LoginContract {

    interface View {
        void switchState(LoginActivity.State newState, String username);
        void showPasswordRepeatError();
        void showUserAlreadyExistsError();
        void showNotExistingUserError();
        void showNetworkError();
        void showWrongPasswordError();
        void loginNow();
    }

    interface Presenter {
        void onUsernameInput(String username, LoginActivity.State nextStateRequest);
        void onPasswordInput(String password);
        void onRegisterInput(String password, String passwordRepeat);
    }

    interface Model {
        String provideUsername();
    }
}
