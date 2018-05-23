package de.web.ngthi.palaver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerData {

    @JsonProperty("Sender")     private String sender;
    @JsonProperty("Recipient")  private String recipient;
    @JsonProperty("Mimetype")   private String mimetype;
    @JsonProperty("ServerData") private String serverData;
    @JsonProperty("DateTime")   private DateTime dateTime;
}
