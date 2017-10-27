package com.irunninglog.api.statistics;

import java.util.Map;

public interface IDataPoint {

    String getDate();

    Map<String, String> getValues();

    IDataPoint setDate(String date);

    IDataPoint setValues(Map<String, String> values);

}
