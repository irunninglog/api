package com.irunninglog.spring.dashboard;

import com.irunninglog.api.Progress;
import com.irunninglog.api.dashboard.IProgressInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class ProgressInfo implements IProgressInfo {

    private String title;
    private String subTitle;
    private String textOne;
    private String textTwo;
    private int max;
    private int value;
    private int percentage;
    private Progress progress;

    @Override
    public IProgressInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IProgressInfo setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    @Override
    public IProgressInfo setTextOne(String text) {
        this.textOne = text;
        return this;
    }

    @Override
    public IProgressInfo setTextTwo(String text) {
        this.textTwo = text;
        return this;
    }

    @Override
    public IProgressInfo setMax(int max) {
        this.max = max;
        return this;
    }

    @Override
    public IProgressInfo setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public IProgressInfo setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

    @Override
    public IProgressInfo setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public String getTextOne() {
        return textOne;
    }

    @Override
    public String getTextTwo() {
        return textTwo;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

}
