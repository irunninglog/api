package com.irunninglog.api.report;

import com.irunninglog.api.Progress;

public interface IDataPoint {

    String getLabel();

    IDataPoint setLabel(String label);

    String getValue();

    IDataPoint setValue(String value);

    Progress getProgress();

    IDataPoint setProgress(Progress progress);

}
