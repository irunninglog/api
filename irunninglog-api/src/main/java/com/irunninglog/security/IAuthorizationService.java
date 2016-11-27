package com.irunninglog.security;

public interface IAuthorizationService {

    User authorize(User user, String resource) throws AuthzException;

}
