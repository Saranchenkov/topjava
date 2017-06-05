package ru.javawebinar.topjava.format;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Ivan on 05.06.2017.
 */

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(final String text, final Locale locale) throws ParseException {
        return LocalTime.parse(text, getDateTimeFormatter(locale));
    }

    @Override
    public String print(final LocalTime ldt, final Locale locale) {
        return ldt.format(getDateTimeFormatter(locale));
    }

    private DateTimeFormatter getDateTimeFormatter(final Locale locale) {
        return DateTimeFormatter.ofPattern("HH:mm", locale);
    }
}
