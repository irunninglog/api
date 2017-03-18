package com.irunninglog.api.report;

import com.irunninglog.api.Unit;

import java.util.List;

public interface IDataSet {

    String getKey();

    IDataSet setKey(String key);

    Unit getUnits();

    IDataSet setUnits(Unit units);

    void add(IDataPoint dataPoint);

    List<IDataPoint> getData();

}
