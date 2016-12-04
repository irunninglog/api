package com.irunninglog.spring.workout.impl;

import com.irunninglog.spring.date.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@SuppressWarnings({"unused", "WeakerAccess"})
public final class WorkoutService {

    private final IWorkoutEntityRepository workoutEntityRepository;
    private final DateService dateService;

    @Autowired
    public WorkoutService(IWorkoutEntityRepository workoutEntityRepository, DateService dateService) {
        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
    }

    public List<WorkoutEntity> findWorkoutsThisWeek(long id, DayOfWeek dayOfTheWeek, int offset) {
        LocalDate endDate = dateService.getWeekEndDate(dayOfTheWeek, offset);
        LocalDate startDate = dateService.getWeekStartDate(dayOfTheWeek, offset);

        return workoutEntityRepository.findByDateRange(id, startDate, endDate);
    }

    public List<WorkoutEntity> findWorkoutsThisMonth(long id, int offset) {
        LocalDate startDate = dateService.getMonthStartDate(offset);
        LocalDate endDate = dateService.getMonthEndDate(offset);

        return workoutEntityRepository.findByDateRange(id, startDate, endDate);
    }

    public List<WorkoutEntity> findWorkoutsThisYear(long id, int offset) {
        LocalDate startDate = dateService.getYearStartDate(offset);
        LocalDate endDate = dateService.getYearEndDate(offset);

        return workoutEntityRepository.findByDateRange(id, startDate, endDate);
    }

    public List<WorkoutEntity> findWorkoutsLastYear(long id, int offset) {
        LocalDate startDate = dateService.getYearStartDate(offset).minusYears(1);
        LocalDate endDate = dateService.getYearEndDate(offset).minusYears(1);

        return workoutEntityRepository.findByDateRange(id, startDate, endDate);
    }

    public LocalDate findWorkoutMonthBefore(long id, LocalDate date) {
        LocalDate localDate = workoutEntityRepository.getFirstWorkoutDateBefore(id, date.withDayOfMonth(1));

        return localDate == null ? null : localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate findWorkoutMonthAfter(long id, LocalDate date) {
        LocalDate localDate = workoutEntityRepository.getFirstWorkoutDateAfter(id, date.with(TemporalAdjusters.lastDayOfMonth()));

        return localDate == null ? null : localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public List<WorkoutEntity> findWorkoutsForMonth(long id, LocalDate localDate) {
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());

        return workoutEntityRepository.findByDateRange(id, startDate, endDate);
    }

}
