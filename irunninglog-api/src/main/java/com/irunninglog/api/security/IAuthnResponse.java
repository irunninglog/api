package com.irunninglog.api.security;

import com.irunninglog.api.IResponse;

public interface IAuthnResponse<R extends IUser, T extends IAuthnResponse> extends IResponse<R, T> {

    String getToken();

    T setToken(String token);

}
