package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.dashboard.ProgressInfo;

import java.util.Comparator;

final class ProgressInfoComparator implements Comparator<ProgressInfo> {

    @Override
    public int compare(ProgressInfo o1, ProgressInfo o2) {
        return o1.getPercentage() == o2.getPercentage()
                ? o1.getMax() > o2.getMax() ? 1 : -1
                : o1.getPercentage() > o2.getPercentage() ? -1 : 1;
    }

}
