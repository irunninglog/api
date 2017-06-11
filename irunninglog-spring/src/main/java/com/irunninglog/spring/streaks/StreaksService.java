package com.irunninglog.spring.streaks;

import com.irunninglog.api.Progress;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreaksService implements IStreaksService {

    private final IFactory factory;

    @Autowired
    public StreaksService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public IStreaks getStreaks(IUser user) {
        IStreak longest = factory.get(IStreak.class)
                .setStartDate("2015-01-01")
                .setEndDate("2017-10-01")
                .setProgress(Progress.GOOD)
                .setDays(274)
                .setRuns(274)
                .setPercentage(100);
        IStreak current = factory.get(IStreak.class)
                .setStartDate("2017-06-10")
                .setEndDate("2017-06-12")
                .setProgress(Progress.BAD)
                .setDays(3)
                .setRuns(3)
                .setPercentage(1);
        IStreak thisYear = factory.get(IStreak.class)
                .setStartDate("2016-12-04")
                .setEndDate("2017-06-02")
                .setProgress(Progress.OK)
                .setDays(181)
                .setRuns(184)
                .setPercentage(68);

        return factory.get(IStreaks.class).setLongest(longest).setCurrent(current).setThisYear(thisYear);
    }

}
