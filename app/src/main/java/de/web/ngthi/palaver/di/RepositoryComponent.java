package de.web.ngthi.palaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.repository.LocalRepository;
import de.web.ngthi.palaver.repository.RestRepository;

@Singleton
@Component(modules ={LocalRepository.class, RestRepository.class})
public interface RepositoryComponent {
    void inject(DataRepository repository);
}
