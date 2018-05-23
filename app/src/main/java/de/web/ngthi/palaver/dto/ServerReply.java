package de.web.ngthi.palaver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServerReply {

    @JsonProperty("MsgType")
    private int msgType;

    @JsonProperty("Info")
    private String info;

    @JsonProperty("Data")
    private Object[] data;

    private boolean hasData() {
        return data != null;
    }

    public boolean hasStringArray() {
        return hasData() && data instanceof String[];
    }

    public boolean hasDataArray() {
        return hasData() && data instanceof ServerData[];
    }
}
