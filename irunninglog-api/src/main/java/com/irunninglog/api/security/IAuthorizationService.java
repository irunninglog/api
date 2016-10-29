package com.irunninglog.api.security;

public interface IAuthorizationService {

    User authorize(User user, String resource) throws AuthzException;

}
