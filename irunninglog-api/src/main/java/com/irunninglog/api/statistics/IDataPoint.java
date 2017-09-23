package com.irunninglog.api.statistics;

public interface IDataPoint {

    String getDate();

    String getLabel();

    String getValueFormatted();

    String getValue();

    IDataPoint setDate(String date);

    IDataPoint setLabel(String label);

    IDataPoint setValueFormatted(String value);

    IDataPoint setValue(String value);

}
