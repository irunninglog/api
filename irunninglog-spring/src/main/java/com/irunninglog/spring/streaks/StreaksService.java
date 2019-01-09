package com.irunninglog.spring.streaks;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.progress.ProgressThresholds;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
import com.irunninglog.spring.strava.StravaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class StreaksService implements IStreaksService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final IFactory factory;
    private final StravaApiService stravaApiService;
    private final ApiMath apiMath;
    private final ApiDate apiDate;

    @Autowired
    public StreaksService(IFactory factory, StravaApiService stravaApiService, ApiMath apiMath, ApiDate apiDate) {
        super();

        this.factory = factory;
        this.stravaApiService = stravaApiService;
        this.apiMath = apiMath;
        this.apiDate = apiDate;
    }

    @Override
    public IStreaks getStreaks(IUser user, int offset) {
        List<IRun> activities = stravaApiService.runs(user);

        List<IStreak> streaksList = new ArrayList<>();

        IStreak streak = null;
        for (IRun run : activities) {
            LocalDate runDate = apiDate.parseZonedDateAsLocalDate(run.getStartTime(), offset);
            if (streak == null || !runDate.isAfter(toLocalDate(streak.getStartDate()).minusDays(2))) {
                streak = newStreak(runDate);
                streaksList.add(streak);
            } else {
                updateStreak(streak, runDate);
            }
        }

        List<IStreak> result = streaksList.stream().filter(item -> item.getDays() > 1).sorted((o1, o2) -> Integer.compare(o2.getDays(), o1.getDays())).collect(Collectors.toList());

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
        int percentage = apiMath.percentage(BigDecimal.valueOf(longest.getDays()), BigDecimal.valueOf(value.getDays())).intValue();

        return factory.get(IStreak.class)
                .setStartDate(value.getStartDate())
                .setEndDate(value.getEndDate())
                .setDays(value.getDays())
                .setRuns(value.getRuns())
                .setPercentage(percentage)
                .setProgress(apiMath.progress(BigDecimal.valueOf(percentage), new ProgressThresholds(20, 80, ProgressThresholds.ProgressMode.LOW_BAD)));
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
        return apiDate.current(minutes).minusDays(1);
    }

    private LocalDate yearStart(int minutes) {
        return apiDate.yearStart(minutes);
    }

}
