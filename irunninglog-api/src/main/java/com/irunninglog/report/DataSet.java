package com.irunninglog.report;

import java.util.ArrayList;
import java.util.List;

public final class DataSet {

    private final List<DataPoint> data = new ArrayList<>();

    public void add(DataPoint _dataPoint) {
        data.add(_dataPoint);
    }

    public List<DataPoint> getData() {
        return data;
    }

}
