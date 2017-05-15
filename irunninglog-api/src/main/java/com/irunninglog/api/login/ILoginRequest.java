package com.irunninglog.api.login;

import com.irunninglog.api.IRequest;

public interface ILoginRequest<T extends ILoginRequest> extends IRequest<T> {

    String getCode();

    T setCode(String code);

}
