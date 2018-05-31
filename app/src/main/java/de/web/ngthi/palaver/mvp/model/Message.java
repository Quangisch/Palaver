package de.web.ngthi.palaver.mvp.model;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message implements Comparable<Message>{

    private User sender;
    private User recipient;

    private String content;
    private DateTime dateTime;
    private Status status;

    public String getDateTimeString() {
        return LocalizedDateTime.getDateTime(dateTime);
    }

    public String toString() {
        return String.format("Message from %s to %s at %s %s: %s ",
                sender, recipient, LocalizedDateTime.getDate(dateTime), LocalizedDateTime.getTime(dateTime), content);
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof Message))
            return false;
        Message m = (Message) o;
        return sender.equals(m.sender) && recipient.equals(m.recipient) && getDateTime().equals(m.getDateTime());
    }

    public DateTime getDateTime() {
        if(dateTime != null)
            return dateTime;
        return DateTime.now();
    }

    public enum Status {
        SENT,
        PENDING,
        FAILED
    }

    @Override
    public int compareTo(@NonNull Message o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
