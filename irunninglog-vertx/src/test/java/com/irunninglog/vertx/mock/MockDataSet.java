package com.irunninglog.vertx.mock;

import com.irunninglog.api.Unit;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;

import java.util.List;

public class MockDataSet implements IDataSet {
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public IDataSet setKey(String key) {
        return null;
    }

    @Override
    public Unit getUnits() {
        return null;
    }

    @Override
    public IDataSet setUnits(Unit units) {
        return null;
    }

    @Override
    public void add(IDataPoint _dataPoint) {

    }

    @Override
    public List<IDataPoint> getData() {
        return null;
    }
}
