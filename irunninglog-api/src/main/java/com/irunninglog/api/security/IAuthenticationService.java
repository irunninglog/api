package com.irunninglog.api.security;

public interface IAuthenticationService {

    IUser authenticateToken(String token) throws AuthnException;

    IUser authenticateCode(String code) throws AuthnException;

}
