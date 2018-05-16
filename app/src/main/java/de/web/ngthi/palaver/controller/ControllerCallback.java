package de.web.ngthi.palaver.controller;


import android.util.Log;

import de.web.ngthi.palaver.dto.ServerReply;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerCallback implements Callback<ServerReply> {
    @Override
    public void onResponse(Call<ServerReply> call, Response<ServerReply> response) {

        if (response.isSuccessful()) {
            String info = response.body().getInfo();
            switch(info) {
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
            }
        } else {
            // error response, no access to resource?
        }
    }

    @Override
    public void onFailure(Call<ServerReply> call, Throwable t) {
        Log.d(call.toString(), t.getMessage());
    }

}
