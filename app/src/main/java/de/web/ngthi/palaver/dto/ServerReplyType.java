package de.web.ngthi.palaver.dto;

public enum ServerReplyType {
    USER_REGISTER_OK(1, "Benutzer erfolgreich angelegt"),
    USER_REGISTER_FAILED(0, "Benutzer existiert bereits"),
    USER_VALIDATE_OK(1, "Benutzer erfolgreich validiert"),
    //VALIDATION
    USER_VALIDATE_FAILED_PASSWORD(0, "Passwort nicht korrekt"),
    USER_VALIDATE_FAILED_USERNAME(0, "Benutzer existiert nicht"),
    USER_PASSWORD_OK(1, "Passwort erfolgreich aktualisiert"),
    USER_PASSWORD_FAILED(0, "Altes Passwort nicht korrekt"),
    USER_PUSHTOKEN_OK(1, "PushToken erfolgreich aktualisiert"),

    MESSAGE_SEND_OK(1, "Nachricht verschickt"),
    MESSAGE_SEND_FAILED(0, "Sender oder Empfänger dem System nicht bekannt"),
    MESSAGE_GET_OK(1, "Nachrichten abgerufen"),
    MESSAGE_GET_FAILED(0, "Empfänger unbekannt"),

    FRIENDS_ADD_OK(1, "Freund hinzugefügt"),
    FRIENDS_ADD_FAILED(0, "Freund bereits auf der Liste"),
    FRIENDS_REMOVE_OK(1, "Freund entfernt"),
    FRIENDS_REMOVE_FAILED(0, "Freund nicht auf der Liste"),
    FRIENDS_GET(1, "Freunde aufgelistet"),

    NETWORK_ERROR(0, "Netzwerkfehler");

    private final int MSGTYPE;
    private final String INFO;

    ServerReplyType(int msgType, String info) {
        this.MSGTYPE = msgType;
        this.INFO = info;
    }

    public String getInfo() {
        return INFO;
    }

    public Integer getMsgType() {
        return MSGTYPE;
    }

    public static boolean isType(ServerReplyType type, ServerReply reply) {
        return type.getMsgType().equals(reply.getMsgType()) && type.getInfo().equals(reply.getInfo());
    }

    public static ServerReplyType getType(ServerReply reply) {
        for(ServerReplyType type : ServerReplyType.values()) {
            if(isType(type, reply))
                return type;
        }
        return null;
    }
}