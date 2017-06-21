package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class AuthenticationService implements IAuthenticationService {

    private final IStravaService stravaService;

    @Autowired
    public AuthenticationService(IStravaService stravaService) {
        this.stravaService = stravaService;
    }

    @Override
    public IUser authenticateToken(String token) throws AuthnException {
        return stravaService.userFromToken(token);
    }

    @Override
    public IUser authenticateCode(String code) throws AuthnException {
        return stravaService.userFromCode(code);
    }

}
