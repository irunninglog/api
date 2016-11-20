package com.irunninglog.api.date.impl;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateService {

    public String formatMedium(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date);
    }

    public LocalDate getWeekStartDate(DayOfWeek dayOfTheWeek, int offset) {
        return getWeekEndDate(dayOfTheWeek, offset).minusDays(6);
    }

    public LocalDate getWeekEndDate(DayOfWeek dayOfTheWeek, int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.next(dayOfTheWeek)).minusDays(1));
    }

    public LocalDate getMonthStartDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.firstDayOfMonth()));
    }

    public LocalDate getMonthEndDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public LocalDate getYearStartDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.firstDayOfYear()));
    }

    public LocalDate getYearEndDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.lastDayOfYear()));
    }

    private LocalDate asLocalDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDate();
    }

    private ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

    @SuppressWarnings("MagicConstant")
    public String getThisWeekEndFull(DayOfWeek weekStart, final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime nextWeekStartDay = clientTime.with(TemporalAdjusters.next(weekStart)).minusDays(1);

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(nextWeekStartDay);
    }

    @SuppressWarnings("MagicConstant")
    public String getThisMonthEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfMonth());

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

    @SuppressWarnings("MagicConstant")
    public String getLastYearEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfYear()).minusYears(1);

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

    @SuppressWarnings("MagicConstant")
    public String getThisYearEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfYear());

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

}
