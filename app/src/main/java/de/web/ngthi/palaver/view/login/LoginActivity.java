package de.web.ngthi.palaver.view.login;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.TextView;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.presenter.LoginContract;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.view.MessageActivity;

public class LoginActivity extends Activity implements LoginContract.View,
        LoginPasswordFragment.PasswordInputListener,
        LoginRegisterFragment.RegisterInputListener,
        LoginUserFragment.UserInputListener{

    private final String TAG = getClass().getSimpleName();
    private LoginContract.Presenter presenter;

    private TextView header;
    private State state;
    private LoginBaseFragment currentFragment;
    private LoginUserFragment userFragment;
    private LoginRegisterFragment registerFragment;
    private LoginPasswordFragment passwordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        header = findViewById(R.id.textview_loginactivity_header);
        userFragment = new LoginUserFragment();
        registerFragment = new LoginRegisterFragment();
        passwordFragment = new LoginPasswordFragment();
        switchState(State.USERNAME, "");
    }

    @Override
    public void switchState(State newState, @NonNull String username) {
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

        header.setText(getString(R.string.login_welcome) + " " + username);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        state = newState;
    }

    @Override
    public void onBackPressed() {
        if(state != State.USERNAME) {
            userFragment = new LoginUserFragment();
            switchState(State.USERNAME, "");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void loginNow() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
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
        presenter.onUsernameInput(username, State.PASSWORD);
    }

    @Override
    public void onRegisterInput(String username) {
        presenter.onUsernameInput(username, State.REGISTER);
    }


    @Override
    public void showPasswordRepeatError() {
        currentFragment.setErrorField(getString(R.string.login_error_wrongpasswordrepeat));
    }

    @Override
    public void showUserAlreadyExistsError() {
        currentFragment.setErrorField(getString(R.string.login_error_useralreadyexsists));
    }

    @Override
    public void showNotExistingUserError() {
        currentFragment.setErrorField(getString(R.string.login_error_useralreadyexsists));
    }

    @Override
    public void showNetworkError() {
        currentFragment.setErrorField(getString(R.string.login_error_network));
    }

    @Override
    public void showWrongPasswordError() {
        currentFragment.setErrorField(getString(R.string.login_error_wrongpassword));
    }

    public enum State {
        USERNAME, PASSWORD, REGISTER;
    }

}
