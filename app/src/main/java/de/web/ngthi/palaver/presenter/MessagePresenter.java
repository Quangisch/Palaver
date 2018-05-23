package de.web.ngthi.palaver.presenter;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import de.web.ngthi.palaver.di.DaggerDataRepositoryComponent;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.view.message.HolderType;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {

    @Inject
    public DataRepository dataRepository;
    private String friend;
    private List<Message> messages = new LinkedList<>();

    public MessagePresenter(MessageContract.View view, String friend) {
        super(view);
        this.friend = friend;
        Log.d(getClass().getSimpleName(), "created Presenter for friend " + friend);
        DaggerDataRepositoryComponent.create().inject(this);
        updateDataList();
    }

    private void updateDataList() {
        addDisposable(dataRepository.getMessagesFrom(friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataList));
    }

    private void updateDataList(List<Message> messages) {
        Log.d(this.getClass().getSimpleName(), String.format("updateDataList: %s with %s messages", friend, messages.size()));
        this.messages = messages;
        getView().notifyDataSetChanged();
        getView().scrollDown();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(MessageContract.MessageViewable holder, int position) {
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
//        Message m = messages.get(0);
//        messages.add(new Message(m.getSender().isLocalUser() ? m.getSender() : m.getRecipient(),
//                m.getSender().isLocalUser() ? m.getRecipient() : m.getSender(), message, DateTime.now()));
        addDisposable(dataRepository.sendMessage(friend, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateDataList));
    }

    @Override
    public String getFriendName() {
        return friend;
    }
}
