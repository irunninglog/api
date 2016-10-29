package com.irunninglog.api.security.impl;

import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.IAuthorizationService;
import com.irunninglog.api.security.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizationService implements IAuthorizationService {

    @Override
    public User authorize(User user, String resource) throws AuthzException {
        throw new AuthzException("Not authorized");
    }

}
