package com.irunninglog.spring.streaks;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
final class StreaksService implements IStreaksService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IFactory factory;
    private final IStravaService stravaService;
    private final DistanceService distanceService;

    @Autowired
    public StreaksService(IFactory factory,
                          IStravaService stravaService,
                          DistanceService distanceService) {
        super();

        this.factory = factory;
        this.stravaService = stravaService;
        this.distanceService = distanceService;
    }

    @Override
    public IStreaks getStreaks(IUser user, int offset) {
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

        streaksList.sort((o1, o2) -> Integer.compare(o2.getDays(), o1.getDays()));

        return populate(factory.get(IStreaks.class), streaksList, offset);
    }

    private IStreaks populate(IStreaks streaks, List<IStreak> streaksList, int minutes) {
        return streaks.setLongest(findLongest(streaksList))
                .setCurrent(findCurrent(streaks.getLongest(), streaksList, minutes))
                .setThisYear(findThisYear(streaks.getLongest(), streaksList, minutes));
    }

    private IStreak findThisYear(IStreak longest, List<IStreak> streaksList, int minutes) {
        if (longest == null) {
            return null;
        }

        for (IStreak value : streaksList) {
            if (!LocalDate.parse(value.getEndDate(), FORMATTER).isBefore(yearStart(minutes))) {
                return copyOf(value, longest);
            }
        }

        return null;
    }

    private IStreak findCurrent(IStreak longest, List<IStreak> streaksList, int minutes) {
        if (longest == null) {
            return null;
        }

        for (IStreak value : streaksList) {
            if (!LocalDate.parse(value.getEndDate(), FORMATTER).isBefore(current(minutes))) {
                return copyOf(value, longest);
            }
        }

        return null;
    }

    private IStreak copyOf(IStreak value, IStreak longest) {
        int percentage = distanceService.percentage(longest.getDays(), value.getDays());

        return factory.get(IStreak.class)
                .setStartDate(value.getStartDate())
                .setEndDate(value.getEndDate())
                .setDays(value.getDays())
                .setRuns(value.getRuns())
                .setPercentage(percentage)
                .setProgress(distanceService.progressWhereLowIsBad(percentage));
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

    private LocalDate current(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().minusDays(2);
    }

    private LocalDate yearStart(int minutes) {
        return clientTimeFromServerTime(ZonedDateTime.now(), minutes).toLocalDate().with(TemporalAdjusters.firstDayOfYear());
    }

    private ZonedDateTime clientTimeFromServerTime(ZonedDateTime time, int minutes) {
        ZonedDateTime utc = time.withZoneSameInstant(ZoneOffset.UTC);
        return utc.withZoneSameInstant(ZoneOffset.ofTotalSeconds(minutes * 60 * -1));
    }

}
