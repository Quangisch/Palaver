package de.web.ngthi.palaver.di;

import dagger.Component;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.repository.DataRepository;
import de.web.ngthi.palaver.view.FriendsActivity;
import de.web.ngthi.palaver.view.login.LoginActivity;
import de.web.ngthi.palaver.view.message.MessageActivity;

@Component(modules = {PalaverApplication.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(FriendsActivity friendsActivity);
    void inject(MessageActivity messageActivity);

    void inject(DataRepository dataRepository);
}
