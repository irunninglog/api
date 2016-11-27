package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.dashboard.ProgressInfo;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.workout.impl.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.impl.WorkoutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
final class DashboardStreaksService {

    private final IWorkoutEntityRepository workoutEntityRepository;
    private final DateService dateService;

    @Autowired
    public DashboardStreaksService(IWorkoutEntityRepository workoutEntityRepository,
                                   DateService dateService) {
        super();

        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
    }

    Collection<ProgressInfo> streaks(ProfileEntity profileEntity) {
        List<WorkoutEntity> workouts = workoutEntityRepository.findByUserId(profileEntity.getId());
        workouts.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        Streaks streaks = getStreaks(workouts);
        ProgressInfo current = new ProgressInfo()
                .setTitle("Current")
                .setSubTitle(streaks.getCurrent().getSpan() + " day(s)")
                .setTextOne(streaks.getCurrent().getCount() + " workout(s)")
                .setTextTwo(streaks.getCurrent().getCount() == 0 ? "No current streak!" : formatStreak(streaks.getCurrent()));

        ProgressInfo thisYear = new ProgressInfo()
                .setTitle("This Year")
                .setSubTitle(streaks.getThisYear().getSpan() + " day(s)")
                .setTextOne(streaks.getThisYear().getCount() + " workout(s)")
                .setTextTwo(streaks.getThisYear().getCount() == 0 ? "No streaks this year!" : formatStreak(streaks.getThisYear()));

        ProgressInfo ever = new ProgressInfo()
                .setTitle("Ever")
                .setSubTitle(streaks.getEver().getSpan() + " day(s)")
                .setTextOne(streaks.getEver().getCount() + " workout(s)")
                .setTextTwo(streaks.getEver().getCount() == 0 ? "No streaks this year!" : formatStreak(streaks.getEver()));

        List<ProgressInfo> progressInfo = new ArrayList<>(3);
        progressInfo.add(current);
        progressInfo.add(thisYear);
        progressInfo.add(ever);

        return progressInfo;
    }

    private Streaks getStreaks(List<WorkoutEntity> workouts) {
        Streak streak = null;
        List<Streak> streaks = new ArrayList<>();
        for (WorkoutEntity entity : workouts) {
            if (streak == null) {
                streak = getNewStreak(entity);
                streaks.add(streak);
            } else if (entity.getDate().isAfter(streak.getStartDate().minusDays(2))) {
                streak.count()
                        .setStartDate(entity.getDate());
            } else {
                streak = getNewStreak(entity);
                streaks.add(streak);
            }
        }

        Streaks result = new Streaks();
        result.setCurrent(new Streak());
        result.setThisYear(new Streak());
        result.setEver(new Streak());

        for (int i = 0; i < streaks.size(); i++) {
            if (i == 0 && streaks.get(i).getEndDate().isAfter(LocalDate.now().minusDays(2))) {
                // Only first streak could be current
                result.setCurrent(streaks.get(i));
                result.setThisYear(streaks.get(i));
                result.setEver(streaks.get(i));
            } else if (streaks.get(i).isThisYear() && streaks.get(i).getCount() > result.getThisYear().getCount()) {
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
