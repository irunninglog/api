package com.irunninglog.spring.date;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class DateServiceTest extends AbstractTest {

    private DateService dateService;

    private int offset;

    @Override
    public void afterBefore(ApplicationContext context) {
        offset = ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1;

        dateService = context.getBean(DateService.class);
    }

    @Test
    public void testGetThisYear() {
        String expected = new SimpleDateFormat("yyyy").format(new Date());
        assertEquals(expected, dateService.getThisYear(0));
    }

    @Test
    public void testGetLastYear() {
        int expected = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())) - 1;
        assertEquals(String.valueOf(expected), dateService.getLastYear(offset));
    }

    @Test
    public void testGetThisMonth() {
        String expected = new SimpleDateFormat("MMMM yyyy").format(new Date());
        assertEquals(expected, dateService.getThisMonth(offset));
    }

    @Test
    public void testGetCurrentDateTime() {
        assertNotNull(dateService.getCurrentDateTime());
    }

    @Test
    public void testFormatDate() {
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        String expected = DateFormat.getDateInstance(DateFormat.FULL).format(Date.from(instant));
        assertEquals(expected, dateService.formatFull(localDate));
    }

    @Test
    public void testFormatShortTime() {
        String expected = "00:58";
        assertEquals(expected, dateService.formatTime(58000));
    }

    @Test
    public void testFormatMediumTime() {
        String expected = "19:50";
        assertEquals(expected, dateService.formatTime(1190000));
    }

    @Test
    public void testFormatLongTime() {
        String expected = "03:06:59";
        assertEquals(expected, dateService.formatTime(11219000));
    }

    @Test
    public void testGetThisWeekEnd() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        String expected = new SimpleDateFormat("MMMM dd, yyyy").format(calendar.getTime());

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        int seconds = offset.getTotalSeconds() / (-60);
        assertEquals(expected, dateService.getThisWeekEndLong(DayOfWeek.MONDAY, seconds));
    }

    @Test
    public void testGetThisWeekEndForAllWeekStart() {
        verifyWeekEnd(DayOfWeek.MONDAY);
        verifyWeekEnd(DayOfWeek.TUESDAY);
        verifyWeekEnd(DayOfWeek.WEDNESDAY);
        verifyWeekEnd(DayOfWeek.THURSDAY);
        verifyWeekEnd(DayOfWeek.FRIDAY);
        verifyWeekEnd(DayOfWeek.SATURDAY);
        verifyWeekEnd(DayOfWeek.SUNDAY);
    }

    private void verifyWeekEnd(DayOfWeek startDay) {
        String weekEnd = dateService.getThisWeekEndLong(startDay, offset);

        int javaStartDay = startDay.getValue();
        if (javaStartDay <= 6) {
            javaStartDay++;
        } else {
            javaStartDay = 1;
        }

        Calendar cal = GregorianCalendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);

        if (today >= javaStartDay) {
            cal.add(Calendar.DAY_OF_MONTH, 6 - (today - javaStartDay));
        } else {
            cal.add(Calendar.DAY_OF_MONTH, (6 - (today - javaStartDay)) % 7);
        }

        String expected = new SimpleDateFormat("MMMM dd, yyyy").format(cal.getTime());
        assertEquals(expected, weekEnd);
    }

    @Test
    public void testFormatNullDate() {
        try {
            dateService.format(null);
            fail("Should not have been able to format a null date");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testFormatSuccess() {
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        assertEquals(new SimpleDateFormat("MM-dd-yyyy").format(Date.from(instant)), dateService.format(date));
    }

    @Test
    public void testParseSuccess() {
        String string = "12-31-1976";
        LocalDate date = dateService.parse(string);
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        assertEquals(string, new SimpleDateFormat("MM-dd-yyyy").format(Date.from(instant)));
    }

    @Test
    public void testParseNull() {
        try {
            dateService.parse(null);
            fail("Should not have been able to parse a null date");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testParseInvalid() {
        try {
            dateService.parse("abc");
            fail("Should not have been able to parse an invalid date");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testClientTimeFromServerTimeUTCServerTokyoClient() {
        ZonedDateTime time = ZonedDateTime.of(2014, 1, 31, 20, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime output = dateService.clientTimeFromServerTime(time, -9 * 60);
        assertEquals(output.getMonth(), Month.FEBRUARY);
        assertEquals(output.getDayOfMonth(), 1);
        assertEquals(output.getOffset().getTotalSeconds(), 9 * 60 * 60);
    }

    @Test
    public void testClientTimeFromServerTimeUTCServerNewYorkClient() {
        ZonedDateTime time = ZonedDateTime.of(2014, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime output = dateService.clientTimeFromServerTime(time, 5 * 60);
        assertEquals(output.getMonth(), Month.JANUARY);
        assertEquals(output.getDayOfMonth(), 31);
        assertEquals(output.getOffset().getTotalSeconds(), -5 * 60 * 60);
    }

    @Test
    public void testClientTimeFromServerTimeNewYorkServerNewYorkClient() {
        ZonedDateTime time = ZonedDateTime.of(2014, 2, 1, 0, 0, 0, 0, ZoneOffset.ofHours(-5));
        ZonedDateTime output = dateService.clientTimeFromServerTime(time, 5 * 60);
        assertEquals(output.getMonth(), Month.FEBRUARY);
        assertEquals(output.getDayOfMonth(), 1);
        assertEquals(output.getOffset().getTotalSeconds(), -5 * 60 * 60);
    }

    @Test
    public void testFormatMedium() {
        LocalDate date = LocalDate.now();

        String expected = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date);

        assertEquals(expected, dateService.formatMedium(date));
    }

    @Test
    public void testGetWeekStartDate() {
        LocalDate weekStart = dateService.getWeekStartDate(DayOfWeek.MONDAY, 300);
        assertTrue(!weekStart.isAfter(LocalDate.now()));
    }

    @Test
    public void testGetMonthStartDate1() {
        LocalDate weekStart = dateService.getMonthStartDate(300);
        assertTrue(!weekStart.isAfter(LocalDate.now()));
    }

    @Test
    public void testGetMonthStartDate2() {
        LocalDate weekStart = dateService.getMonthStartDate(LocalDate.now());
        assertTrue(!weekStart.isAfter(LocalDate.now()));
    }

    @Test
    public void testGetYearStartDate1() {
        LocalDate weekStart = dateService.getYearStartDate(300);
        assertTrue(!weekStart.isAfter(LocalDate.now()));
    }

    @Test
    public void testGetYearStartDate2() {
        LocalDate weekStart = dateService.getYearStartDate(LocalDate.now());
        assertTrue(!weekStart.isAfter(LocalDate.now()));
    }

    @Test
    public void testGetMonthEndDate() {
        LocalDate date = dateService.getMonthEndDate(300);
        assertTrue(!date.isBefore(LocalDate.now()));
    }

    @Test
    public void testGetYearEndDate() {
        LocalDate date = dateService.getYearEndDate(300);
        assertTrue(!date.isBefore(LocalDate.now()));
    }

    @Test
    public void testGetThisWeekEndFull() {
        assertNotNull(dateService.getThisWeekEndFull(DayOfWeek.MONDAY, 300));
    }

    @Test
    public void testGetThisYearEndFull() {
        assertNotNull(dateService.getThisYearEndFull(300));
    }

    @Test
    public void testGetThisMonthEndFull() {
        assertNotNull(dateService.getThisMonthEndFull(300));
    }

    @Test
    public void testGetLastYearEndFull() {
        assertNotNull(dateService.getLastYearEndFull(300));
    }

    @Test
    public void formatMonthMedium() {
        LocalDate now = LocalDate.now();

        assertEquals(DateTimeFormatter.ofPattern("MMMM yyyy").format(now), dateService.formatMonthMedium(now));
    }

    @Test
    public void formatMonthShort() {
        LocalDate now = LocalDate.now();

        assertEquals(DateTimeFormatter.ofPattern("MMM").format(now), dateService.formatMonthShort(now));
    }

    @Test
    public void formatYear() {
        LocalDate now = LocalDate.now();

        assertEquals(DateTimeFormatter.ofPattern("yyyy").format(now), dateService.formatYear(now));
    }

}
