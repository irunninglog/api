package com.irunninglog.vertx.route;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.Envelope;
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

            Q request = factory.get(requestClass);

            request(request, routingContext);

            String requestString = mapper.encode(request);

            Envelope envelope = new Envelope().setRequest(requestString).setUser(routingContext.get("user"));

            vertx.eventBus().<String>send(endpoint.getAddress(),
                    mapper.encode(envelope),
                    result -> handle(result, routingContext));
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("handle:end:{}:{}ms", routingContext.normalisedPath(), System.currentTimeMillis() - start);
            }
        }
    }

    protected void request(Q request, RoutingContext routingContext) {
        // Empty for subclasses
    }

    private void handle(AsyncResult<Message<String>> result, RoutingContext routingContext) {
        try {
            if (result.succeeded()) {
                String resultString = result.result().body();

                logger.info("handleAuthenticated:{}:{}", endpoint.getAddress(), resultString);

                S response = mapper.decode(resultString, responseClass);

                logger.info(LOG_STMT, endpoint.getAddress(), response);

                if (response.getStatus() == ResponseStatus.OK) {
                    succeed(routingContext, response);
                } else {
                    fail(routingContext, response.getStatus());
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error("handleAuthenticated:failure", result.cause());
                    logger.error("handleAuthenticated:failure{}", routingContext.normalisedPath());
                }

                fail(routingContext, ResponseStatus.ERROR);
            }
        } catch (Exception ex) {
            logger.error("handleAuthenticated:exception", ex);
            fail(routingContext, ResponseStatus.ERROR);
        }
    }

    private void succeed(RoutingContext routingContext, IResponse response) {
        HttpServerResponse serverResponse = routingContext.request().response()
                .setChunked(true)
                .putHeader("Content-Type", "application/json");

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
