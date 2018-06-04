package de.web.ngthi.palaver.mvp.view.login;

import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.web.ngthi.palaver.R;

abstract class LoginBaseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = LoginBaseFragment.class.getSimpleName();
    private View view;
    private EditText primaryField;
    private Button primaryButton;

    @Override
    public void onStart() {
        super.onStart();

        primaryField = view.findViewById(R.id.edittext_login_primary);
        primaryButton = view.findViewById(R.id.button_login_primary);

        primaryButton.setOnClickListener(this);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onPause()  {
        super.onPause();

        clearPrimaryString();
    }

    public void makeSnack(int errorResID) {
        makeSnack(getString(errorResID));
    }
    public void makeSnack(String errorMessage) {
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show();
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
