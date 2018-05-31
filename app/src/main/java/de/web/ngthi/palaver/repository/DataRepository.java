package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.dto.ServerReplyType;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import io.reactivex.Single;

@Module
public class DataRepository implements IRepository {

    private final String TAG = "=="+getClass().getSimpleName()+"==";

    private IRepository restRepository;
    private IRepository localRepository;

    @Inject
    public DataRepository (RestRepository restRepository, LocalRepository localRepository) {
        Log.d(TAG, "=========CONSTRUCTOR=========");
        this.restRepository = restRepository;
        this.localRepository = localRepository;
    }

    //TODO
    private IRepository getDefaultRepository() {
        return restRepository;
    }

    @Override
    public void setLocalUser(@NonNull String username, @NonNull String password) {
        localRepository.setLocalUser(username, password);
        restRepository.setLocalUser(username, password);
//        TODO
//        loadLocalUserData();
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username) {
        Log.d(TAG, "isValidUser("+username+")");
        return getDefaultRepository().isValidUser(username);
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username, @NonNull String password) {
        return getDefaultRepository().isValidUser(username, password);
    }

    @Override
    public Single<Boolean> isValidNewUser(@NonNull String username, @NonNull String password) {
        return getDefaultRepository().isValidNewUser(username, password);
    }

    @Override
    public Single<ServerReplyType> changePassword(@NonNull String newPassword) {
        return getDefaultRepository().changePassword(newPassword);
    }

    @Override
    public Single<ServerReplyType> refreshToken(String token) {
        return restRepository.refreshToken(token);
    }

    @Override
    public Single<ServerReplyType> sendMessage(@NonNull String friend, @NonNull String message) {
        return getDefaultRepository().sendMessage(friend, message);
    }

    @Override
    public Single<List<Message>> getMessagesFrom(@NonNull String friend) {
        return getDefaultRepository().getMessagesFrom(friend);
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime) {
        return getDefaultRepository().getMessageFromOffset(friend, dateTime);
    }

    @Override
    public Single<ServerReplyType> addFriend(@NonNull String friend) {
        Log.d(TAG, "addFriend:" + friend);
        return getDefaultRepository().addFriend(friend);
    }

    @Override
    public Single<ServerReplyType> removeFriend(@NonNull String friend) {
        return getDefaultRepository().removeFriend(friend);
    }

    @Override
    public Single<List<User>> getFriendList() {
        return getDefaultRepository().getFriendList();
    }


//    public void loadLocalUserData() {
//        TODO
//        if(getLocalUser() != null) {
//            LocalUser user = getLocalUser();
//            //addFriends
//            getDefaultRepository().getFriendList()
//                    .subscribeOn(Schedulers.computation())
//                    .subscribe(user::addFriends)
//                    .dispose();
//
//            List<User> friends = user.getSortedFriendList();
//
//            for(User u : friends) {
//                getDefaultRepository().getMessagesFrom(u.getUsername())
//                        .subscribeOn(Schedulers.computation())
//                        .subscribe(m -> user.addMessages(u.getUsername(), m))
//                        .dispose();
//            }
//        }
//    }
}
