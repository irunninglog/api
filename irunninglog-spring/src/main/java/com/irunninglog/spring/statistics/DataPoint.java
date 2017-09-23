package com.irunninglog.spring.statistics;

import com.irunninglog.api.statistics.IDataPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class DataPoint implements IDataPoint {

    private String date;
    private String label;
    private String valueFormatted;
    private String value;

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getValueFormatted() {
        return valueFormatted;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public IDataPoint setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public IDataPoint setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public IDataPoint setValueFormatted(String value) {
        this.valueFormatted = value;
        return this;
    }

    @Override
    public IDataPoint setValue(String value) {
        this.value = value;
        return this;
    }

}
