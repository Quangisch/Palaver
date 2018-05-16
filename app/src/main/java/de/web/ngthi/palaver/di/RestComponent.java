package de.web.ngthi.palaver.di;

import android.app.Activity;
import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.presenter.LoginPresenter;

@Singleton
@Component(modules ={RestController.class})
public interface RestComponent {
    void inject(LoginPresenter presenter);
    void inject(Application application);
}
