package de.web.ngthi.palaver.view.message;

import android.view.View;

import de.web.ngthi.palaver.R;

public class ReceivedMessageHolder extends MessageBaseRowView {
    public ReceivedMessageHolder(View view) {
        super(view, R.id.text_message_body_received, R.id.text_message_time_received);
    }
}
