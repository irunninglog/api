package com.irunninglog.spring.report;

import com.irunninglog.api.Progress;
import com.irunninglog.api.report.IDataPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class DataPoint implements IDataPoint {

    private String label;
    private String value;
    private Progress progress;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public IDataPoint setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public IDataPoint setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public IDataPoint setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

}
