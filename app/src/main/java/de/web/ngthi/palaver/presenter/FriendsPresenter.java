package de.web.ngthi.palaver.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.web.ngthi.palaver.di.DaggerDataRepositoryComponent;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import de.web.ngthi.palaver.repository.DataRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FriendsPresenter extends BasePresenter<FriendsContract.View> implements FriendsContract.Presenter {

    @Inject
    public DataRepository dataRepository;
    private List<User> friends = new LinkedList<>();
//    TODO Snippet message and dateTime?
    private Map<User, Message> friendsMap = new HashMap<>();

    public FriendsPresenter(FriendsContract.View view) {
        super(view);
        DaggerDataRepositoryComponent.create().inject(this);
        updateDataList();
    }

    private void updateDataList() {
        addDisposable(dataRepository.getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataList));
    }

    private void updateDataList(List<User> friends) {
        Log.d(getClass().getSimpleName(), "updateDataList with friendsCount: " + friends.size());
        for(User u : friends)
            Log.d(getClass().getSimpleName(), "friend: " + u);
        this.friends = friends;
        getView().notifyDataSetChanged();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(FriendsContract.FriendsViewable holder, int position) {
        String name = friends.get(position).getUsername();
//        String message = friends.get(position)
//        String datTime =
        holder.bind(name, "...", "12:34");
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

    }

    @Override
    public void onRemoveFriend(String friend) {

    }
}
