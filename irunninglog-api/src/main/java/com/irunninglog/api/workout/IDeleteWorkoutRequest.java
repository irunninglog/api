package com.irunninglog.api.workout;

import com.irunninglog.api.IProfileIdRequest;

public interface IDeleteWorkoutRequest extends IProfileIdRequest<IDeleteWorkoutRequest> {

    IDeleteWorkoutRequest setWorkoutId(long id);

    long getWorkoutId();

}
