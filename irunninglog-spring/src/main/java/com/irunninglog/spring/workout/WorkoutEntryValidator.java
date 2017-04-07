package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class WorkoutEntryValidator implements Validator {

    private final MathService mathService;

    @Autowired
    public WorkoutEntryValidator(MathService mathService) {
        super();

        this.mathService = mathService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IWorkoutEntry.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Check required fields
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "workout.date.empty", "Workout date is required");

        IWorkoutEntry workoutEntry = (IWorkoutEntry) target;

        // If date is present, make sure it's valid
        if (workoutEntry.getDate() != null && !workoutEntry.getDate().trim().isEmpty() && !Pattern.compile(DateService.DATE_INCOMING).matcher(workoutEntry.getDate()).matches()) {
            errors.rejectValue("date", "workout.date.invalid", "Workout date is not in a valid format");
        }

        // If distance is there, make sure it's valid
        if (workoutEntry.getDistance() != null && workoutEntry.getDistance().trim().isEmpty()) {
            errors.rejectValue("distance", "workout.distance.empty", "Workout distance cannot be empty");
        } else if (workoutEntry.getDistance() != null) {
            try {
                double distance = mathService.parse(workoutEntry.getDistance());
                if (distance < 1E-9) {
                    errors.rejectValue("distance", "workout.distance.invalid", "Workout distance is not valid");
                }
            } catch (Exception ex) {
                errors.rejectValue("distance", "workout.distance.invalid", "Workout distance is not valid");
            }
        }

        // Ensure that duration is not negative
        if (workoutEntry.getDuration() < 0) {
            errors.rejectValue("duration", "workout.duration.invalid", "Workout duration is not valid");
        }
    }

}
