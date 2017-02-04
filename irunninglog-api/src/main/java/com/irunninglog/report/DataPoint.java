package com.irunninglog.report;

import com.irunninglog.api.Progress;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class DataPoint {

    private String label;
    private String value;
    private Progress progress;

    public String getLabel() {
        return label;
    }

    public DataPoint setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getValue() {
        return value;
    }

    public DataPoint setValue(String value) {
        this.value = value;
        return this;
    }

    public Progress getProgress() {
        return progress;
    }

    public DataPoint setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

}
