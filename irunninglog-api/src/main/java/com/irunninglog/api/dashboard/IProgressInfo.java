package com.irunninglog.api.dashboard;

import com.irunninglog.api.Progress;

public interface IProgressInfo {

    IProgressInfo setTitle(String name);

    IProgressInfo setSubTitle(String description);

    IProgressInfo setTextOne(String text);

    IProgressInfo setTextTwo(String text);

    IProgressInfo setMax(int max);

    IProgressInfo setValue(int value);

    IProgressInfo setPercentage(int percentage);

    IProgressInfo setProgress(Progress progress);

    int getPercentage();

    int getMax();

    String getTitle();

    String getSubTitle();

    String getTextOne();

    String getTextTwo();

    Progress getProgress();

    int getValue();

}
