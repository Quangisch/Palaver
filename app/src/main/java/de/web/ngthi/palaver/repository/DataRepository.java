package de.web.ngthi.palaver.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

@Module
public class DataRepository implements IRepository {

    private RestRepository restRepository;
    private LocalRepository localRepository;

    @Inject
    public DataRepository (RestRepository restRepository, LocalRepository localRepository) {
        Log.d(getClass().getSimpleName(), "=========CONSTRUCTOR=========");
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
    public Completable changePassword(@NonNull String newPassword) {
        return getDefaultRepository().changePassword(newPassword);
    }

    @Override
    public Completable refreshToken() {
        return getDefaultRepository().refreshToken();
    }

    @Override
    public Completable sendMessage(@NonNull String friend, @NonNull String message) {
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
    public Completable addFriend(@NonNull String friend) {
        return getDefaultRepository().addFriend(friend);
    }

    @Override
    public Completable removeFriend(@NonNull String friend) {
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
