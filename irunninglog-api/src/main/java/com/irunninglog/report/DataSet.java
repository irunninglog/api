package com.irunninglog.report;

import com.irunninglog.Unit;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class DataSet {

    private String key;
    private Unit units;

    private final List<DataPoint> data = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public DataSet setKey(String key) {
        this.key = key;
        return this;
    }

    public Unit getUnits() {
        return units;
    }

    public DataSet setUnits(Unit units) {
        this.units = units;
        return this;
    }

    public void add(DataPoint _dataPoint) {
        data.add(_dataPoint);
    }

    public List<DataPoint> getData() {
        return data;
    }

}
