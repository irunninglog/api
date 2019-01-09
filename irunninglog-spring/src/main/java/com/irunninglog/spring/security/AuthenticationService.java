package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.strava.StravaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class AuthenticationService implements IAuthenticationService {

    private final StravaApiService stravaApiService;

    @Autowired
    public AuthenticationService(StravaApiService stravaApiService) {
        this.stravaApiService = stravaApiService;
    }

    @Override
    public IUser authenticateToken(String token) {
        return stravaApiService.userFromToken(token);
    }

    @Override
    public IUser authenticateCode(String code) throws AuthnException {
        return stravaApiService.userFromCode(code);
    }

}
