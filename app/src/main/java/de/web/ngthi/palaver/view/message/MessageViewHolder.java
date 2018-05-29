package de.web.ngthi.palaver.view.message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.web.ngthi.palaver.presenter.MessageContract;

public abstract class MessageViewHolder extends RecyclerView.ViewHolder implements MessageContract.MessageViewable {

    private TextView messageText;
    private TextView timeText;

    public MessageViewHolder(View view, int messageId, int timeId) {
        super(view);
        messageText = view.findViewById(messageId);
        timeText = view.findViewById(timeId);
    }

    @Override
    public void bind(String messageContent, String dateTime) {
        messageText.setText(messageContent);
        if(dateTime != null)
            timeText.setText(dateTime);
    }
}
