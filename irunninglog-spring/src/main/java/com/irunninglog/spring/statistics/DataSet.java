package com.irunninglog.spring.statistics;

import com.irunninglog.api.statistics.IDataPoint;
import com.irunninglog.api.statistics.IDataSet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("prototype")
final class DataSet implements IDataSet {

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
