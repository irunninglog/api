package com.irunninglog.api.date.impl;

import com.irunninglog.api.date.IDateService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateService implements IDateService {

    @Override
    public String formatMedium(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date);
    }

    @Override
    public LocalDate getWeekStartDate(DayOfWeek dayOfTheWeek, int offset) {
        return getWeekEndDate(dayOfTheWeek, offset).minusDays(6);
    }

    @Override
    public LocalDate getWeekEndDate(DayOfWeek dayOfTheWeek, int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.next(dayOfTheWeek)).minusDays(1));
    }

    @Override
    public LocalDate getMonthStartDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.firstDayOfMonth()));
    }

    @Override
    public LocalDate getMonthEndDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.lastDayOfMonth()));
    }

    @Override
    public LocalDate getYearStartDate(int offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), offset);
        return asLocalDate(clientTime.with(TemporalAdjusters.firstDayOfYear()));
    }

    @Override
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

}
