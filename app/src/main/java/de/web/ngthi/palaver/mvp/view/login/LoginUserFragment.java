package de.web.ngthi.palaver.mvp.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.web.ngthi.palaver.R;

public class LoginUserFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String TAG = LoginUserFragment.class.getSimpleName();

    private UserInputListener activityUserCallback;
    private TextView registerField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login_user, container, false);
        registerField = view.findViewById(R.id.textview_loginuser_register);
        registerField.setOnClickListener(this);
        setView(view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityUserCallback = (UserInputListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + UserInputListener.class.toString());
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, String.format("onClick(%s)", v.toString()));
        if(v == getPrimaryButton()) {
            activityUserCallback.onLoginInput(getPrimaryString());
        } else if(v == registerField) {
            activityUserCallback.onRegisterInput(getPrimaryString());
        }
    }

    interface UserInputListener {
        void onLoginInput(String username);
        void onRegisterInput(String username);
    }
}
