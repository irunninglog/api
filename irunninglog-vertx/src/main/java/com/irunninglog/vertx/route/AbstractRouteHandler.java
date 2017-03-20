package com.irunninglog.vertx.route;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRouteHandler<Q extends IRequest, S extends IResponse> implements Handler<RoutingContext> {

    private static final String LOG_STMT = "handle:{}:{}";

    private final Vertx vertx;
    private final IFactory factory;
    private final IMapper mapper;
    private final Class<Q> requestClass;
    private final Class<S> responseClass;
    private final Endpoint endpoint;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRouteHandler(Vertx vertx,
                                IFactory factory,
                                IMapper mapper,
                                Class<Q> requestClass,
                                Class<S> responseClass) {

        this.vertx = vertx;
        this.factory = factory;
        this.mapper = mapper;
        this.requestClass = requestClass;
        this.responseClass = responseClass;

        RouteHandler routeHandler = this.getClass().getAnnotation(RouteHandler.class);
        this.endpoint = routeHandler.endpoint();
    }

    @Override
    public final void handle(RoutingContext routingContext) {
        long start = System.currentTimeMillis();

        try {
            if (logger.isInfoEnabled()) {
                logger.info("handle:start:{}", routingContext.normalisedPath());
            }

            IAuthnRequest authnRequest = factory.get(IAuthnRequest.class)
                    .setToken(routingContext.request().getHeader("Authorization"))
                    .setPath(routingContext.normalisedPath())
                    .setEndpoint(endpoint());

            logger.info("handle:authn:{}", authnRequest);

            vertx.eventBus().<String>send(AuthnVerticle.ADDRESS, mapper.encode(authnRequest), result -> {
                        if (result.succeeded()) {
                            String resultString = result.result().body();

                            logger.info(LOG_STMT, AuthnVerticle.ADDRESS, resultString);

                            IAuthnResponse authnResponse = mapper.decode(resultString, IAuthnResponse.class);

                            logger.info(LOG_STMT, AuthnVerticle.ADDRESS, authnResponse.getStatus());

                            if (authnResponse.getStatus() == ResponseStatus.OK) {
                                routingContext.put("user", authnResponse.getBody());
                                handleAuthenticated(routingContext, authnResponse);
                            } else {
                                fail(routingContext, authnResponse.getStatus());
                            }
                        } else {
                            logger.error("handle:authn:failure", result.cause());
                            logger.error("handle:authn:failure{}", routingContext.normalisedPath());

                            fail(routingContext, ResponseStatus.ERROR);
                        }
                    });
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("handle:end:{}:{}ms", routingContext.normalisedPath(), System.currentTimeMillis() - start);
            }
        }
    }

    private void handleAuthenticated(RoutingContext routingContext, IAuthnResponse authnResponse) {
        if (logger.isInfoEnabled()) {
            logger.info("handleAuthenticated:start:{}", routingContext.normalisedPath());
        }

        try {
            Q request = factory.get(requestClass);

            request(request, routingContext);

            handle(routingContext, request, authnResponse.getToken());
        } catch (Exception ex) {
            logger.error("Caught and exception; treating as unauthenticated", ex);

            fail(routingContext, ResponseStatus.UNAUTHENTICATED);
        }
    }

    protected void request(Q request, RoutingContext routingContext) {
        // Empty for subclasses
    }

    private void handle(RoutingContext routingContext, Q request, String token) {
        String offsetString = routingContext.request().getHeader("iRunningLog-Utc-Offset");
        int offset = offsetString == null ? 0 : Integer.parseInt(offsetString);
        request.setOffset(offset);

        String requestString = mapper.encode(request);

        logger.info("handleAuthenticated:{}:{}", endpoint.getAddress(), requestString);

        vertx.eventBus().<String>send(endpoint.getAddress(), requestString, result -> handle(result, routingContext, token));
    }

    private void handle(AsyncResult<Message<String>> result, RoutingContext routingContext, String token) {
        try {
            if (result.succeeded()) {
                String resultString = result.result().body();

                logger.info("handleAuthenticated:{}:{}", endpoint.getAddress(), resultString);

                S response = mapper.decode(resultString, responseClass);

                logger.info(LOG_STMT, endpoint.getAddress(), response);

                if (response.getStatus() == ResponseStatus.OK) {
                    succeed(routingContext, response, token);
                } else {
                    fail(routingContext, response.getStatus());
                }
            } else {
                logger.error("handleAuthenticated:failure", result.cause());
                logger.error("handleAuthenticated:failure{}", routingContext.normalisedPath());

                fail(routingContext, ResponseStatus.ERROR);
            }
        } catch (Exception ex) {
            logger.error("handleAuthenticated:exception", ex);
            fail(routingContext, ResponseStatus.ERROR);
        }
    }

    private void succeed(RoutingContext routingContext, IResponse response, String token) {
        HttpServerResponse serverResponse = routingContext.request().response()
                .setChunked(true)
                .putHeader("Content-Type", "application/json");

        if (token != null) {
            serverResponse.putHeader("iRunningLog-Token", token);
        }

        serverResponse.setStatusCode(response.getStatus().getCode())
                .write(mapper.encode(response.getBody())).end();
    }

    private void fail(RoutingContext routingContext, ResponseStatus error) {
        if (logger.isErrorEnabled()) {
            logger.error("fail:{}:{}", routingContext.normalisedPath(), error);
        }

        routingContext.request().response().setChunked(true)
                .setStatusCode(error.getCode())
                .write(error.getMessage())
                .end();
    }

    public final Endpoint endpoint() {
        return endpoint;
    }

}
