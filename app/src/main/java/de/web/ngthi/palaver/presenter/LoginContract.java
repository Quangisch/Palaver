package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;

import dagger.Module;
import de.web.ngthi.palaver.view.login.LoginActivity;
import de.web.ngthi.palaver.view.login.LoginState;

public interface LoginContract {

    interface View extends BaseContract.View {
        void switchState(LoginState newState, String username);
        void showPasswordRepeatError();
        void showUserAlreadyExistsError();
        void showNotExistingUserError();
        void showWrongPasswordError();
        void loginNow(String username, String password);

        void showPasswordTooShort();
        void showPasswordTooLong();
        void showUsernameTooShort();
        void showUsernameTooLong();

    }

    interface Presenter extends BaseContract.Presenter<View>{
        void onUsernameInput(String username, LoginState nextStateRequest);
        void onPasswordInput(String password);
        void onRegisterInput(String password, String passwordRepeat);
    }
}
