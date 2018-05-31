package de.web.ngthi.palaver.mvp.view.message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.MessageContract;

abstract class MessageViewHolder extends RecyclerView.ViewHolder implements MessageContract.MessageViewable {

    private TextView messageText;
    private TextView timeText;

    MessageViewHolder(View view, int messageId, int timeId) {
        super(view);
        messageText = view.findViewById(messageId);
        timeText = view.findViewById(timeId);
    }

    @Override
    public void bind(String messageContent, String dateTime) {
        messageText.setText(messageContent);
        timeText.setText(dateTime);
    }

    public void bindPending(String messageContent) {
        bind(messageContent, itemView.getResources().getString(R.string.message_info_sentPending));
    }

    public void bindFailed(String messageContent) {
        bind(messageContent, itemView.getResources().getString(R.string.message_info_sentFailed));
    }
}
