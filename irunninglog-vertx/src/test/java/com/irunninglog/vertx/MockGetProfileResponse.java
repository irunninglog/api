package com.irunninglog.vertx;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfile;

public class MockGetProfileResponse implements IGetProfileResponse {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockProfile.class)
    private IProfile body;

    @Override
    public IGetProfileResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public IGetProfileResponse setBody(IProfile body) {
        this.body = body;
        return this;
    }

    @Override
    public IProfile getBody() {
        return body;
    }

}
