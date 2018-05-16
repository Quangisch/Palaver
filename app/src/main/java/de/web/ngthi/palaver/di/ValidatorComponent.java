package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.presenter.LoginPresenter;
import de.web.ngthi.palaver.service.Validator;

@Singleton
@Component(modules ={Validator.class})
public interface ValidatorComponent {
    void inject(LoginPresenter presenter);
}
