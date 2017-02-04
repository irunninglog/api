package com.irunninglog.api.workout;

import com.irunninglog.api.IProfileIdRequest;

public interface IGetWorkoutsRequest extends IProfileIdRequest<IGetWorkoutsRequest> {

    String getDate();

    IGetWorkoutsRequest setDate(String date);

}
