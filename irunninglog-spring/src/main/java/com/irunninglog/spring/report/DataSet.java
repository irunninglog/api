package com.irunninglog.spring.report;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.irunninglog.api.Unit;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.spring.json.UnitDeserializer;
import com.irunninglog.spring.json.UnitSerializer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class DataSet implements IDataSet {

    private String key;
    @JsonSerialize(using = UnitSerializer.class)
    @JsonDeserialize(using = UnitDeserializer.class)
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
    public void add(IDataPoint dataPoint) {
        data.add(dataPoint);
    }

    @Override
    public List<IDataPoint> getData() {
        return data;
    }

}
