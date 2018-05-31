package de.web.ngthi.palaver.mvp.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Locale;

import lombok.Getter;


@Getter
public final class LocalizedDateTime {

    private static org.joda.time.format.DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
    private static org.joda.time.format.DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");


    private LocalizedDateTime() {

    }

    //TODO
    public static void setLocale(Locale locale) {
        if(locale == Locale.GERMANY) {
            timeFormatter = DateTimeFormat.forPattern("HH:mm");
            dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");

        } else if(locale == Locale.US) {
            timeFormatter = DateTimeFormat.forPattern("hh:mm");
            dateFormatter = DateTimeFormat.forPattern("MM-dd-yyyy");

        } else { //DEFAULT
            timeFormatter = DateTimeFormat.forPattern("HH:mm");
            dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        }
    }

    public static String getTime(DateTime dateTime) {
        if(dateTime != null)
            return timeFormatter.print(dateTime);
        return null;
    }

    public static String getDate(DateTime dateTime) {
        if(dateTime != null)
            return dateFormatter.print(dateTime);
        return null;
    }

    public static String getDateTime(DateTime dateTime) {
        if(dateTime != null)
            return getDate(dateTime) + ", " + getTime(dateTime);
        return null;
    }
}
