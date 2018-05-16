package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.presenter.FriendsPresenter;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.presenter.MessagePresenter;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.Validator;

@Singleton
@Component(modules ={Validator.class})
public interface ValidatorComponent {
    void inject(LoginPresenter presenter);
    void inject(FriendsPresenter presenter);
    void inject(MessagePresenter presenter);

    void inject(DataRepository dataRepository);
}
