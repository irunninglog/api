package com.irunninglog.api.progress;

public class ProgressThresholds {

    public enum ProgressMode {
        LOW_GOOD,
        LOW_BAD
    }

    private final int threshold1;
    private final int threshold2;
    private final ProgressMode mode;

    public ProgressThresholds(int threshold1, int threshold2, ProgressMode mode) {
        this.threshold1 = threshold1;
        this.threshold2 = threshold2;
        this.mode = mode;
    }

    public int getThreshold1() {
        return threshold1;
    }

    public int getThreshold2() {
        return threshold2;
    }

    public ProgressMode getMode() {
        return mode;
    }

}
