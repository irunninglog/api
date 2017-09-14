package com.irunninglog.api.statistics;

public interface ITotalByYear {

    int getYear();

    String getTotal();

    int getPercentage();

    ITotalByYear setYear(int year);

    ITotalByYear setTotal(String total);

    ITotalByYear setPercentage(int percentage);

}
