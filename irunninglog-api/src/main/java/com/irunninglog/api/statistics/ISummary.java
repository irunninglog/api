package com.irunninglog.api.statistics;

public interface ISummary {

    String getThisWeek();

    String getThisMonth();

    String getThisYear();

    ISummary setThisWeek(String value);

    ISummary setThisMonth(String value);

    ISummary setThisYear(String value);

}
