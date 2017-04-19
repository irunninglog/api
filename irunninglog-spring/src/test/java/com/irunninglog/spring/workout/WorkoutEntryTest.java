package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkoutEntryTest extends AbstractTest {

    private WorkoutEntryValidator workoutEntryValidator;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        workoutEntryValidator = applicationContext.getBean(WorkoutEntryValidator.class);
    }

    @Test
    public void nullDate() {
        IWorkoutEntry workoutEntry = new WorkoutEntry();

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("date").size());
    }

    @Test
    public void emptyDate() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDate("  ");

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("date").size());
    }

    @Test
    public void invalidDate() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDate("abcd");

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("date").size());
    }

    @Test
    public void blankDistance() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDate("01-01-2017").setDistance("  ");

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("distance").size());
    }

    @Test
    public void negativeDistance() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDate("01-01-2017").setDistance("-1");

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("distance").size());
    }

    @Test
    public void invalidDistance() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDate("01-01-2017").setDistance("aaa");

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("distance").size());
    }

    @Test
    public void negativeDuration() {
        IWorkoutEntry workoutEntry = new WorkoutEntry().setDuration(-1);

        DataBinder binder = new DataBinder(workoutEntry);
        Errors errors = binder.getBindingResult();
        workoutEntryValidator.validate(workoutEntry, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors("duration").size());
    }

    @Test
    public void supports() {
        assertTrue(workoutEntryValidator.supports(WorkoutEntry.class));
    }

}
