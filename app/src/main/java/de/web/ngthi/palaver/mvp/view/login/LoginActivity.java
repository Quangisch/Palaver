package de.web.ngthi.palaver.mvp.view.login;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.LoginContract;
import de.web.ngthi.palaver.mvp.presenter.LoginPresenter;
import de.web.ngthi.palaver.mvp.view.BaseActivity;
import de.web.ngthi.palaver.mvp.view.friends.FriendsActivity;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View,
        LoginPasswordFragment.PasswordInputListener,
        LoginRegisterFragment.RegisterInputListener,
        LoginUserFragment.UserInputListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.textview_loginactivity_header) TextView header;
    private LoginState state;
    private LoginBaseFragment userFragment;
    private LoginBaseFragment registerFragment;
    private LoginBaseFragment passwordFragment;

    public LoginActivity() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO dependencyInjection
        setPresenter(new LoginPresenter(this, getPalaverApplication().getRepository()));
        userFragment = new LoginUserFragment();
        registerFragment = new LoginRegisterFragment();
        passwordFragment = new LoginPasswordFragment();


        getToolbar().setTitle(R.string.app_name);

        switchState(LoginState.USERNAME, "");
    }


    @Override
    public void switchState(LoginState newState, @NonNull String username) {
        Log.d(TAG, "switch State from " + state + " to " + newState + " @" + username);

        FragmentTransaction transaction;
        switch(newState){
            case PASSWORD:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, passwordFragment);
                break;
            case REGISTER:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment);
                break;
            case USERNAME:
            default:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, userFragment);
                break;
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        String headerString = String.format("%s %s", getString(R.string.login_welcome), username);
        header.setText(headerString);
        state = newState;
    }

    @Override
    public void onBackPressed() {
        if(isLoading()) {
            endLoading();
        }else if(state != LoginState.USERNAME) {
            userFragment = new LoginUserFragment();
            switchState(LoginState.USERNAME, "");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void notifyDataSetChanged() {
        //nothing
    }

    @Override
    public void loginNow(@NonNull String username, @NonNull String password) {
        getPalaverApplication().saveLocalUserData(username, password);

        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPasswordInput(String password) {
        getPresenter().onPasswordInput(password);
    }

    @Override
    public void onRegisterInput(String password, String passwordRepeat) {
        getPresenter().onRegisterInput(password, passwordRepeat);
    }

    @Override
    public void onLoginInput(String username) {
        getPresenter().onUsernameInput(username, LoginState.PASSWORD);
    }

    @Override
    public void onRegisterInput(String username) {
        getPresenter().onUsernameInput(username, LoginState.REGISTER);
    }

    @Override
    public void showPasswordTooShort() {
        makeSnack(R.string.global_error_passwordShort);
    }

    @Override
    public void showUsernameTooShort() {
        makeSnack(R.string.global_error_usernameShort);
    }

    @Override
    public void showPasswordRepeatError() {
        makeSnack(R.string.login_error_wrongPasswordRepeat);
    }

    @Override
    public void showUserAlreadyExistsError() {
        makeSnack(R.string.login_error_userAlreadyExsists);
    }

    @Override
    public void showNotExistingUserError() {
        makeSnack(R.string.login_error_unknownUser);
    }

    @Override
    public void showWrongPasswordError() {
        makeSnack(R.string.login_error_wrongPassword);
    }

}
