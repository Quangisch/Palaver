package de.web.ngthi.palaver.model;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

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

    private static DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");

    public String getTimeString() {
        if(dateTime != null)
            return timeFormatter.print(dateTime);
        return null;
    }

    public String getDateString() {
        if(dateTime != null)
            return dateFormatter.print(dateTime);
        return null;
    }

    public String getDateTimeString() {
        if(dateTime != null)
            return getDateString() + ", " + getTimeString();
        return null;
    }

    public String toString() {
        return String.format("Message from %s to %s at %s %s: %s ", sender, recipient, getDateString(), getTimeString(), content);
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

    @Override
    public int compareTo(@NonNull Message o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
