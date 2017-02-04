package com.irunninglog.api;

public interface IResponse<B, T extends IResponse> {

    T setStatus(ResponseStatus status);

    ResponseStatus getStatus();

    T setBody(B body);

    B getBody();

}
