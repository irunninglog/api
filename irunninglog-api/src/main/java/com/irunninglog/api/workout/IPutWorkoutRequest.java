package com.irunninglog.api.workout;

import com.irunninglog.api.IProfileIdRequest;

public interface IPutWorkoutRequest extends IProfileIdRequest<IPutWorkoutRequest> {

    IPutWorkoutRequest setWorkout(IWorkoutEntry workout);

    IWorkoutEntry getWorkout();

}
