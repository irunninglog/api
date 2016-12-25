package com.irunninglog.spring.date;

import com.irunninglog.spring.service.InternalService;
import org.springframework.util.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;

@InternalService
@SuppressWarnings({"WeakerAccess", "unused"})
public final class DateService {

    public static final String DATE_INCOMING = "^(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-](19|20)\\d\\d$";
    private static final String WIRE_FORMAT = "MM-dd-yyyy";

    public String formatFull(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(date);
    }

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

    ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

    public String getThisWeekEndFull(DayOfWeek weekStart, final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime nextWeekStartDay = clientTime.with(TemporalAdjusters.next(weekStart)).minusDays(1);

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(nextWeekStartDay);
    }

    public String getThisMonthEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfMonth());

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

    public String getLastYearEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfYear()).minusYears(1);

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

    public String getThisYearEndFull(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime endDay = clientTime.with(TemporalAdjusters.lastDayOfYear());

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(endDay);
    }

    public LocalDate parse(String date) {
        Assert.notNull(date, "Date cannot be null");

        return LocalDate.parse(date, DateTimeFormatter.ofPattern(WIRE_FORMAT));
    }

    public String format(LocalDate date) {
        Assert.notNull(date, "Date cannot be null");

        return DateTimeFormatter.ofPattern(WIRE_FORMAT).format(date);
    }

    public String formatTime(long time) {
        DateTimeFormatter formatter;
        if (time < 3600000) {
            formatter = DateTimeFormatter.ofPattern("mm:ss");
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        LocalDateTime local = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
        return formatter.format(local);
    }

    public String getThisYear(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        return DateTimeFormatter.ofPattern("yyyy").format(clientTime);
    }

    public String getLastYear(final int _offset) {
        return String.valueOf(Integer.parseInt(getThisYear(_offset)) - 1);
    }

    public String getThisWeekEndLong(DayOfWeek weekStart, final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        ZonedDateTime nextWeekStartDay = clientTime.with(TemporalAdjusters.next(weekStart)).minusDays(1);

        return DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(nextWeekStartDay);
    }

    public String getCurrentDateTime() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).format(ZonedDateTime.now());
    }

    public String getThisMonth(final int _offset) {
        ZonedDateTime clientTime = clientTimeFromServerTime(ZonedDateTime.now(), _offset);
        return DateTimeFormatter.ofPattern("MMMM yyyy").format(clientTime);
    }

}
