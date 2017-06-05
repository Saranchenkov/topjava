package ru.javawebinar.topjava.format;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Ivan on 05.06.2017.
 */

public class LocalDateFormatter implements Formatter<LocalDate>{
    @Override
    public LocalDate parse(final String text, final Locale locale) throws ParseException {
        return LocalDate.parse(text, getDateTimeFormatter(locale));
    }

    @Override
    public String print(final LocalDate ldt, final Locale locale) {
        return ldt.format(getDateTimeFormatter(locale));
    }

    private DateTimeFormatter getDateTimeFormatter(final Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd", locale);
    }
}
