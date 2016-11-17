package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.security.AuthnRequest;
import com.irunninglog.api.security.AuthnResponse;
import com.irunninglog.api.service.AbstractRequest;
import com.irunninglog.api.service.AbstractResponse;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

abstract class AbstactRouteHandler<Q extends AbstractRequest, S extends AbstractResponse> implements IRouteHandler {

    final Logger logger = LoggerFactory.getLogger(getClass());
    private final Vertx vertx;
    private final Class<S> responseClass;

    AbstactRouteHandler(Vertx vertx, Class<S> responseClass) {
        this.vertx = vertx;
        this.responseClass = responseClass;
    }

    @Override
    public final void handle(RoutingContext routingContext) {
        long start = System.currentTimeMillis();

        try {
            logger.info("handle:start:{}", routingContext.normalisedPath());

            AuthnRequest authnRequest = authnRequest(routingContext);
            if (authnRequest == null) {
                logger.error("Unable to get authentication information from request");

                fail(routingContext, ResponseStatus.Unauthnticated);

                return;
            }

            logger.info("handle:authn:{}", authnRequest.getUsername());

            vertx.eventBus().<String>send(Address.Authenticate.getAddress(), Json.encode(authnRequest), result -> {
                        if (result.succeeded()) {
                            String resultString = result.result().body();

                            logger.info("handle:{}:{}", Address.Authenticate.getAddress(), resultString);

                            AuthnResponse authnResponse = Json.decodeValue(resultString, AuthnResponse.class);

                            logger.info("handle:{}:{}", Address.Authenticate.getAddress(), authnResponse.getStatus());

                            if (authnResponse.getStatus() == ResponseStatus.Ok) {
                                handleAuthenticated(routingContext);
                            } else {
                                fail(routingContext, authnResponse.getStatus());
                            }
                        } else {
                            logger.error("handle:authn:failure", result.cause());
                            logger.error("handle:authn:failure{}", routingContext.normalisedPath());

                            fail(routingContext, ResponseStatus.Error);
                        }
                    });
        } finally {
            logger.info("handle:end:{}:{}ms", routingContext.normalisedPath(), System.currentTimeMillis() - start);
        }
    }

    private void handleAuthenticated(RoutingContext routingContext) {
        logger.info("handleAuthenticated:start:{}", routingContext.normalisedPath());

        Q request = request(routingContext);

        String offset = routingContext.request().getHeader("iRunningLog-Utc-Offset");
        request.setOffset(offset == null ? 0 : Integer.parseInt(offset));

        String requestString = Json.encode(request);

        logger.info("handleAuthenticated:{}:{}", address(), requestString);

        vertx.eventBus().<String>send(address().getAddress(), requestString, result -> {
            if (result.succeeded()) {
                String resultString = result.result().body();

                logger.info("handleAuthenticated:{}:{}", address(), resultString);

                S response = Json.decodeValue(resultString, responseClass);

                logger.info("handle:{}:{}", address(), response);

                if (response.getStatus() == ResponseStatus.Ok) {
                    succeed(routingContext, response);
                } else {
                    fail(routingContext, response.getStatus());
                }
            } else {
                logger.error("handleAuthenticated:failure", result.cause());
                logger.error("handleAuthenticated:failure{}", routingContext.normalisedPath());

                fail(routingContext, ResponseStatus.Error);
            }
        });
    }

    private AuthnRequest authnRequest(RoutingContext routingContext) {
        AuthnRequest authnRequest = null;

        try {
            String authHeader = routingContext.request().getHeader("Authorization");
            if (authHeader != null && authHeader.toLowerCase().startsWith("basic")) {
                String decoded = new String(Base64.getDecoder().decode(authHeader.split(" ")[1]));
                String [] tokens = decoded.split(":");
                authnRequest = new AuthnRequest()
                        .setUsername(tokens[0])
                        .setPassword(tokens[1])
                        .setPath(routingContext.normalisedPath());
            }
        } catch (Exception ex) {
            logger.error("Unable to decode authorization header", ex);
        }

        return authnRequest;
    }

    private void succeed(RoutingContext routingContext, AbstractResponse response) {
        routingContext.request().response()
                .setChunked(true)
                .putHeader("Content-Type", "application/json")
                .setStatusCode(response.getStatus().getCode())
                .write(Json.encode(response.getBody()))
                .end();
    }

    private void fail(RoutingContext routingContext, ResponseStatus error) {
        logger.error("fail:{}:{}", routingContext.normalisedPath(), error);

        routingContext.request().response().setChunked(true)
                .setStatusCode(error.getCode())
                .write(error.getMessage())
                .end();
    }

    protected abstract Q request(RoutingContext routingContext);

    protected abstract Address address();

}
