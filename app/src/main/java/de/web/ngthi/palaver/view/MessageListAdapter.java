package de.web.ngthi.palaver.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.model.Message;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int MESSAGE_SENT = 0;
    private static final int MESSAGE_RECEIVED = 1;

    private List<Message> messages;

    public MessageListAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View messageView;
        if(viewType == MESSAGE_SENT) {
            messageView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
           return new SentMessageHolder(messageView);
        } else {
            messageView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedMessageHolder(messageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(messages.get(position));
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(messages.get(position));
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().isLocalUser()) {
            return MESSAGE_SENT;
        } else {
            return MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        private TextView messageText, timeText;
        SentMessageHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.text_message_body_sent);
            timeText = view.findViewById(R.id.text_message_time_sent);
        }

        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText(message.getDateTimeString());
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView messageText, timeText;
        ReceivedMessageHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.text_message_body_received);
            timeText = view.findViewById(R.id.text_message_time_received);
        }

        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText(message.getDateTimeString());
        }
    }




}
