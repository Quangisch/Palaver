package de.web.ngthi.palaver.mvp.contract;

import de.web.ngthi.palaver.mvp.view.login.LoginState;

public interface LoginContract {

    interface View extends BaseContract.View {
        void switchState(LoginState newState, String username);
        void showPasswordRepeatError();
        void showUserAlreadyExistsError();
        void showNotExistingUserError();
        void showWrongPasswordError();
        void loginNow(String username, String password);

        void showPasswordTooShort();
        void showUsernameTooShort();

    }

    interface Presenter extends BaseContract.Presenter<View>{
        void onUsernameInput(String username, LoginState nextStateRequest);
        void onPasswordInput(String password);
        void onRegisterInput(String password, String passwordRepeat);
    }
}
