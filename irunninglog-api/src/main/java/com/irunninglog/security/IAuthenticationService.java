package com.irunninglog.security;

public interface IAuthenticationService {

    User authenticate(String username, String password) throws AuthnException;

}
