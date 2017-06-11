package com.irunninglog.spring.streaks;

import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Streaks implements IStreaks {

    private IStreak longest;
    private IStreak current;
    private IStreak thisYear;

    @Override
    public IStreak getLongest() {
        return longest;
    }

    @Override
    public Streaks setLongest(IStreak longest) {
        this.longest = longest;
        return this;
    }

    @Override
    public IStreak getCurrent() {
        return current;
    }

    @Override
    public IStreaks setCurrent(IStreak current) {
        this.current = current;
        return this;
    }

    @Override
    public IStreak getThisYear() {
        return thisYear;
    }

    @Override
    public IStreaks setThisYear(IStreak thisYear) {
        this.thisYear = thisYear;
        return this;
    }

}
