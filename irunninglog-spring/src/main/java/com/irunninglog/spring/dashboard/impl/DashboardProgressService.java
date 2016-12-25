package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.Unit;
import com.irunninglog.dashboard.ProgressInfo;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.service.InternalService;
import com.irunninglog.spring.workout.impl.WorkoutEntity;
import com.irunninglog.spring.workout.impl.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@InternalService
final class DashboardProgressService {

    private final WorkoutService workoutsService;
    private final MathService mathService;
    private final DateService dateService;

    @Autowired
    public DashboardProgressService(WorkoutService workoutsService,
                                    MathService mathService,
                                    DateService dateService) {
        super();

        this.workoutsService = workoutsService;
        this.mathService = mathService;
        this.dateService = dateService;
    }

    Collection<ProgressInfo> progress(ProfileEntity profile, int offset) {
        List<ProgressInfo> list = new ArrayList<>();

        list.add(thisWeek(profile, offset));
        list.add(thisMonth(profile, offset));
        list.add(currentYear(profile, offset));

        ProgressInfo previousYear = previousYear(profile, offset);
        if (previousYear != null) {
            list.add(previousYear(profile, offset));
        }

        return list;
    }

    private ProgressInfo thisWeek(ProfileEntity profile, int offset) {
        List<WorkoutEntity> weekly = workoutsService.findWorkoutsThisWeek(profile.getId(), profile.getWeekStart(), offset);
        BigDecimal thisWeek = new BigDecimal(profile.getWeeklyTarget());

        return getProgressInfo(weekly,
                thisWeek,
                profile.getPreferredUnits(),
                "This Week",
                dateService.getThisWeekEndFull(profile.getWeekStart(), offset));
    }

    private ProgressInfo thisMonth(ProfileEntity profile, int offset) {
        List<WorkoutEntity> monthly = workoutsService.findWorkoutsThisMonth(profile.getId(), offset);
        BigDecimal thisMonth = new BigDecimal(profile.getMonthlyTarget());

        return getProgressInfo(monthly,
                thisMonth,
                profile.getPreferredUnits(),
                "This Month",
                dateService.getThisMonthEndFull(offset));
    }

    private ProgressInfo currentYear(ProfileEntity profile, int offset) {
        List<WorkoutEntity> yearly = workoutsService.findWorkoutsThisYear(profile.getId(), offset);
        BigDecimal thisYear = new BigDecimal(profile.getYearlyTarget());

        return getProgressInfo(yearly,
                thisYear,
                profile.getPreferredUnits(),
                "This Year",
                dateService.getThisYearEndFull(offset));
    }

    private ProgressInfo previousYear(ProfileEntity profile, int offset) {
        List<WorkoutEntity> lastYears = workoutsService.findWorkoutsLastYear(profile.getId(), offset);
        if (!lastYears.isEmpty()) {
            BigDecimal lastYear = new BigDecimal(profile.getYearlyTarget());

            return getProgressInfo(lastYears,
                    lastYear,
                    profile.getPreferredUnits(),
                    "Last Year",
                    dateService.getLastYearEndFull(offset));
        } else {
             return null;
        }
    }

    private ProgressInfo getProgressInfo(List<WorkoutEntity> entities, BigDecimal target, Unit units, String title, String subTitle) {
        BigDecimal mileage = new BigDecimal("0.0");

        for (WorkoutEntity workout : entities) {
            mileage = mileage.add(new BigDecimal(workout.getDistance()));
        }

        int max = mathService.intValue(target);
        int value = Math.min(mathService.intValue(mileage), max);

        ProgressInfo progressInfo = new ProgressInfo();
        progressInfo.setTitle(title);
        progressInfo.setSubTitle(subTitle);
        progressInfo.setTextOne(entities.size() + " workout(s)");
        progressInfo.setTextTwo(formatTextTwo(mileage, target, units));
        progressInfo.setMax(max);
        progressInfo.setValue(value);
        progressInfo.setPercentage(mathService.getPercentage(value, max));
        progressInfo.setProgress(mathService.progress(mileage, target));

        return progressInfo;
    }

    private String formatTextTwo(BigDecimal mileage, BigDecimal target, Unit units) {
        return target.doubleValue() < 1E-9 ? mathService.format(mileage, units) : formatProgressLabel(mileage, target, units);
    }

    private String formatProgressLabel(BigDecimal mileage, BigDecimal target, Unit units) {
        if (mileage.compareTo(target) > 0) {
            return mathService.format(mileage, units) + " of " + mathService.format(target, units) + " (100%)";
        } else {
            BigDecimal percent = mathService.divide(mileage.multiply(new BigDecimal(100)), target);
            return mathService.format(mileage, units) + " of " + mathService.format(target, units) + " (" + mathService.intValue(percent) + "%)";
        }
    }

}
