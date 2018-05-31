package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.network.dto.ServerReplyType;
import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;
import io.reactivex.Single;

@Module
public class DataRepository implements IRepository {

    private static final String TAG = DataRepository.class.getSimpleName();

    private IRepository restRepository;
    private IRepository localRepository;

    @Inject
    public DataRepository (RestRepository restRepository, LocalRepository localRepository) {
        Log.d(TAG, "==============constructor==============");
        this.restRepository = restRepository;
        this.localRepository = localRepository;
    }

    //TODO
    private IRepository getDefaultRepository() {
        return restRepository;
    }

    @Override
    public void setLocalUser(@NonNull String username, @NonNull String password) {
        Log.d(TAG, String.format("setLocalUser(%s, %s)", username, password));
        localRepository.setLocalUser(username, password);
        restRepository.setLocalUser(username, password);

//        loadLocalUserData(); //TODO
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username) {
        Log.d(TAG, String.format("isValidUser(%s)", username));
        return getDefaultRepository().isValidUser(username);
    }

    @Override
    public Single<Boolean> isValidUser(@NonNull String username, @NonNull String password) {
        Log.d(TAG, String.format("isValidUser(%s, %s)", username, password));
        return getDefaultRepository().isValidUser(username, password);
    }

    @Override
    public Single<Boolean> registerNewUser(@NonNull String username, @NonNull String password) {
        Log.d(TAG, String.format("registerNewUser(%s, %s)", username, password));
        return getDefaultRepository().registerNewUser(username, password);
    }

    @Override
    public Single<ServerReplyType> changePassword(@NonNull String newPassword) {
        Log.d(TAG, String.format("changePassword(%s)", newPassword));
        return getDefaultRepository().changePassword(newPassword);
    }

    @Override
    public Single<ServerReplyType> refreshToken(String token) {
        Log.d(TAG, String.format("refreshToken(%s)", token));
        return restRepository.refreshToken(token);
    }

    @Override
    public Single<ServerReplyType> sendMessage(@NonNull String friend, @NonNull String message) {
        Log.d(TAG, String.format("sendMessage(%s, %s)", friend, message));
        return getDefaultRepository().sendMessage(friend, message);
    }

    @Override
    public Single<List<Message>> getMessagesFrom(@NonNull String friend) {
        Log.d(TAG, String.format("getMessagesFrom(%s)", friend));
        return getDefaultRepository().getMessagesFrom(friend);
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(@NonNull String friend, @NonNull String dateTime) {
        Log.d(TAG, String.format("getMessageFromOffset(%s, %s)", friend, dateTime));
        return getDefaultRepository().getMessageFromOffset(friend, dateTime);
    }

    @Override
    public Single<ServerReplyType> addFriend(@NonNull String friend) {
        Log.d(TAG, String.format("addFriend(%s)", friend));
        return getDefaultRepository().addFriend(friend);
    }

    @Override
    public Single<ServerReplyType> removeFriend(@NonNull String friend) {
        Log.d(TAG, String.format("removeFriend(%s)", friend));
        return getDefaultRepository().removeFriend(friend);
    }

    @Override
    public Single<List<User>> getFriendList() {
        Log.d(TAG, "removeFriend()");
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
