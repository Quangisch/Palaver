package de.web.ngthi.palaver.view.login;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.presenter.LoginContract;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.view.friends.FriendsActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View,
        LoginPasswordFragment.PasswordInputListener,
        LoginRegisterFragment.RegisterInputListener,
        LoginUserFragment.UserInputListener{

    public PalaverApplication application;
    private LoginContract.Presenter presenter;

    private TextView header;
    private LoginState state;
    private LoginBaseFragment currentFragment;
    private LoginUserFragment userFragment;
    private LoginRegisterFragment registerFragment;
    private LoginPasswordFragment passwordFragment;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        application = (PalaverApplication) getApplication();
        application.clearLocalUserData();


        Toolbar toolbar = findViewById(R.id.toolbar_login);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        header = findViewById(R.id.textview_loginactivity_header);
        userFragment = new LoginUserFragment();
        registerFragment = new LoginRegisterFragment();
        passwordFragment = new LoginPasswordFragment();
        presenter = new LoginPresenter(this, application.getRepository());

        switchState(LoginState.USERNAME, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void switchState(LoginState newState, @NonNull String username) {
        Log.d(TAG, "switch State from " + state + " to " + newState + " @" + username);

        FragmentTransaction transaction;
        switch(newState){
            case PASSWORD:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, passwordFragment);
                currentFragment = passwordFragment;
                break;
            case REGISTER:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment);
                currentFragment = registerFragment;
                break;
            case USERNAME:
            default:
                transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, userFragment);
                currentFragment = userFragment;
                break;
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        String headerString = String.format("%s %s", getString(R.string.login_welcome), username);
        header.setText(headerString);
        state = newState;
    }

    @Override
    public void onBackPressed() {
        if(state != LoginState.USERNAME) {
            userFragment = new LoginUserFragment();
            switchState(LoginState.USERNAME, "");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void loginNow(@NonNull String username, @NonNull String password) {
        application.saveLocalUserData(username, password);

        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    private void makeSnack(int resId) {
        Snackbar.make(findViewById(R.id.coordinatorlayout_login), resId, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showPasswordTooShort() {
        makeSnack(R.string.global_error_passwordShort);
    }

    @Override
    public void showPasswordTooLong() {
        makeSnack(R.string.global_error_passwordLong);
    }

    @Override
    public void showUsernameTooShort() {
        makeSnack(R.string.global_error_usernameShort);
    }

    @Override
    public void showUsernameTooLong() {
        makeSnack(R.string.global_error_usernameLong);
    }

    @Override
    public void onPasswordInput(String password) {
        presenter.onPasswordInput(password);
    }

    @Override
    public void onRegisterInput(String password, String passwordRepeat) {
        presenter.onRegisterInput(password, passwordRepeat);
    }

    @Override
    public void onLoginInput(String username) {
        presenter.onUsernameInput(username, LoginState.PASSWORD);
    }

    @Override
    public void onRegisterInput(String username) {
        presenter.onUsernameInput(username, LoginState.REGISTER);
    }


    @Override
    public void showPasswordRepeatError() {
        currentFragment.setErrorField(getString(R.string.login_error_wrongPasswordRepeat));
    }

    @Override
    public void showUserAlreadyExistsError() {
        currentFragment.setErrorField(getString(R.string.login_error_userAlreadyExsists));
    }

    @Override
    public void showNotExistingUserError() {
        currentFragment.setErrorField(getString(R.string.login_error_unknownUser));
    }

    @Override
    public void showNetworkError() {
        currentFragment.setErrorField(getString(R.string.login_error_network));
    }

    @Override
    public void showWrongPasswordError() {
        currentFragment.setErrorField(getString(R.string.login_error_wrongPassword));
    }

}
