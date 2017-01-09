package com.irunninglog.security;

public interface IAuthenticationService {

    User authenticate(AuthnRequest request) throws AuthnException, AuthzException;

}
