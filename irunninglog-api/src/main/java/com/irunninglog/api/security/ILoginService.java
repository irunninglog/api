package com.irunninglog.api.security;

@FunctionalInterface
public interface ILoginService {

    ILogin login(IUser user);

}
