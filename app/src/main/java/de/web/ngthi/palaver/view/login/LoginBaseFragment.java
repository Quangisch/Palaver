package de.web.ngthi.palaver.view.login;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.web.ngthi.palaver.R;

public abstract class LoginBaseFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private View view;
    private EditText primaryField;
    private Button primaryButton;
    private TextView errorField;

    private LoginActivity.State state;

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");

        primaryField = view.findViewById(R.id.edittext_login_primary);
        primaryButton = view.findViewById(R.id.button_login_primary);
        errorField = view.findViewById(R.id.textview_login_error);

        primaryButton.setOnClickListener(this);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onPause()  {
        super.onPause();

        Log.d(TAG, "onPause()");
        clearPrimaryString();
        clearErrorField();
    }

    public void setErrorField(String errorMessage) {
        errorField.setText(errorMessage);
    }

    public void clearErrorField() {
        errorField.setText("");
    }

    public String getPrimaryString() {
        return primaryField.getText().toString();
    }

    public void clearPrimaryString() {
        primaryField.setText("");
    }

    public Button getPrimaryButton() {
        return primaryButton;
    }

}
