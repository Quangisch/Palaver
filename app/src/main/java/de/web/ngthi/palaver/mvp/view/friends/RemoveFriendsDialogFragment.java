package de.web.ngthi.palaver.mvp.view.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.web.ngthi.palaver.R;

public class RemoveFriendsDialogFragment extends DialogFragment {

    private static final String TAG = RemoveFriendsDialogFragment.class.getSimpleName();

    private InputListener listener;
    private List<String> selectedFriends;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] friends = listener.getFriends();
        selectedFriends = new LinkedList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.friends_title_removeFriend)
                //TODO setAdapter for asynchronous list backing
                .setMultiChoiceItems(friends, null, (DialogInterface dialog, int which, boolean checked) -> {
                    Log.d(TAG, String.format("%s: %s", checked ? "checked" : "unchecked", friends[which]));
                    if(checked) {
                        selectedFriends.add(friends[which]);
                    } else {
                        selectedFriends.remove(friends[which]);
                    }
                })
                .setPositiveButton(R.string.friends_button_removeFriend, (DialogInterface dialog, int which) ->
                        listener.onRemoveDialogPositiveButton(selectedFriends))
                .setNegativeButton(R.string.friends_button_cancel, (DialogInterface dialog, int which) ->
                        RemoveFriendsDialogFragment.this.getDialog().cancel());

        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (InputListener) context;
        } catch(ClassCastException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    interface InputListener {
        void onRemoveDialogPositiveButton(List<String> selectedFriends);
        String[] getFriends();
    }
}
