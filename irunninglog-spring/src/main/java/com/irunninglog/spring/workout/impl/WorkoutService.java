package com.irunninglog.spring.workout.impl;

import com.irunninglog.Progress;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.data.impl.AbstractDataEntity;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.workout.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApiService
public class WorkoutService implements IWorkoutService {

    private static final String TITLE_FULL = "I ran #distance in #duration (#pace pace)";
    private static final String TITLE_DISTANCE = "I ran for #distance";
    private static final String TITLE_DURATION = "I ran for #duration";
    private static final String TITLE_MIN = "I went for a run";

    private final IProfileEntityRepository profileEntityRepository;
    private final FindWorkoutsService findWorkoutsService;
    private final DateService dateService;
    private final MathService mathService;

    @Autowired
    public WorkoutService(IProfileEntityRepository profileEntityRepository,
                          FindWorkoutsService findWorkoutsService,
                          DateService dateService,
                          MathService mathService) {

        this.profileEntityRepository = profileEntityRepository;
        this.findWorkoutsService = findWorkoutsService;
        this.dateService = dateService;
        this.mathService = mathService;
    }

    @Override
    public GetWorkoutsResponse get(GetWorkoutsRequest request) {
        LocalDate localDate = request.getDate() == null
                ? dateService.getMonthStartDate(request.getOffset())
                : dateService.parse(request.getDate());

        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        List<WorkoutEntity> workoutEntities =
                findWorkoutsService.findWorkoutsForMonth(profileEntity.getId(), localDate);

        Workouts workouts = new Workouts()
                .setSummary(summary(localDate, workoutEntities, profileEntity))
                .setPrevious(month(findWorkoutsService.findWorkoutMonthBefore(profileEntity.getId(), localDate)))
                .setNext(month(findWorkoutsService.findWorkoutMonthAfter(profileEntity.getId(), localDate)));

        for (WorkoutEntity workoutEntity : workoutEntities) {
            workouts.getWorkouts().add(workout(workoutEntity, profileEntity));
        }

        workouts.getWorkouts().sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        return new GetWorkoutsResponse()
                .setBody(workouts)
                .setStatus(ResponseStatus.Ok);
    }

    private WorkoutsSummary summary(LocalDate localDate, List<WorkoutEntity> workoutEntities, ProfileEntity profileEntity) {
        BigDecimal mileage = new BigDecimal(workoutEntities.stream().mapToDouble(WorkoutEntity::getDistance).sum());

        String mileageString = mathService.format(mileage, profileEntity.getPreferredUnits());

        Progress progress = mathService.progress(mileage, new BigDecimal(profileEntity.getMonthlyTarget()));

        int percentage = mathService.getPercentage(mathService.intValue(mileage),
                mathService.intValue(new BigDecimal(profileEntity.getMonthlyTarget())));

        percentage = Math.min(100, percentage);

        return new WorkoutsSummary()
                .setTitle(dateService.formatMonthMedium(localDate))
                .setCount(workoutEntities.size())
                .setMileage(mileageString)
                .setProgress(progress)
                .setPercentage(percentage);
    }

    private WorkoutsMonth month(LocalDate month) {
        return month == null ? null : new WorkoutsMonth()
                .setTitle(dateService.formatMonthMedium(month))
                .setDate(dateService.format(month));
    }

    private Workout workout(WorkoutEntity workoutEntity, ProfileEntity profileEntity) {
        String distance = workoutEntity.getDistance() > 1E-9 ? mathService.format(workoutEntity.getDistance(), profileEntity.getPreferredUnits()) : "--";
        String duration = workoutEntity.getDuration() > 0 ? dateService.formatTime((workoutEntity.getDuration())) : "--";
        String pace = workoutEntity.getDuration() > 0 && workoutEntity.getDistance() > 0 ? dateService.formatTime((long) (workoutEntity.getDuration() / workoutEntity.getDistance())) : "--";

        return new Workout()
                .setId(workoutEntity.getId())
                .setPrivacy(workoutEntity.getPrivacy())
                .setDate(dateService.format(workoutEntity.getDate()))
                .setDistance(distance)
                .setDuration(duration)
                .setPace(pace)
                .setTitle(title(distance, duration))
                .setRoute(data(workoutEntity.getRoute()))
                .setRun(data(workoutEntity.getRun()))
                .setShoe(data(workoutEntity.getShoe()));
    }

    private String title(String distance, String duration) {
        String title;

        if (!"--".equals(distance) && !"--".equals(duration)) {
            title = TITLE_FULL;
        } else if (!"--".equals(distance)) {
            title = TITLE_DISTANCE;
        } else if (!"--".equals(duration)) {
            title = TITLE_DURATION;
        } else {
            title = TITLE_MIN;
        }

        return title;
    }

    private WorkoutData data(AbstractDataEntity data) {
        return data == null ? null : new WorkoutData()
                .setId(data.getId())
                .setName(data.getName())
                .setDescription(data.getDescription());
    }

}
