package de.web.ngthi.palaver.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.web.ngthi.palaver.Configuration;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FriendsPresenter extends BasePresenter<FriendsContract.View> implements FriendsContract.Presenter {


    private List<User> friends = new LinkedList<>();
    private Map<User, Message> friendsMap = new HashMap<>();

    public FriendsPresenter(FriendsContract.View view, IRepository repository, String username, String password) {
        super(view, repository);
        getRepository().setLocalUser(username, password);
        getRepository().refreshToken();
        updateDataList();
    }

    private void updateDataList() {
        addDisposable(getRepository().getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataList));
    }

    private void updateDataList(List<User> friends) {
        Log.d(getClass().getSimpleName(), "updateDataList with friendsCount: " + friends.size());
        for(User u : friends)
            Log.d(getClass().getSimpleName(), "friend: " + u);
        this.friends = friends;

        for(User friend : friends) {
            addDisposable(getRepository().getMessagesFrom(friend.getUsername())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(m -> updateDataMap(friend, m)));
        }

        getView().notifyDataSetChanged();
    }

    private void updateDataMap(User friend, List<Message> messages) {
        int lastIndex = messages.size() - 1;
        Message lastMessage = null;
        if(lastIndex >= 0)
            lastMessage = messages.get(lastIndex);
        friendsMap.put(friend, lastMessage);

        getView().notifyDataSetChanged();
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataList));
    }

    @Override
    public void onRemoveFriend(List<Integer> friendIndex) {
        for(int i : friendIndex) {
            String friend = friends.get(i).getUsername();

            addDisposable(getRepository().removeFriend(friend)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateDataList));
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
        addDisposable(getRepository().isValidUser(username, oldPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> checkOldPassword(u, newPassword, newPasswordRepeat)));
    }

    private void checkOldPassword(boolean valid, String newPassword, String newPasswordRepeat) {
        Log.d(getClass().getSimpleName(), String.format("checkOldPassword(%b, %s, %s)", valid, newPassword, newPasswordRepeat));
        Log.d(getClass().getSimpleName(), String.format("%d < %d < %d)", Configuration.MIN_PASSWORD_LENGTH, newPassword.length(), Configuration.MAX_PASSWORD_LENGTH));
        if(valid) {
            if(newPassword.equals(newPasswordRepeat)) {
                if(newPassword.length() < Configuration.MIN_PASSWORD_LENGTH)
                    getView().showPasswordTooShort();
                else if(newPassword.length() > Configuration.MAX_PASSWORD_LENGTH)
                    getView().showPasswordTooLong();
                else {
                    addDisposable(getRepository().changePassword(newPassword)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> getView().showChangedPassword()));
                }
            } else {
                getView().showWrongPasswordRepeat();
            }
        } else {
            getView().showWrongOldPassword();
        }
    }


}
