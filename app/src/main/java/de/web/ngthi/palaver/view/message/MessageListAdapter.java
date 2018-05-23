package de.web.ngthi.palaver.view.message;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.presenter.MessageContract;

public class MessageListAdapter extends RecyclerView.Adapter {

    private MessageContract.Presenter presenter;

    public MessageListAdapter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderType type = HolderType.values()[viewType];
        View messageView;
        switch(type) {
            case SENT:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent, parent, false);
                return new SentMessageViewHolder(messageView);
            case RECEIVED:
            default:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received, parent, false);
                return new ReceivedMessageViewHolder(messageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        presenter.onBindRepositoryRowViewAtPosition((MessageContract.MessageViewable)holder, position);
    }


    @Override
    public int getItemViewType(int position) {
        return presenter.getRepositoriesRowsType(position);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoriesRowsCount();
    }

}
