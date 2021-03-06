package de.web.ngthi.palaver.mvp.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.web.ngthi.palaver.R;

public class LoginPasswordFragment extends LoginBaseFragment implements View.OnClickListener {

    private PasswordInputListener activityPasswordCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_password, container, false);
        setView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityPasswordCallback = (LoginActivity) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + PasswordInputListener.class.toString());
        }
    }

    @Override
    public void onClick(View v) {
        activityPasswordCallback.onPasswordInput(getPrimaryString());
    }

    interface PasswordInputListener {
        void onPasswordInput(String password);
    }
}
