package com.irunninglog.api.security;

import com.irunninglog.api.IRequest;

public interface ILoginRequest<T extends ILoginRequest, U extends IUser> extends IRequest<T> {

    ILoginRequest setUser(U user);

    U getUser();

}
