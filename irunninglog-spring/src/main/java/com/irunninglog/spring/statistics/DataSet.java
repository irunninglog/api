package com.irunninglog.spring.statistics;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.statistics.IDataPoint;
import com.irunninglog.api.statistics.IDataSet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("prototype")
final class DataSet implements IDataSet {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = DataPoint.class)
    private Collection<IDataPoint> points;

    @Override
    public Collection<IDataPoint> getPoints() {
        return points;
    }

    @Override
    public IDataSet setPoints(Collection<IDataPoint> points) {
        this.points = points;
        return this;
    }

}
