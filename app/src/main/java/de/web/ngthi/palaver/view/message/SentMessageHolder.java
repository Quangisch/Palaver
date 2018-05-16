package de.web.ngthi.palaver.view.message;

import android.view.View;

import de.web.ngthi.palaver.R;

public class SentMessageHolder extends MessageBaseRowView {
    public SentMessageHolder(View view) {
        super(view, R.id.text_message_body_sent, R.id.text_message_time_sent);
    }
}
