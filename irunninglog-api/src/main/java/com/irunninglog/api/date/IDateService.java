package com.irunninglog.api.date;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface IDateService {

    String formatMedium(LocalDate startDate);

    LocalDate getWeekEndDate(DayOfWeek dayOfTheWeek, int offset);

    LocalDate getWeekStartDate(DayOfWeek dayOfTheWeek, int offset);

    LocalDate getMonthStartDate(int offset);

    LocalDate getMonthEndDate(int offset);

    LocalDate getYearStartDate(int offset);

    LocalDate getYearEndDate(int offset);

}
