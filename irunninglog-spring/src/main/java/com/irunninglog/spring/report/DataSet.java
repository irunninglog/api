package com.irunninglog.spring.report;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.Unit;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class DataSet implements IDataSet {

    private String key;
    private Unit units;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = DataPoint.class)
    private final List<IDataPoint> data = new ArrayList<>();

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public IDataSet setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public Unit getUnits() {
        return units;
    }

    @Override
    public IDataSet setUnits(Unit units) {
        this.units = units;
        return this;
    }

    @Override
    public void add(IDataPoint _dataPoint) {
        data.add(_dataPoint);
    }

    @Override
    public List<IDataPoint> getData() {
        return data;
    }

}
