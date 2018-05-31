package de.web.ngthi.palaver.mvp.view.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.web.ngthi.palaver.R;

public class ChangePasswordDialogFragment extends DialogFragment {

    private InputListener listener;
    private AlertDialog dialog;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordRepeat;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);

        oldPassword = view.findViewById(R.id.edittext_friend_dialog_oldPassword);
        newPassword = view.findViewById(R.id.edittext_friend_dialog_newPassword);
        newPasswordRepeat = view.findViewById(R.id.edittext_friend_dialog_newPasswordRepeat);

        builder.setTitle(R.string.friends_title_changePassword)
                .setView(view)
                .setPositiveButton(R.string.friends_button_changePassword, null)
                .setNegativeButton(R.string.friends_button_cancel, (DialogInterface dialog, int which)
                                -> ChangePasswordDialogFragment.this.getDialog().cancel());

        dialog = builder.create();
        dialog.setOnShowListener((DialogInterface d) -> dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setOnClickListener((View v) -> listener.onChangeDialogPositiveButton(
                        oldPassword.getText().toString(),
                        newPassword.getText().toString(),
                        newPasswordRepeat.getText().toString())));
        return dialog;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (InputListener) context;
        } catch(ClassCastException e) {
            Log.d(getClass().getSimpleName(), e.getLocalizedMessage());
        }
    }

    interface InputListener {
        void onChangeDialogPositiveButton(String oldPassword, String newPassword, String newPasswordRepeat);
    }
}
