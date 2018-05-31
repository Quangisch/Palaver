package de.web.ngthi.palaver.mvp.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.web.ngthi.palaver.R;

public class LoginRegisterFragment extends LoginBaseFragment implements View.OnClickListener{

    private RegisterInputListener activityRegisterCallback;
    private EditText passwordRepeatField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        setView(view);
        return view;
    }

    public void onStart() {
        super.onStart();
        passwordRepeatField = getActivity().findViewById(R.id.edittext_login_secondary);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityRegisterCallback = (LoginActivity) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + RegisterInputListener.class.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        passwordRepeatField.setText("");
    }

    @Override
    public void onClick(View v) {
        activityRegisterCallback.onRegisterInput(getPrimaryString(), passwordRepeatField.getText().toString());
    }

    interface RegisterInputListener {
        void onRegisterInput(String password, String passwordRepeat);
    }
}
