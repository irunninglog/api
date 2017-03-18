package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IProgressInfo;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.spring.data.GoalEntity;
import com.irunninglog.spring.data.IGoalEntityRepository;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.InternalService;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@InternalService
final class DashboardGoalsService {

    private static final String FORMAT_GOAL_DATES_1 = "{0} through {1}";
    private static final String FORMAT_GOAL_DATES_2 = "From {0}";
    private static final String FORMAT_GOAL_DATES_3 = "Through {0}";
    private static final String FORMAT_GOAL_DATES_4 = "All dates";

    private final IGoalEntityRepository goalEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final DateService dateService;
    private final MathService mathService;
    private final IFactory factory;

    @Autowired
    public DashboardGoalsService(IGoalEntityRepository goalEntityRepository,
                                 DateService dateService,
                                 IWorkoutEntityRepository workoutEntityRepository,
                                 MathService mathService,
                                 IFactory factory) {
        super();

        this.goalEntityRepository = goalEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
        this.mathService = mathService;
        this.factory = factory;
    }

    Collection<IProgressInfo> goals(ProfileEntity profile) {
        List<IProgressInfo> progressList = new ArrayList<>();

        for (GoalEntity goalEntity : goalEntityRepository.dashboardGoals(profile.getId())) {
            BigDecimal bigDecimal = getSum(goalEntity, profile.getId());

            int value = Math.min(bigDecimal.intValue(), (int) goalEntity.getGoal());
            int max = (int) goalEntity.getGoal();

            IProgressInfo progressInfo = factory.get(IProgressInfo.class)
                    .setTitle(goalEntity.getName())
                    .setSubTitle(goalEntity.getDescription())
                    .setTextOne(formatDates(goalEntity))
                    .setTextTwo(mathService.formatProgressText(bigDecimal.min(BigDecimal.valueOf(goalEntity.getGoal())), BigDecimal.valueOf(goalEntity.getGoal()), profile.getPreferredUnits()))
                    .setMax(max)
                    .setValue(value)
                    .setPercentage(mathService.getPercentage(value, max))
                    .setProgress(mathService.progress(bigDecimal, BigDecimal.valueOf(goalEntity.getGoal())));

            progressList.add(progressInfo);
        }

        progressList.sort(new ProgressInfoComparator());

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

        return bigDecimal == null ? BigDecimal.valueOf(0) : bigDecimal;
    }

}
