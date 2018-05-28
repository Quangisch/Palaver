package de.web.ngthi.palaver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerData {

    @JsonProperty("Sender")     private String sender;
    @JsonProperty("Recipient")  private String recipient;
    @JsonProperty("Mimetype")   private String mimetype;
    @JsonProperty("Data")       private String serverData;
    @JsonProperty("DateTime")   private String dateTime;

    public String toString() {
        String result = "error:ServerData";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } catch(JsonProcessingException e) { }
        return result;
    }
}
