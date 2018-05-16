package de.web.ngthi.palaver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ServerRequest {

    @JsonProperty("Username")   private String username;
    @JsonProperty("Password")   private String password;
    @JsonProperty("NewPassword")private String newPassword;
    @JsonProperty("PushToken")  private String pushToken;
    @JsonProperty("Mimetype")   private String mimetype;
    @JsonProperty("ServerData") private String data;
    @JsonProperty("Friend")     private String friend;
    @JsonProperty("Recipient")  private String recipient;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXXX")
    @JsonProperty("Offset")     private LocalDateTime offset;

    private ServerRequest() {
        //user Builder
    }

    public static class Builder {

        private String username;
        private String password;
        private String newPassword;
        private String pushToken;
        private String mimetype;
        private String data;
        private String friend;
        private String recipient;
        private LocalDateTime offset;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder localUser(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public Builder token() {
            pushToken = "TOKEN_HERE!";
            return this;
        }

        public Builder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder friend(String friend) {
            this.friend = friend;
            return this;
        }

        public Builder mimetype() {
            mimetype = "text/plain";
            return this;
        }

        public Builder offset(LocalDateTime offset) {
            this.offset = offset;
            return this;
        }

        public ServerRequest build() {
            ServerRequest request = new ServerRequest();
            request.username = this.username;
            request.password = this.password;
            request.newPassword = this.newPassword;
            request.pushToken = this.pushToken;
            request.mimetype = this.mimetype;
            request.data = this.data;
            request.friend = this.friend;
            request.recipient = this.recipient;
            request.offset = this.offset;
            return request;
        }

    }

    public enum Type {
        USER_REGISTER,
        USER_VALIDATE,
        USER_PASSWORD,
        USER_PUSHTOKEN,

        MESSAGE_SEND,
        MESSAGE_GET,
        MESSAGE_GETOFFSET,

        FRIENDS_ADD,
        FRIENDS_REMOVE,
        FRIENDS_GET;
    }

}
