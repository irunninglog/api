package com.irunninglog.spring.streaks;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.util.DateService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class StreaksService implements IStreaksService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IFactory factory;
    private final IStravaService stravaService;
    private final DistanceService distanceService;
    private final DateService dateService;

    @Autowired
    public StreaksService(IFactory factory,
                          IStravaService stravaService,
                          DistanceService distanceService,
                          DateService dateService) {
        super();

        this.factory = factory;
        this.stravaService = stravaService;
        this.distanceService = distanceService;
        this.dateService = dateService;
    }

    @Override
    public IStreaks getStreaks(IUser user, int offset) {
        List<IRun> activities = stravaService.runs(user);
        activities.sort((o1, o2) -> dateService.toLocalDate(o2.getStartTime()).compareTo(dateService.toLocalDate(o1.getStartTime())));

        List<IStreak> streaksList = new ArrayList<>();

        IStreak streak = null;
        for (IRun run : activities) {
            LocalDate runDate = dateService.toLocalDate(run.getStartTime());
            if (streak == null || !runDate.isAfter(toLocalDate(streak.getStartDate()).minusDays(2))) {
                streak = newStreak(runDate);
                streaksList.add(streak);
            } else {
                updateStreak(streak, runDate);
            }
        }

        List<IStreak> result = streaksList.stream().filter(item -> item.getDays() > 1).collect(Collectors.toList());

        result.sort((o1, o2) -> Integer.compare(o2.getDays(), o1.getDays()));

        return populate(factory.get(IStreaks.class), result, offset);
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
            if (!LocalDate.parse(value.getStartDate(), FORMATTER).isBefore(yearStart(minutes))) {
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
        return dateService.current(minutes).minusDays(1);
    }

    private LocalDate yearStart(int minutes) {
        return dateService.yearStart(minutes);
    }

}
