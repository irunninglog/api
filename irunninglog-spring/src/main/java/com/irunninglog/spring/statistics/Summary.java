package com.irunninglog.spring.statistics;

import com.irunninglog.api.statistics.ISummary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Summary implements ISummary {

    private String thisWeek;
    private String thisMonth;
    private String thisYear;
    private String allTime;

    @Override
    public String getThisWeek() {
        return thisWeek;
    }

    @Override
    public String getThisMonth() {
        return thisMonth;
    }

    @Override
    public String getThisYear() {
        return thisYear;
    }

    @Override
    public String getAllTime() {
        return allTime;
    }

    @Override
    public ISummary setThisWeek(String value) {
        thisWeek = value;
        return this;
    }

    @Override
    public ISummary setThisMonth(String value) {
        thisMonth = value;
        return this;
    }

    @Override
    public ISummary setThisYear(String value) {
        thisYear = value;
        return this;
    }

    @Override
    public ISummary setAllTime(String value) {
        allTime = value;
        return this;
    }

}
