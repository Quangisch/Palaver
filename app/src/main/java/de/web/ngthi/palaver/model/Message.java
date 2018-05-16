package de.web.ngthi.palaver.model;

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
public class Message implements Comparator<Message>{

    private User sender;
    private User recipient;

    private String content;
    private DateTime dateTime;

    private static DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");


    public String getTimeString() {
        return timeFormatter.print(dateTime);
    }

    public String getDateString() {
        return dateFormatter.print(dateTime);
    }

    public String getDateTimeString() {
        return getDateString() + ", " + getTimeString();
    }

    public String toString() {
        return String.format("Message from %s to %s at %s %s: %s ", sender, recipient, getDateString(), getTimeString(), content);
    }

    @Override
    public int compare(Message m1, Message m2) {
        return m1.dateTime.compareTo(m2.dateTime);
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof Message))
            return false;
        Message m = (Message) o;
        return sender.equals(m.sender) && recipient.equals(m.recipient) && dateTime.equals(m.dateTime);
    }
}
