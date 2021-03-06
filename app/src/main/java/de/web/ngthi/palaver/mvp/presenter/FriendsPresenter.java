package de.web.ngthi.palaver.mvp.presenter;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.web.ngthi.palaver.mvp.contract.FriendsContract;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FriendsPresenter extends BasePresenter<FriendsContract.View> implements FriendsContract.Presenter {

    private static final String TAG = FriendsPresenter.class.getSimpleName();

    private List<User> friends = new LinkedList<>();
    private Map<User, Message> friendsMap = new HashMap<>();

    public FriendsPresenter(FriendsContract.View view, IRepository repository, String username, String password) {
        super(view, repository);
        Log.d(TAG, "==============constructor==============");
        Log.d(TAG, String.format("Presenter for username:\"%s\", \"%s\"", username, password));
        getRepository().setLocalUser(username, password);
        addDisposable(getRepository().refreshToken(FirebaseInstanceId.getInstance().getToken())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applyLoader())
                .subscribe(this::logToken, this::showNetworkError));
        updateDataList();
    }



    private void logToken(ServerReplyType reply) {
        Log.d(TAG, reply.toString());
    }

    private void updateDataList() {
        addDisposable(getRepository().getFriendList()
                .compose(applySchedulers())
                .subscribe(this::updateDataList, this::showNetworkError));
    }

    private void updateDataList(List<User> friends) {
        this.friends = friends;

        for(User friend : friends) {
            addDisposable(getRepository().getMessagesFrom(friend.getUsername())
                    .compose(applySchedulers())
                    .subscribe(m -> updateDataMap(friend, m), this::showNetworkError));
        }

        if(friends.size() == 0)
            onDataRefreshed();
    }

    private void updateDataMap(User friend, List<Message> messages) {
        int lastIndex = messages.size() - 1;
        Message lastMessage = null;
        if(lastIndex >= 0)
            lastMessage = messages.get(lastIndex);
        friendsMap.put(friend, lastMessage);
        onDataRefreshed();
    }

    private void onDataRefreshed() {
        getView().notifyDataSetChanged();
        getView().onSwipeRefreshEnd();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(FriendsContract.FriendsViewable holder, int position) {
        String name = friends.get(position).getUsername();
        Message m = friendsMap.get(new User(name));
        String message = "";
        String datTime = "";
        if(m != null) {
            message = m.getContent();
            datTime = m.getDateTimeString();
        }
        holder.bind(name, message, datTime);
    }

    @Override
    public int getRepositoriesRowsCount() {
        return friends.size();
    }

    @Override
    public void onFriendClick(String friend) {
        getView().onFriendClick(friend);
    }

    @Override
    public void onAddFriend(String friend) {
        addDisposable(getRepository().addFriend(friend)
                .compose(applySchedulers())
                .subscribe(this::addFriend, this::showNetworkError));
    }

    private void addFriend(ServerReplyType type) {
        switch(type) {
            case FRIENDS_ADD_OK: updateDataList();
                break;
            case FRIENDS_ADD_FAILED_ALREADY_ADDED: getView().showDuplicateFriendError();
                break;
            case FRIENDS_ADD_FAILED_UNKNOWN: getView().showUnknownFriendError();
                break;
        }
    }

    @Override
    public void onRemoveFriend(List<String> friends) {
        for(String friend : friends) {
            addDisposable(getRepository().removeFriend(friend)
                    .compose(applySchedulers())
                    .subscribe(this::removeFriend, this::showNetworkError));
        }
    }

    private void removeFriend(ServerReplyType type) {
        switch(type) {
            case FRIENDS_REMOVE_OK:
                updateDataList();
                break;
            case FRIENDS_REMOVE_FAILED:
                getView().showUnknownFriendError();
                break;
        }
    }

    @Override
    public String[] getFriends() {
        List<String> friendNames = new LinkedList<>();
        for(User u : friends)
            friendNames.add(u.getUsername());
        return friendNames.toArray(new String[friendNames.size()]);
    }

    @Override
    public void onChangePassword(String username, String oldPassword, String newPassword, String newPasswordRepeat) {
        Log.d(getClass().getSimpleName(), String.format("onChangePassword(username:%s, oldpassword:%s, newPassword:%s, newPasswordRepeat:%s)",
                username, oldPassword, newPassword, newPasswordRepeat));
        if(!newPassword.equals(newPasswordRepeat))
            getView().showWrongPasswordRepeat();
        else {
            addDisposable(getRepository().changePassword(username, oldPassword, newPassword)
                    .compose(applySchedulers())
                    .subscribe(u -> changePassword(u, oldPassword, newPassword), this::showNetworkError));
        }
    }

    @Override
    public void onSwipeRefreshStart() {
        updateDataList();
    }

    private void changePassword(ServerReplyType replyType, String oldPassword, String newPassword) {
        switch(replyType) {
            case USER_VALIDATE_FAILED_PASSWORD:
            case USER_PASSWORD_FAILED:
                getView().showWrongOldPassword();
                break;
            case USER_VALIDATE_FAILED_SHORT: //server doesn't check min length when changing pw
                getView().showPasswordTooShort();
                break;
            case USER_PASSWORD_OK:
                getView().showChangedPassword();
                break;
        }
    }

    @Override
    public void onReceiveBroadcastNotification() {
        updateDataList();
    }
}
