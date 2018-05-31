package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.repository.DataRepository;

@Singleton
@Component(modules = {DataRepository.class})
public interface DataRepositoryComponent {
    void inject(PalaverApplication application);
}
