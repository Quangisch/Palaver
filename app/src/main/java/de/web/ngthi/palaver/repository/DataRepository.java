package de.web.ngthi.palaver.repository;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

@Module
public class DataRepository implements IRepository {

    private PalaverApplication application;
    private IRepository localRepository;
    private IRepository restRepository;

    @Inject
    public DataRepository (PalaverApplication application, RestRepository restRepository, LocalRepository localRepository) {
        this.application = application;
        this.restRepository = restRepository;
        this.localRepository = localRepository;

        restRepository.setLocalUser(application.getLocalUser());
        localRepository.setLocalUser(application.getLocalUser());
        Log.d("=========", application.getLocalUser().toString());
    }

    //TODO
    private IRepository getDefaultRepository() {
        return localRepository;
    }


    @Override
    public void setLocalUser(LocalUser user) {
        localRepository.setLocalUser(user);
        restRepository.setLocalUser(user);
    }

    @Override
    public Single<Boolean> isValidUser(String username) {
        return getDefaultRepository().isValidUser(username);
    }

    @Override
    public Single<Boolean> isValidUser(String username, String password) {
        return getDefaultRepository().isValidUser(username, password);
    }

    @Override
    public Single<Boolean> isValidNewUser(String username, String password) {
        return getDefaultRepository().isValidNewUser(username, password);
    }

    @Override
    public Completable changePassword(String newPassword) {
        return getDefaultRepository().changePassword(newPassword);
    }

    @Override
    public Completable refreshToken() {
        return getDefaultRepository().refreshToken();
    }

    @Override
    public Completable sendMessage(String friend, String message) {
        return getDefaultRepository().sendMessage(friend, message);
    }

    @Override
    public Single<List<Message>> getMessagesFrom(String friend) {
        return getDefaultRepository().getMessagesFrom(friend);
    }

    @Override
    public Single<List<Message>> getMessageFromOffset(String friend, String dateTime) {
        return getDefaultRepository().getMessageFromOffset(friend, dateTime);
    }

    @Override
    public Completable addFriend(String friend) {
        return getDefaultRepository().addFriend(friend);
    }

    @Override
    public Completable removeFriend(String friend) {
        return getDefaultRepository().removeFriend(friend);
    }

    @Override
    public Single<List<User>> getFriendList() {
        return getDefaultRepository().getFriendList();
    }
}
