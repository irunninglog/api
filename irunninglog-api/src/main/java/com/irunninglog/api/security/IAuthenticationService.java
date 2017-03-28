package com.irunninglog.api.security;

public interface IAuthenticationService {

    IUser authenticate(String token) throws AuthnException;

    String token(IUser user) throws AuthnException;

}
