package de.web.ngthi.palaver.mvp.view.message;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contracts.MessageContract;

class MessageListAdapter extends RecyclerView.Adapter {

    private MessageContract.Presenter presenter;

    MessageListAdapter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        View messageView;
        switch(type) {
            case SENT:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent, parent, false);
                return new SentMessageViewHolder(messageView);
            case RECEIVED:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received, parent, false);
                return new ReceivedMessageViewHolder(messageView);
            case PENDING:
            default:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent_pending, parent, false);
                return new SentMessageViewHolder(messageView);
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
