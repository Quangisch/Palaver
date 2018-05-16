package de.web.ngthi.palaver.view.message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.presenter.MessageContract;

public abstract class MessageBaseRowView extends RecyclerView.ViewHolder implements MessageContract.MessageRowView {

    private TextView messageText;
    private TextView timeText;

    public MessageBaseRowView(View view, int messageId, int timeId) {
        super(view);
        messageText = view.findViewById(messageId);
        timeText = view.findViewById(timeId);
    }

    @Override
    public void bind(String messageContent, String dateTime) {
        messageText.setText(messageContent);
        timeText.setText(dateTime);
    }
}
