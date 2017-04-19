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

import java.text.MessageFormat;

public final class SecurityHandler implements Handler<RoutingContext> {

    private static final String PATTERN = "/profiles/{0}";
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
        if (endpoint.getControl() == AccessControl.ANONYMOUS) {
            if (LOG.isInfoEnabled()) {
                LOG.info("handle:anonymous:{}:{}", routingContext.normalisedPath(), endpoint);
            }

            routingContext.next();
        } else if (endpoint.getControl() == AccessControl.DENY) {
            fail(routingContext, ResponseStatus.UNAUTHORIZED);
        } else {
            routingContext.vertx().<IUser>executeBlocking(future -> authenticate(routingContext, future),
                    asyncResult -> authenticated(endpoint, routingContext, asyncResult));
        }
    }

    private void authenticated(Endpoint endpoint, RoutingContext routingContext, AsyncResult<IUser> asyncResult) {
        if (asyncResult.succeeded()) {
            if (endpoint == null) {
                // Static content
                routingContext.next();
            } else if (authorized(endpoint, routingContext, asyncResult.result())) {
                IUser user = asyncResult.result();
                routingContext.put("user", user);
                routingContext.next();
            } else {
                fail(routingContext, ResponseStatus.UNAUTHORIZED);
            }
        } else {
            //noinspection ThrowableResultOfMethodCallIgnored
            fail(routingContext, ResponseStatus.UNAUTHENTICATED);
        }
    }

    private boolean authorized(Endpoint endpoint, RoutingContext routingContext, IUser user) {
        if (endpoint.getControl() == AccessControl.ALLOW || hasRole(user, "ADMIN")) {
            return Boolean.TRUE;
        } else if (hasRole(user, "MYPROFILE")) {
            String path = MessageFormat.format(PATTERN, String.valueOf(user.getId()));
            return routingContext.normalisedPath().equals(path) || routingContext.normalisedPath().startsWith(path + "/");
        }

        return Boolean.FALSE;
    }

    private boolean hasRole(IUser user, String role) {
        for (String string : user.getAuthorities()) {
            if (string.equals(role)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    private void authenticate(RoutingContext routingContext, Future<IUser> future) {
        try {
            future.complete(authenticationService.authenticate(routingContext.request().getHeader("Authorization")));
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
