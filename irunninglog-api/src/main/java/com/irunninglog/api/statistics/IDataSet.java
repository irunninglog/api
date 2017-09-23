package com.irunninglog.api.statistics;

import java.util.Collection;

public interface IDataSet {

    Collection<IDataPoint> getPoints();

    IDataSet setPoints(Collection<IDataPoint> points);

}
