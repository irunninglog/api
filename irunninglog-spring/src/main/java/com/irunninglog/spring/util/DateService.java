package com.irunninglog.spring.util;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public final class DateService {

    private static final String WIRE_FORMAT = "MM-dd-yyyy";

    public LocalDate current(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate();
    }

    public LocalDate weekStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(DayOfWeek.MONDAY);
    }

    public LocalDate monthStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate yearStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfYear());
    }

    private ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

    public LocalDate toLocalDate(String date) {
        return ZonedDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate();
    }

    public LocalDate monthStart(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public String format(LocalDate date) {
        return DateTimeFormatter.ofPattern(WIRE_FORMAT).format(date);
    }

}
