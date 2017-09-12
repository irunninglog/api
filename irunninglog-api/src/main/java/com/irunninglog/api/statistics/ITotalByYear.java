package com.irunninglog.api.statistics;

public interface ITotalByYear {

    int getYear();

    String getTotal();

    ITotalByYear setYear(int year);

    ITotalByYear setTotal(String total);

}
