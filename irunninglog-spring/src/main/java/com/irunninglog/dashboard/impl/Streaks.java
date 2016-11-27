package com.irunninglog.dashboard.impl;

class Streaks {


    private Streak current;
    private Streak thisYear;
    private Streak ever;

    Streak getCurrent() {
        return current;
    }

    Streaks setCurrent(Streak current) {
        this.current = current;
        return this;
    }

    Streak getThisYear() {
        return thisYear;
    }

    Streaks setThisYear(Streak thisYear) {
        this.thisYear = thisYear;
        return this;
    }

    Streak getEver() {
        return ever;
    }

    Streaks setEver(Streak ever) {
        this.ever = ever;
        return this;
    }

}
