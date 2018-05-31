package de.web.ngthi.palaver.mvp.view.message;

import android.view.View;

import de.web.ngthi.palaver.R;

class ReceivedMessageViewHolder extends MessageViewHolder {
    ReceivedMessageViewHolder(View view) {
        super(view, R.id.text_message_body_received, R.id.text_message_time_received);
    }
}
