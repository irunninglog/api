package com.irunninglog.api.dashboard;

public class DashboardInfo {

    private SummaryItem thisWeek;
    private SummaryItem thisMonth;
    private SummaryItem thisYear;
    private SummaryItem lastYear;

    public SummaryItem getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(SummaryItem thisWeek) {
        this.thisWeek = thisWeek;
    }

    public SummaryItem getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(SummaryItem thisMonth) {
        this.thisMonth = thisMonth;
    }

    public SummaryItem getThisYear() {
        return thisYear;
    }

    public void setThisYear(SummaryItem thisYear) {
        this.thisYear = thisYear;
    }

    public SummaryItem getLastYear() {
        return lastYear;
    }

    public void setLastYear(SummaryItem lastYear) {
        this.lastYear = lastYear;
    }

}
