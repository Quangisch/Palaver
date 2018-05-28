package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.controller.RestController;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.repository.RestRepository;

@Singleton
@Component(modules = RestController.class)
public interface RestComponent {
    void inject(RestRepository restRepository);
}
