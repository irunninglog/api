package com.irunninglog.spring.statistics;

import com.irunninglog.api.statistics.IDataPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
final class DataPoint implements IDataPoint {

    private String date;
    private Map<String, String> values;

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public Map<String, String> getValues() {
        return values;
    }

    @Override
    public IDataPoint setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public IDataPoint setValues(Map<String, String> values) {
        this.values = values;
        return this;
    }

}
