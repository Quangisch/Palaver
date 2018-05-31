package de.web.ngthi.palaver.mvp.view.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.web.ngthi.palaver.R;

public class RemoveFriendsDialogFragment extends DialogFragment {

    private InputListener listener;
    private List<Integer> selectedFriends;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedFriends = new LinkedList<>();

        Log.d("======","===========");
        for(String s : listener.getFriends()) {
            Log.d(getClass().getSimpleName(), s);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.friends_title_removeFriend)
                //TODO setAdapter for asynchronous list backing
                .setMultiChoiceItems(listener.getFriends(), null, (DialogInterface dialog, int which, boolean checked) -> {
                    if(checked)
                        selectedFriends.add(which);
                    else
                        selectedFriends.remove(Integer.valueOf(which));
                })
                .setPositiveButton(R.string.friends_button_removeFriend, (DialogInterface dialog, int which) -> {
                    //TODO get edittext
                    listener.onRemoveDialogPositiveButton(selectedFriends);
                }).setNegativeButton(R.string.friends_button_cancel, (DialogInterface dialog, int which) ->
                        RemoveFriendsDialogFragment.this.getDialog().cancel());

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
        void onRemoveDialogPositiveButton(List<Integer> selectedFriendsIndex);
        String[] getFriends();
    }
}
