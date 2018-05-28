package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.presenter.FriendsPresenter;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.presenter.MessagePresenter;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.IRepository;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {DataRepository.class})
public interface DataRepositoryComponent {
    void inject(PalaverApplication application);
}
