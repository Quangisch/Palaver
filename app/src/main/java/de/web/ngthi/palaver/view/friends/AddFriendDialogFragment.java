package de.web.ngthi.palaver.view.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.web.ngthi.palaver.R;

public class AddFriendDialogFragment extends DialogFragment {

    private InputListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edittext, null);
        builder.setTitle(R.string.friends_title_addFriend)
                .setView(view)
                .setPositiveButton(R.string.friends_button_addFriend, (DialogInterface dialog, int which) -> {
                    EditText newFriend = view.findViewById(R.id.edittext_friends_dialog);
                    listener.onAddDialogPositiveButton(newFriend.getText().toString());
                }).setNegativeButton(R.string.friends_button_cancel, (DialogInterface dialog, int which) -> this.dismiss());

        return builder.create();
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
        void onAddDialogPositiveButton(String friend);
        void onAddDialogNegativeButton();
    }
}
