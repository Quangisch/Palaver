package de.web.ngthi.palaver.mvp.presenter;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import de.web.ngthi.palaver.dto.ServerReplyType;
import de.web.ngthi.palaver.mvp.contracts.MessageContract;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.LocalizedDateTime;
import de.web.ngthi.palaver.mvp.model.User;
import de.web.ngthi.palaver.mvp.view.message.HolderType;
import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {

    private final String TAG = "=="+getClass().getSimpleName()+"==";

    private String friend;
    private List<Message> messages = new LinkedList<>();
    private DateTime offset;

    public MessagePresenter(MessageContract.View view, IRepository repository, String friend) {
        super(view, repository);
        this.friend = friend;
        Log.d(getClass().getSimpleName(), "created Presenter for friend " + friend);
        updateDataList();
    }

    private void updateDataList() {
        if(offset == null) {
            Log.d(TAG, "updateList without offset");
            addDisposable(getRepository().getMessagesFrom(friend)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateDataList, this::showNetworkError));
        } else {
            Log.d(TAG, "updateList with offset: " + offset.toString());
            addDisposable(getRepository().getMessageFromOffset(friend, offset.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateDataList, this::showNetworkError));
        }
    }

    private void updateDataList(List<Message> messages) {
        Log.d(this.getClass().getSimpleName(), String.format("updateDataList: %s with %s messages", friend, messages.size()));
        this.messages = messages;
        getView().notifyDataSetChanged();
        getView().onSwipeRefreshEnd();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(MessageContract.MessageViewable holder, int position) {
        Message m = messages.get(position);
        switch(m.getStatus()) {
            case SENT:
                holder.bind(m.getContent(), m.getDateTimeString());
                break;
            case PENDING:
                holder.bindPending(m.getContent());
                break;
            case FAILED:
                holder.bindFailed(m.getContent());
                break;
        }
    }

    @Override
    public int getRepositoriesRowsCount() {
        return messages.size();
    }

    @Override
    public int getRepositoriesRowsType(int position) {
        if(messages.get(position).getSender() == null)
            return HolderType.PENDING.ordinal();
        else if(messages.get(position).getSender().getUsername().equals(friend))
            return HolderType.RECEIVED.ordinal();
        else
            return HolderType.SENT.ordinal();
    }

    @Override
    public void onMessageSend(String message) {
        messages.add(new Message(null, new User(friend), message, null, Message.Status.PENDING));
        getView().notifyDataSetChanged();
        addDisposable(getRepository().sendMessage(friend, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::messageSend, this::showNetworkError));
    }

    private void messageSend(ServerReplyType type) {
        switch (type) {
            case MESSAGE_SEND_OK: updateDataList(); break;
            case MESSAGE_GET_FAILED: getView().showNetworkError(); break;
        }
    }

    @Override
    public void showNetworkError(Throwable throwable) {
        for(Message m : messages) {
            if (m.getStatus() == Message.Status.PENDING)
                m.setStatus(Message.Status.FAILED);
        }
        getView().notifyDataSetChanged();
        super.showNetworkError(throwable);
    }

    @Override
    public void onSwipeRefreshStart() {
        updateDataList();
    }

    @Override
    public void onAddFilterOffset(int year, int month, int day, int hour, int minute) {
        offset = new DateTime(year, month, day, hour, minute);
        Log.d(TAG, "onAddFilterOffset: " + offset.toString());
        updateDataList();
    }

    @Override
    public void onRemoveFilterOffset() {
        offset = null;
        updateDataList();
    }

    @Override
    public boolean hasOffsetFilter() {
        return offset != null;
    }

    @Override
    public String getOffsetString() {
        if(hasOffsetFilter())
            return LocalizedDateTime.getDateTime(offset);
        return "";
    }
}
