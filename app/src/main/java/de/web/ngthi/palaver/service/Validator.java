package de.web.ngthi.palaver.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.web.ngthi.palaver.controller.UserService;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class Validator {

    private final String passwordNotCorrect = "Passwort nicht korrekt";

    @Inject
    public Validator() {}


    public boolean isExistingUser(ServerReply reply) {
        return reply.getMsgType() == 1 ||
                (reply.getMsgType() == 0 && reply.getInfo().equals(passwordNotCorrect));
    }

    /*
     case "Benutzer erfolgreich angelegt": break;
                case "Benutzer existiert bereits": break;
                case "Benutzer erfolgreich validiert": break;
                case "Passwort nicht korrekt": break;
                case "Benutzer existiert nicht": break;
                case "Passwort erfolgreich aktualisiert": break;
                case "Altes Passwort nicht korrekt": break;
                case "PushToken erfolgreich aktualisiert": break;

                case "Nachricht verschickt": break;
                case "Sender oder Empfänger dem System nicht bekannt": break;
                case "Nachrichten abgerufen": break;
                case "Empfänger unbekannt": break;

                case "Freund hinzugefügt": break;
                case "Freund bereits auf der Liste": break;
                case "Freund entfernt": break;
                case "Freund nicht auf der Liste": break;
                case "Freunde aufgelistet": break;
     */

}
