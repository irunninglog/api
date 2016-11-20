package com.irunninglog.api.dashboard.impl;

class Streaks {


    private Streak current;
    private Streak thisYear;
    private Streak ever;

    public Streak getCurrent() {
        return current;
    }

    public Streaks setCurrent(Streak current) {
        this.current = current;
        return this;
    }

    public Streak getThisYear() {
        return thisYear;
    }

    public Streaks setThisYear(Streak thisYear) {
        this.thisYear = thisYear;
        return this;
    }

    public Streak getEver() {
        return ever;
    }

    public Streaks setEver(Streak ever) {
        this.ever = ever;
        return this;
    }

}
