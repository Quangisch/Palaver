package de.web.ngthi.palaver.mvp.view.friends;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contracts.FriendsContract;

public class FriendsViewHolder extends RecyclerView.ViewHolder implements FriendsContract.FriendsViewable, View.OnClickListener {

    private TextView friendNameText;
    private TextView messageText;
    private TextView timeText;
    private FriendsContract.Presenter presenter;

    FriendsViewHolder(View view, FriendsContract.Presenter presenter) {
        super(view);
        this.presenter = presenter;
        friendNameText = view.findViewById(R.id.textView_friend_name);
        messageText = view.findViewById(R.id.textView_friend_message);
        timeText = view.findViewById(R.id.textView_friend_dateTime);
        view.setOnClickListener(this);
    }


    public void bind(String friendName, String message, String dateTime) {
        Log.d(getClass().getSimpleName(), String.format("bind(%s, %s, %s)", friendName, message, dateTime));
        friendNameText.setText(friendName);
        messageText.setText(message);
        timeText.setText(dateTime);
    }

    @Override
    public void onClick(View v) {
        presenter.onFriendClick(friendNameText.getText().toString());
    }
}
