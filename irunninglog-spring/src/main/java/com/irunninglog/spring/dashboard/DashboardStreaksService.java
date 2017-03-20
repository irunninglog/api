package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IProgressInfo;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.InternalService;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.WorkoutEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@InternalService
final class DashboardStreaksService {

    private static final String SUFFIX_DAYS = " day(s)";
    private static final String SUFFIX_WORKOUTS = " workout(s)";
    private static final Logger LOG = LoggerFactory.getLogger(DashboardStreaksService.class);

    private final IWorkoutEntityRepository workoutEntityRepository;
    private final DateService dateService;
    private final IFactory factory;

    @Autowired
    public DashboardStreaksService(IWorkoutEntityRepository workoutEntityRepository,
                                   DateService dateService,
                                   IFactory factory) {
        super();

        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
        this.factory = factory;
    }

    Collection<IProgressInfo> streaks(ProfileEntity profileEntity, int offset) {
        List<WorkoutEntity> workouts = workoutEntityRepository.findByProfileId(profileEntity.getId());
        workouts.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        Streaks streaks = getStreaks(workouts, offset);
        IProgressInfo current = factory.get(IProgressInfo.class)
                .setTitle("Current")
                .setSubTitle(streaks.getCurrent().getSpan() + SUFFIX_DAYS)
                .setTextOne(streaks.getCurrent().getCount() + SUFFIX_WORKOUTS)
                .setTextTwo(streaks.getCurrent().getCount() == 0 ? "No current streak!" : formatStreak(streaks.getCurrent()));

        IProgressInfo thisYear = factory.get(IProgressInfo.class)
                .setTitle("This Year")
                .setSubTitle(streaks.getThisYear().getSpan() + SUFFIX_DAYS)
                .setTextOne(streaks.getThisYear().getCount() + SUFFIX_WORKOUTS)
                .setTextTwo(streaks.getThisYear().getCount() == 0 ? "No streaks this year!" : formatStreak(streaks.getThisYear()));

        IProgressInfo ever = factory.get(IProgressInfo.class)
                .setTitle("Ever")
                .setSubTitle(streaks.getEver().getSpan() + SUFFIX_DAYS)
                .setTextOne(streaks.getEver().getCount() + SUFFIX_WORKOUTS)
                .setTextTwo(streaks.getEver().getCount() == 0 ? "No streaks this year!" : formatStreak(streaks.getEver()));

        List<IProgressInfo> progressInfo = new ArrayList<>(3);
        progressInfo.add(current);
        progressInfo.add(thisYear);
        progressInfo.add(ever);

        return progressInfo;
    }

    private Streaks getStreaks(List<WorkoutEntity> workouts, int offset) {
        Streak streak = null;
        List<Streak> streaks = new ArrayList<>();
        for (WorkoutEntity entity : workouts) {
            if (streak == null || !entity.getDate().isAfter(streak.getStartDate().minusDays(2))) {
                streak = getNewStreak(entity);
                streaks.add(streak);
            } else {
                streak.count().setStartDate(entity.getDate());
            }
        }

        Streaks result = new Streaks();
        result.setCurrent(new Streak());
        result.setThisYear(new Streak());
        result.setEver(new Streak());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime cutoff = dateService.clientTimeFromServerTime(ZonedDateTime.now(), offset).minusDays(2);

        LOG.debug("Processing streaks {}->{}", now, cutoff);

        for (int i = 0; i < streaks.size(); i++) {
            ZonedDateTime endDate = dateService.clientTimeFromServerTime(ZonedDateTime.now(), offset);
            endDate = (ZonedDateTime) streaks.get(i).getEndDate().adjustInto(endDate);

            LOG.debug("Streak from {} {} {} {}", streaks.get(i).getStartDate(), streaks.get(i).getEndDate(), endDate, cutoff);

            if (i == 0 && endDate.isAfter(cutoff)) {
                // Only first streak could be current
                result.setCurrent(streaks.get(i));
                result.setThisYear(streaks.get(i));
                result.setEver(streaks.get(i));
            } else if (isThisYearStreak(streaks.get(i), offset) && streaks.get(i).getCount() > result.getThisYear().getCount()) {
                result.setThisYear(streaks.get(i));

                if (streaks.get(i).getCount() > result.getEver().getCount()) {
                    result.setEver(streaks.get(i));
                }
            } else if (streaks.get(i).getCount() > result.getEver().getCount()) {
                result.setEver(streaks.get(i));
            }
        }

        return result;
    }

    private boolean isThisYearStreak(Streak streak, int offset) {
        return streak.getEndDate().isAfter(dateService.getYearStartDate(offset).minusDays(1));
    }

    private Streak getNewStreak(WorkoutEntity entity) {
        return new Streak()
                .count()
                .setEndDate(entity.getDate())
                .setStartDate(entity.getDate());
    }

    private String formatStreak(Streak streak) {
        return dateService.formatMedium(streak.getStartDate())
                + " through "
                + dateService.formatMedium(streak.getEndDate());
    }

}
