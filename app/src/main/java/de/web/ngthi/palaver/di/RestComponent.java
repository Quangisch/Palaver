package de.web.ngthi.palaver.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.presenter.FriendsPresenter;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.presenter.MessagePresenter;
import de.web.ngthi.palaver.repository.DataRepository;
import retrofit2.Retrofit;

@Singleton
@Component(modules ={RestController.class})
public interface RestComponent {
    Retrofit retrofit();
    UserService userService();

    void inject(LoginPresenter presenter);
    void inject(FriendsPresenter presenter);
    void inject(MessagePresenter presenter);
    void inject(DataRepository dataRepository);
}
