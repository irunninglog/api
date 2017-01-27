package com.irunninglog.spring.workout.impl;

import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.workout.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiService
public class WorkoutService implements IWorkoutService {

    private final IWorkoutEntityRepository workoutEntityRepository;
    private final DateService dateService;

    @Autowired
    public WorkoutService(IWorkoutEntityRepository workoutEntityRepository,
                          DateService dateService) {

        this.workoutEntityRepository = workoutEntityRepository;
        this.dateService = dateService;
    }

    @Override
    public GetWorkoutsResponse get(GetWorkoutsRequest request) {
        // TODO - get just for month
        List<WorkoutEntity> workoutEntities = workoutEntityRepository.findByProfileId(request.getId());

        Workouts workouts = new Workouts();
        for (WorkoutEntity workoutEntity : workoutEntities) {
            workouts.getWorkouts().add(new Workout());
        }

        return new GetWorkoutsResponse().setBody(workouts).setStatus(ResponseStatus.Ok);
    }

}
