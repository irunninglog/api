package com.irunninglog.spring.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.service.ApiService;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

@ApiService
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private final IFactory factory;

    @Autowired
    public AuthenticationService(IFactory factory) throws UnsupportedEncodingException {

        this.factory = factory;
    }

    @Override
    public IUser authenticate(String token) throws AuthnException {
        if (token == null) {
            throw new AuthnException("No token provided");
        } else if (!token.startsWith("Bearer ")) {
            throw new AuthnException("Invalid token format");
        } else {
            try {
                return verify(token);
            } catch (Exception ex) {
                LOG.error("Token verification failed", ex);
                throw new AuthnException("Token verification failed");
            }
        }
    }

    private IUser verify(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        API api = new API(apiToken);

        StravaAthlete athlete = api.getAuthenticatedAthlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setAuthorities(Collections.singletonList("MYPROFILE"));
    }

}
