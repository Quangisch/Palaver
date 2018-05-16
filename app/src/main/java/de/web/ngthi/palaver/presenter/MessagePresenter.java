package de.web.ngthi.palaver.presenter;

import org.joda.time.DateTime;

import java.util.List;

import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.view.message.HolderType;

public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {


    private List<Message> messages;

    public MessagePresenter(MessageContract.View view, List<Message> messages) {
        super(view);
        this.messages = messages;
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(MessageContract.MessageRowView holder, int position) {
        Message m = messages.get(position);
        holder.bind(m.getContent(), m.getDateTimeString());
    }

    @Override
    public int getRepositoriesRowsCount() {
        return messages.size();
    }

    @Override
    public int getRepositoriesRowsType(int position) {
        if(messages.get(position).getSender().isLocalUser())
            return HolderType.SENT.ordinal();
        else
            return HolderType.RECEIVED.ordinal();
    }

    @Override
    public void onMessageSend(String message) {
        Message m = messages.get(0);
        messages.add(new Message(m.getSender().isLocalUser() ? m.getSender() : m.getRecipient(),
                m.getSender().isLocalUser() ? m.getRecipient() : m.getSender(), message, DateTime.now()));
        getView().notifyDataSetChanged();
        getView().scrollDown();
    }

    @Override
    public String getFriendName() {
        return messages.get(0).getSender().isLocalUser() ? messages.get(0).getRecipient().getUsername() : messages.get(0).getSender().getUsername();
    }
}
