package com.irunninglog.vertx.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Authenticator implements AuthProvider {

    private static final Logger LOG = LoggerFactory.getLogger(Authenticator.class);

    private final IAuthenticationService authenticationService;

    public Authenticator(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void authenticate(JsonObject jsonObject, Handler<AsyncResult<User>> handler) {
        try {
            authenticationService.authenticate("allan@irunninglog.com", "password");
        } catch (AuthnException ex) {
            LOG.error("Unable to perform authentication", ex);
        }
    }

}
