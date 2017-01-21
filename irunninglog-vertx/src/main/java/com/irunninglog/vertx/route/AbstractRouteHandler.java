package com.irunninglog.vertx.route;

import com.irunninglog.security.AuthnRequest;
import com.irunninglog.security.AuthnResponse;
import com.irunninglog.service.AbstractRequest;
import com.irunninglog.service.AbstractResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRouteHandler<Q extends AbstractRequest, S extends AbstractResponse> implements Handler<RoutingContext> {

    private final Vertx vertx;
    private final Class<S> responseClass;
    private final Endpoint endpoint;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRouteHandler(Vertx vertx, Class<S> responseClass) {
        this.vertx = vertx;
        this.responseClass = responseClass;

        RouteHandler routeHandler = this.getClass().getAnnotation(RouteHandler.class);
        this.endpoint = routeHandler.endpoint();
    }

    @Override
    public final void handle(RoutingContext routingContext) {
        long start = System.currentTimeMillis();

        try {
            logger.info("handle:start:{}", routingContext.normalisedPath());

            AuthnRequest authnRequest = new AuthnRequest()
                    .setToken(routingContext.request().getHeader("Authorization"))
                    .setEndpoint(endpoint());

            logger.info("handle:authn:{}", authnRequest);

            vertx.eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(authnRequest), result -> {
                        if (result.succeeded()) {
                            String resultString = result.result().body();

                            logger.info("handle:{}:{}", AuthnVerticle.ADDRESS, resultString);

                            AuthnResponse authnResponse = Json.decodeValue(resultString, AuthnResponse.class);

                            logger.info("handle:{}:{}", AuthnVerticle.ADDRESS, authnResponse.getStatus());

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

        logger.info("handleAuthenticated:{}:{}", endpoint.getAddress(), requestString);

        vertx.eventBus().<String>send(endpoint.getAddress(), requestString, result -> {
            if (result.succeeded()) {
                String resultString = result.result().body();

                logger.info("handleAuthenticated:{}:{}", endpoint.getAddress(), resultString);

                S response = Json.decodeValue(resultString, responseClass);

                logger.info("handle:{}:{}", endpoint.getAddress(), response);

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

    public final Endpoint endpoint() {
        return endpoint;
    }

}
