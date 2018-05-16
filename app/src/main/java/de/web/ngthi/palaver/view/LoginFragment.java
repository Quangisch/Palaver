package de.web.ngthi.palaver.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.web.ngthi.palaver.R;

public class LoginFragment extends Fragment {

    private LoginState state = LoginState.USERNAME;
    private Button button;
    private TextView textButton;
    private TextView headerText;

    private EditText upperText;
    private EditText lowerText;

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.button_loginfragment_button);
        textButton = getActivity().findViewById(R.id.textview_loginfragment_textbutton);
        upperText = getActivity().findViewById(R.id.edittext_loginfragment_upper);
        lowerText = getActivity().findViewById(R.id.edittext_loginfragment_lower);
        headerText = getActivity().findViewById(R.id.textview_loginactivity_header);

        button.setOnClickListener(new ButtonListener());
        textButton.setOnClickListener(new TextListener());

        upperText.setOnEditorActionListener(new TextEditListener());
        lowerText.setOnEditorActionListener(new TextEditListener());

        updateView();
    }

    private void updateView() {
        headerText.setText(R.string.login_welcome);
        upperText.getText().clear();
        lowerText.getText().clear();
        switch (state) {
            case USERNAME:
                upperText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                upperText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                upperText.setHint(R.string.login_hint_username);
                lowerText.setVisibility(View.INVISIBLE);
                button.setText(R.string.login_button_login);
                textButton.setText(R.string.login_button_register);
                break;
            case LOGIN:
                headerText.append(" " + username);
                transformToPassword(upperText);
                lowerText.setVisibility(View.INVISIBLE);
                button.setText(R.string.login_button_login);
                textButton.setText("");
                break;
            case REGISTER:
                headerText.append(" " + username);

                transformToPassword(upperText);
                transformToPassword(lowerText);

                button.setText(R.string.login_button_register);
                textButton.setText("");
                break;
        }
    }

    private void transformToPassword(EditText edit) {
        edit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit.setHint(R.string.login_hint_password);
        edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit.setVisibility(View.VISIBLE);
    }

    private boolean isValideInput() {
        String snackString = null;
        switch (state) {

            case USERNAME:
                if (upperText.getText().toString().equals("Peter"))
                    return true;
                else
                    snackString = getString(R.string.login_snack_unknownuser);
                break;
            case LOGIN:
                if (upperText.getText().toString().equals("Password"))
                    return true;
                else
                    snackString = getString(R.string.login_snack_wrongpassword);
                break;
            case REGISTER:
                if (upperText.getText().toString().equals(lowerText.getText().toString()))
                    return true;
                else
                    snackString = getString(R.string.login_snack_wrongpasswordrepeat);
                break;
        }

        if(snackString != null)
            Snackbar.make(getView(), R.string.login_snack_unknownuser, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        return false;
    }

    public enum LoginState {
        USERNAME, LOGIN, REGISTER;
    }

    private void pressButton() {
        if (isValideInput()) {
            switch (state) {
                case USERNAME:
                    username = upperText.getText().toString();
                    state = LoginState.LOGIN;
                    break;

                case REGISTER:
                case LOGIN:

                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    startActivity(intent);
//                  username = null;
//                  state = State.USERNAME;
                    break;
            }
            updateView();
        }
        upperText.requestFocus();
        upperText.requestFocusFromTouch();
    }

    private class TextListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (isValideInput()) {
                username = upperText.getText().toString();
                state = LoginState.REGISTER;
                updateView();
            }
        }
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            pressButton();
        }
    }

    private class TextEditListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_NULL
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                switch(state) {

                    case USERNAME:
                    case LOGIN:
                        pressButton();
                        return true;
                    case REGISTER:
                        if(v == lowerText) {
                            pressButton();
                            return true;
                        }
                }
            }
            return false;
        }
    }

}