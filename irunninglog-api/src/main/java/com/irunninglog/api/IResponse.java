package com.irunninglog.api;

public interface IResponse {

    IResponse setStatus(ResponseStatus status);

    ResponseStatus getStatus();

    IResponse setBody(Object body);

    Object getBody();

}
