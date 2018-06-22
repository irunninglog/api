package com.irunninglog.date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class ApiDateTest {

    private final ApiDate apiDate = new ApiDate();
    private final int offset = ZonedDateTime.now().getOffset().getTotalSeconds() / -60;

    @Test
    public void format() {
        LocalDate date = LocalDate.now();
        assertEquals(date.format(DateTimeFormatter.ISO_DATE), apiDate.format(date));

        ZonedDateTime dateTime = ZonedDateTime.now();
        assertEquals(dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), apiDate.format(dateTime));
    }

    @Test
    public void parseZonedDate() {
        ZonedDateTime dateTime = ZonedDateTime.now();

        assertEquals(dateTime.toLocalDate(), apiDate.parseZonedDate(apiDate.format(dateTime)));
    }

    @Test
    public void weekStart() {
        assertEquals(LocalDate.now().with(DayOfWeek.MONDAY), apiDate.weekStart(offset));
    }

    @Test
    public void monthStart() {
        assertEquals(LocalDate.now().withDayOfMonth(1), apiDate.monthStart(offset));
        assertEquals(LocalDate.now().withDayOfMonth(1), apiDate.monthStart(LocalDate.now()));
    }

    @Test
    public void yearStart() {
        assertEquals(LocalDate.now().withDayOfYear(1), apiDate.yearStart(offset));
    }

    @Test
    public void current() {
        assertEquals(LocalDate.now(), apiDate.current(offset));
    }

}
