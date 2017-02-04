package com.irunninglog.api;

import com.irunninglog.service.ResponseStatus;

public interface IResponse<B, T extends IResponse> {

    T setStatus(ResponseStatus status);

    ResponseStatus getStatus();

    T setBody(B body);

    B getBody();

}
