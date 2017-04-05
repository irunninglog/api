package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IProgressInfo;

import java.util.Comparator;

final class ProgressInfoComparator implements Comparator<IProgressInfo> {

    @Override
    public int compare(IProgressInfo o1, IProgressInfo o2) {
        int maxComparison = o1.getMax() > o2.getMax() ? 1 : -1;
        int percentComparison = o1.getPercentage() > o2.getPercentage() ? -1 : 1;

        return o1.getPercentage() == o2.getPercentage()
                ? maxComparison
                : percentComparison;
    }

}
