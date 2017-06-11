package com.irunninglog.spring.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
final class AuthenticationService implements IAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private final IFactory factory;
    private final int id;
    private final String secret;

    @Autowired
    public AuthenticationService(IFactory factory, Environment environment) throws UnsupportedEncodingException {
        this.factory = factory;

        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public IUser authenticateToken(String token) throws AuthnException {
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

    @Override
    public IUser authenticateCode(String code) throws AuthnException {
        AuthorisationService service = new AuthorisationServiceImpl();
        Token token= service.tokenExchange(id, secret, code);
        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    private IUser verify(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        API api = new API(apiToken);

        StravaAthlete athlete = api.getAuthenticatedAthlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

}
