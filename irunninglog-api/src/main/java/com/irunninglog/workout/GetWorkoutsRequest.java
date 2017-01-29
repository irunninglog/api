package com.irunninglog.workout;

import com.irunninglog.service.AbstractProfileIdRequest;

public final class GetWorkoutsRequest extends AbstractProfileIdRequest<GetWorkoutsRequest> {

    private String date;

    public String getDate() {
        return date;
    }

    public GetWorkoutsRequest setDate(String date) {
        this.date = date;
        return this;
    }

}
