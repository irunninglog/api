package com.irunninglog.spring.report;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.api.report.IMultiSet;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.InternalService;
import com.irunninglog.spring.workout.WorkoutEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@InternalService
class MileageByMonthService {

    private static final List<String> MONTHS;

    static {
        MONTHS = new ArrayList<>();
        MONTHS.add("Jan");
        MONTHS.add("Feb");
        MONTHS.add("Mar");
        MONTHS.add("Apr");
        MONTHS.add("May");
        MONTHS.add("Jun");
        MONTHS.add("Jul");
        MONTHS.add("Aug");
        MONTHS.add("Sep");
        MONTHS.add("Oct");
        MONTHS.add("Nov");
        MONTHS.add("Dec");
    }

    private final DateService dateService;
    private final MathService mathService;
    private final IFactory factory;

    @Autowired
    MileageByMonthService(DateService dateService,
                          MathService mathService,
                          IFactory factory) {

        this.dateService = dateService;
        this.mathService = mathService;
        this.factory = factory;
    }

    IMultiSet multiSet(List<WorkoutEntity> workoutEntities, ProfileEntity profileEntity) {
        workoutEntities.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        IMultiSet multiSet = factory.get(IMultiSet.class);
        while (!workoutEntities.isEmpty()) {
            String year = dateService.formatYear(workoutEntities.get(0).getDate());
            LocalDate yearStart = dateService.getYearStartDate(workoutEntities.get(0).getDate());
            IDataSet yearDataSet = factory.get(IDataSet.class)
                    .setKey(year)
                    .setUnits(profileEntity.getPreferredUnits());
            multiSet.getData().add(yearDataSet);

            addMonthsToYear(workoutEntities, yearStart, yearDataSet, profileEntity);

            yearDataSet.getData().sort((o1, o2) -> {
                int index1 = MONTHS.indexOf(o1.getLabel());
                int index2 = MONTHS.indexOf(o2.getLabel());

                return index1 == index2 ? 0 : index1 < index2 ? -1 : 1;
            });
        }

        return multiSet;
    }

    private void addMonthsToYear(List<WorkoutEntity> workouts, LocalDate yearStart, IDataSet yearDataSet, ProfileEntity profileEntity) {
        while (!workouts.isEmpty()) {
            WorkoutEntity workoutEntity = workouts.get(0);
            if (!workoutEntity.getDate().isBefore(yearStart)) {
                double total = 0.0;

                LocalDate monthStart = dateService.getMonthStartDate(workoutEntity.getDate());
                for (Iterator<WorkoutEntity> iterator = workouts.iterator(); iterator.hasNext();) {
                    WorkoutEntity inner = iterator.next();
                    if (!inner.getDate().isBefore(monthStart)) {
                        total += inner.getDistance();
                        iterator.remove();
                    } else {
                        break;
                    }
                }

                IDataPoint monthDataPoint = factory.get(IDataPoint.class)
                        .setLabel(dateService.formatMonthShort(monthStart))
                        .setValue(mathService.formatShort(total, profileEntity.getPreferredUnits()))
                        .setProgress(mathService.progress(new BigDecimal(total), new BigDecimal(profileEntity.getMonthlyTarget())));

                yearDataSet.add(monthDataPoint);
            } else{
                break;
            }
        }
    }

}
