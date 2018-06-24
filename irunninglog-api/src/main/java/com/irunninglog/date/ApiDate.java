package com.irunninglog.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public final class ApiDate {

    public ZonedDateTime parseZonedDate(String dateTime) {
        return ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public LocalDate parseZonedDateAsLocalDate(String dateTime) {
        return parseZonedDate(dateTime).toLocalDate();
    }

    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public String format(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public LocalDate weekStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(DayOfWeek.MONDAY);
    }

    public LocalDate monthStart(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate monthStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate yearStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfYear());
    }

    public LocalDate current(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate();
    }

    private  ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

}
