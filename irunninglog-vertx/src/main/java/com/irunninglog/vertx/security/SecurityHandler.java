package com.irunninglog.vertx.security;

import com.irunninglog.api.AccessControl;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecurityHandler implements Handler<RoutingContext> {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityHandler.class);

    private final IAuthenticationService authenticationService;

    public SecurityHandler(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        handleEndpoint(routingContext);
    }

    private void handleEndpoint(RoutingContext routingContext) {
        Endpoint endpoint = endpoint(routingContext);

        if (LOG.isInfoEnabled()) {
            LOG.info("handle:endpoint:{}:{}", routingContext.normalisedPath(), endpoint);
        }

        if (endpoint == null) {
            fail(routingContext, ResponseStatus.NOT_FOUND);
        } else {
            handle(endpoint, routingContext);
        }
    }

    private void handle(Endpoint endpoint, RoutingContext routingContext) {
        if (endpoint.getControl() == AccessControl.ALL) {
            if (LOG.isInfoEnabled()) {
                LOG.info("handle:anonymous:{}:{}", routingContext.normalisedPath(), endpoint);
            }

            routingContext.next();
        } else {
            routingContext.vertx().<IUser>executeBlocking(future -> authenticate(routingContext, future),
                    asyncResult -> authenticated(routingContext, asyncResult));
        }
    }

    private void authenticated(RoutingContext routingContext, AsyncResult<IUser> asyncResult) {
        if (asyncResult.succeeded()) {
            IUser user = asyncResult.result();
            if (user == null) {
                LOG.error("Treating a null authentication response as a failure");
                fail(routingContext, ResponseStatus.UNAUTHENTICATED);
            } else {
                routingContext.put("user", user);
                routingContext.next();
            }
        } else {
            //noinspection ThrowableResultOfMethodCallIgnored
            fail(routingContext, ResponseStatus.UNAUTHENTICATED);
        }
    }

    private void authenticate(RoutingContext routingContext, Future<IUser> future) {
        try {
            future.complete(authenticationService.authenticateToken(routingContext.request().getHeader("Authorization")));
        } catch (AuthnException ex) {
            LOG.error("authenticate:exception", ex);
            future.fail(ex);
        }
    }

    private Endpoint endpoint(RoutingContext routingContext) {
        Endpoint endpoint = null;

        for (Endpoint value : Endpoint.values()) {
            if (routingContext.currentRoute().getPath().equals(value.getPath())) {
                endpoint = value;
                break;
            }
        }

        return endpoint;
    }

    private void fail(RoutingContext routingContext, ResponseStatus status) {
        if (LOG.isErrorEnabled()) {
            LOG.error("fail:{}:{}", routingContext.normalisedPath(), status);
        }

        routingContext.request().response().setChunked(true)
                .setStatusCode(status.getCode())
                .write(status.getMessage())
                .end();
    }

}
