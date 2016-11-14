package com.irunninglog.api.date.impl;

import com.irunninglog.api.date.IDateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class DateService implements IDateService {

    @Override
    public String formatMedium(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date);
    }

}
