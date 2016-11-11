package com.irunninglog.api.dashboard;

import com.google.common.base.MoreObjects;
import com.irunninglog.api.Progress;

public class ProgressInfo {

    private String title;
    private String subTitle;
    private String textOne;
    private String textTwo;
    private int percentage;
    private int max;
    private int value;
    private Progress progress;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTextOne() {
        return textOne;
    }

    public void setTextOne(String textOne) {
        this.textOne = textOne;
    }

    public String getTextTwo() {
        return textTwo;
    }

    public void setTextTwo(String textTwo) {
        this.textTwo = textTwo;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
