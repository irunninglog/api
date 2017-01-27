package com.irunninglog.workout;

import com.irunninglog.service.AbstractProfileIdRequest;

import java.time.LocalDate;

public final class GetWorkoutsRequest extends AbstractProfileIdRequest<GetWorkoutsRequest> {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public GetWorkoutsRequest setDate(LocalDate date) {
        this.date = date;
        return this;
    }

}
