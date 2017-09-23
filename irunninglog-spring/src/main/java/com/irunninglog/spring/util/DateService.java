package com.irunninglog.spring.util;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

@Service
public final class DateService {

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

    public LocalDate monthStart(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
    }

}
