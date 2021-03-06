package de.web.ngthi.palaver.network.dto;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class ServerRequest {

    @JsonProperty("Username")   private String username;
    @JsonProperty("Password")   private String password;
    @JsonProperty("NewPassword")private String newPassword;
    @JsonProperty("PushToken")  private String pushToken;
    @JsonProperty("Mimetype")   private String mimetype;
    @JsonProperty("Data")       private String data;
    @JsonProperty("Friend")     private String friend;
    @JsonProperty("Recipient")  private String recipient;
    @JsonProperty("Offset")     private String offset;

    private ServerRequest() {
        //use Builder
    }

    @Override
    public String toString() {
        String result = "error";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } catch(JsonProcessingException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
        }
        return result;
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
        private String offset;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public Builder token(String token) {
            pushToken = token;
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

        public Builder offset(String offset) {
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
}
