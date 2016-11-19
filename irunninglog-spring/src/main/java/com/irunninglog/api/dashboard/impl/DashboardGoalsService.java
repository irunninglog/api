package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.dashboard.ProgressInfo;
import com.irunninglog.api.data.impl.GoalEntity;
import com.irunninglog.api.data.impl.IGoalEntityRepository;
import com.irunninglog.api.date.IDateService;
import com.irunninglog.api.math.impl.MathService;
import com.irunninglog.api.profile.impl.ProfileEntity;
import com.irunninglog.api.workout.impl.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DashboardGoalsService {

    private static final String FORMAT_GOAL_DATES_1 = "{0} through {1}";
    private static final String FORMAT_GOAL_DATES_2 = "From {0}";
    private static final String FORMAT_GOAL_DATES_3 = "Through {0}";
    private static final String FORMAT_GOAL_DATES_4 = "All dates";

    private final IGoalEntityRepository goalEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final IDateService dateService;
    private final MathService mathService;

    @Autowired
    public DashboardGoalsService(IGoalEntityRepository goalEntityRepository,
                                 IDateService dateService,
                                 IWorkoutEntityRepository workoutEntityRepository,
                                 MathService mathService) {
        super();

        this.goalEntityRepository = goalEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
        this.mathService = mathService;
    }

    Collection<ProgressInfo> goals(ProfileEntity profile) {
        List<ProgressInfo> progressList = new ArrayList<>();

        for (GoalEntity goalEntity : goalEntityRepository.dashboardGoals(profile.getId())) {
            BigDecimal bigDecimal = getSum(goalEntity, profile.getId());

            int value = Math.min(bigDecimal.intValue(), (int) goalEntity.getGoal());
            int max = (int) goalEntity.getGoal();

            ProgressInfo progressInfo = new ProgressInfo()
                    .setTitle(goalEntity.getName())
                    .setSubTitle(goalEntity.getDescription())
                    .setTextOne(formatDates(goalEntity))
                    .setTextTwo(mathService.formatProgressText(bigDecimal.min(new BigDecimal(goalEntity.getGoal())), new BigDecimal(goalEntity.getGoal()), profile.getPreferredUnits()))
                    .setMax(max)
                    .setValue(value)
                    .setPercentage(mathService.getPercentage(value, max))
                    .setProgress(mathService.progress(bigDecimal, new BigDecimal(goalEntity.getGoal())));

            progressList.add(progressInfo);
        }

        return progressList;
    }

    private String formatDates(GoalEntity goalEntity) {
        String dateString;
        if (goalEntity.getStartDate() != null && goalEntity.getEndDate() != null) {
            dateString = MessageFormat.format(FORMAT_GOAL_DATES_1,
                    dateService.formatMedium(goalEntity.getStartDate()),
                    dateService.formatMedium(goalEntity.getEndDate()));
        } else if(goalEntity.getStartDate() != null) {
            dateString = MessageFormat.format(FORMAT_GOAL_DATES_2,
                    dateService.formatMedium(goalEntity.getStartDate()));
        } else if (goalEntity.getEndDate() != null) {
            dateString = MessageFormat.format(FORMAT_GOAL_DATES_3,
                    dateService.formatMedium(goalEntity.getEndDate()));
        } else {
            dateString = FORMAT_GOAL_DATES_4;
        }

        return dateString;
    }

    private BigDecimal getSum(GoalEntity goalEntity, long id) {
        BigDecimal bigDecimal;
        if (goalEntity.getStartDate() != null && goalEntity.getEndDate() != null) {
            bigDecimal = workoutEntityRepository.sumBetween(goalEntity.getStartDate(), goalEntity.getEndDate(), id);
        } else if (goalEntity.getStartDate() != null) {
            bigDecimal = workoutEntityRepository.sumFrom(goalEntity.getStartDate(), id);
        } else if (goalEntity.getEndDate() != null) {
            bigDecimal = workoutEntityRepository.sumTo(goalEntity.getEndDate(), id);
        } else {
            bigDecimal = workoutEntityRepository.sumAll(id);
        }

        return bigDecimal == null ? new BigDecimal(0) : bigDecimal;
    }

}
