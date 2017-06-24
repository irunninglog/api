package com.irunninglog.spring.streaks;

import com.irunninglog.api.Progress;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
final class StreaksService implements IStreaksService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IFactory factory;
    private final IStravaService stravaService;

    @Autowired
    public StreaksService(IFactory factory, IStravaService stravaService) {
        this.factory = factory;
        this.stravaService = stravaService;
    }

    @Override
    public IStreaks getStreaks(IUser user) {
        List<IStravaRun> activities = stravaService.runs(user);
        activities.sort((o1, o2) -> o2.getStartTimeLocal().compareTo(o1.getStartTimeLocal()));


        List<IStreak> streaksList = new ArrayList<>();

        IStreak streak = null;
        for (IStravaRun run : activities) {
            LocalDate runDate = run.getStartTimeLocal().toLocalDate();
            if (streak == null || !runDate.isAfter(toLocalDate(streak.getStartDate()).minusDays(2))) {
                streak = newStreak(runDate);
                streaksList.add(streak);
            } else {
                updateStreak(streak, runDate);
            }
        }

        streaksList.sort((o1, o2) -> new Integer(o2.getDays()).compareTo(o1.getDays()));
        // TODO - Remove mock data
        streaksList.add(0, factory.get(IStreak.class)
                .setStartDate("2015-01-01")
                .setEndDate("2015-10-01")
                .setDays(274)
                .setRuns(274));

        return populate(factory.get(IStreaks.class), streaksList);
    }

    private IStreaks populate(IStreaks streaks, List<IStreak> streaksList) {
        return streaks.setLongest(findLongest(streaksList))
                .setCurrent(findCurrent(streaks.getLongest(), streaksList))
                .setThisYear(findThisYear(streaks.getLongest(), streaksList));
    }

    private IStreak findThisYear(IStreak longest, List<IStreak> streaksList) {
        for (IStreak value : streaksList) {
            // TODO - Need to factor in user time zone
            if (!LocalDate.parse(value.getEndDate(), FORMATTER).isBefore(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()))) {
                return copyOf(value, longest);
            }
        }

        return null;
    }

    private IStreak findCurrent(IStreak longest, List<IStreak> streaksList) {
        for (IStreak value : streaksList) {
            // TODO - Need to factor in user time zone
            if (!LocalDate.parse(value.getEndDate(), FORMATTER).isBefore(LocalDate.now().minusDays(1))) {
                return copyOf(value, longest);
            }
        }

        return null;
    }

    private IStreak copyOf(IStreak value, IStreak longest) {
        int percentage = value.getDays() * 100 / longest.getDays();

        return factory.get(IStreak.class)
                .setStartDate(value.getStartDate())
                .setEndDate(value.getEndDate())
                .setDays(value.getDays())
                .setRuns(value.getRuns())
                .setPercentage(percentage)
                .setProgress(progess(percentage));
    }

    private Progress progess(int percentage) {
        if (percentage >= 80) {
            return Progress.GOOD;
        } else if (percentage >= 20) {
            return Progress.OK;
        } else {
            return Progress.BAD;
        }
    }

    private IStreak findLongest(List<IStreak> streaksList) {
        if (!streaksList.isEmpty() && streaksList.get(0).getDays() > 1) {
            return copyOf(streaksList.get(0), streaksList.get(0));
        }

        return null;
    }

    private IStreak newStreak(LocalDate runDate) {
        String date = runDate.format(FORMATTER);

        return factory.get(IStreak.class)
                .setStartDate(date)
                .setEndDate(date)
                .setDays(1)
                .setRuns(1);
    }

    private void updateStreak(IStreak streak, LocalDate runDate) {
        if (!streak.getStartDate().equals(runDate.format(FORMATTER))) {
            streak.setDays(streak.getDays() + 1);
            streak.setStartDate(runDate.format(FORMATTER));
        }

        streak.setRuns(streak.getRuns() + 1);
    }

    private LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

}
