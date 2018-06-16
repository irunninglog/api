package com.irunninglog.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public final class ApiDate {

    private ApiDate() { }

    public static LocalDate parseAsLocalDate(String dateTime) {
        return ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate();
    }

    public static String format(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public static String format(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static LocalDate weekStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(DayOfWeek.MONDAY);
    }

    public static LocalDate monthStart(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate monthStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate yearStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDate current(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate();
    }

    private static ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

}
