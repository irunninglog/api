package com.irunninglog.api.security;

import com.irunninglog.api.Endpoint;

public interface IAuthenticationService {

    IUser authenticate(Endpoint endpoint, String path, String token) throws AuthnException, AuthzException;

    String token(IUser user) throws AuthnException;

}
