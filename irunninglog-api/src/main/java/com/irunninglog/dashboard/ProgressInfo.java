package com.irunninglog.dashboard;

import com.google.common.base.MoreObjects;
import com.irunninglog.Progress;

@SuppressWarnings("unused")
public final class ProgressInfo {

    private String title;
    private String subTitle;
    private String textOne;
    private String textTwo;
    private int percentage;
    private int max;
    private int value;
    private Progress progress;

    public int getMax() {
        return max;
    }

    public ProgressInfo setMax(int max) {
        this.max = max;
        return this;
    }

    public int getValue() {
        return value;
    }

    public ProgressInfo setValue(int value) {
        this.value = value;
        return this;
    }

    public Progress getProgress() {
        return progress;
    }

    public ProgressInfo setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProgressInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public ProgressInfo setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public String getTextOne() {
        return textOne;
    }

    public ProgressInfo setTextOne(String textOne) {
        this.textOne = textOne;
        return this;
    }

    public String getTextTwo() {
        return textTwo;
    }

    public ProgressInfo setTextTwo(String textTwo) {
        this.textTwo = textTwo;
        return this;
    }

    public int getPercentage() {
        return percentage;
    }

    public ProgressInfo setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("subTitle", subTitle)
                .add("textOne", textOne)
                .add("textTwo", textTwo)
                .add("percentage", percentage)
                .add("max", max)
                .add("value", value)
                .add("progress", progress)
                .toString();
    }

}
