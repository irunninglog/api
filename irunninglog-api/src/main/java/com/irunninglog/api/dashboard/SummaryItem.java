package com.irunninglog.api.dashboard;

import com.google.common.base.MoreObjects;
import com.irunninglog.api.Progress;

public class SummaryItem {

    private String label;
    private Progress progress;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("progress", progress)
                .toString();
    }

}
