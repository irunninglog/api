package com.irunninglog.api.security;

public interface IAuthenticationService {

    User authenticate(String username, String password) throws AuthnException;

}
