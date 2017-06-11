package com.irunninglog.api.streaks;

public interface IStreaks {

    IStreaks setLongest(IStreak longest);

    IStreaks setCurrent(IStreak current);

    IStreaks setThisYear(IStreak thisYear);

    IStreak getLongest();

    IStreak getCurrent();

    IStreak getThisYear();

}
