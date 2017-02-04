package com.irunninglog.api.security;

import com.irunninglog.api.IRequest;

public interface ILoginRequest extends IRequest<ILoginRequest> {

    ILoginRequest setUser(IUser user);

    IUser getUser();

}
