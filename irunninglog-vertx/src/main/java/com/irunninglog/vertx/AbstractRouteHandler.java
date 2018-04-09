package com.irunninglog.vertx;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public abstract class AbstractRouteHandler implements Handler<RoutingContext> {

    private static final String LOG_STMT = "handle:{}:{}";

    private final Vertx vertx;
    private final IFactory factory;
    private final IMapper mapper;
    private final Endpoint endpoint;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRouteHandler(Vertx vertx,
                                IFactory factory,
                                IMapper mapper) {

        this.vertx = vertx;
        this.factory = factory;
        this.mapper = mapper;

        EndpointHandler endpointHandler = this.getClass().getAnnotation(EndpointHandler.class);
        this.endpoint = endpointHandler.endpoint();
    }

    @Override
    public final void handle(RoutingContext routingContext) {
        long start = System.currentTimeMillis();

        try {
            if (logger.isInfoEnabled()) {
                logger.info("handle:start:{}", routingContext.normalisedPath());
            }

            IRequest request = factory.get(IRequest.class);
            request.setMap(new HashMap<>());
            request.setUser(routingContext.get("user"));
            request.setBody(routingContext.getBodyAsString());

            String offset = routingContext.request().getHeader("IRL-TimeZone-Offset");
            request.setOffset(offset == null ? 0 : Integer.parseInt(offset));

            request(request, routingContext);

            vertx.eventBus().<String>send(endpoint.getAddress(),
                    mapper.encode(request),
                    result -> handle(result, routingContext));
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("handle:end:{}:{}ms", routingContext.normalisedPath(), System.currentTimeMillis() - start);
            }
        }
    }

    protected void request(IRequest request, RoutingContext routingContext) {
        // Empty for subclasses
    }

    private void handle(AsyncResult<Message<String>> result, RoutingContext routingContext) {
        try {
            if (result.succeeded()) {
                String resultString = result.result().body();

                if (logger.isDebugEnabled()) {
                    logger.debug(LOG_STMT, endpoint.getAddress(), resultString);
                }

                IResponse response = mapper.decode(resultString, IResponse.class);

                logger.info(LOG_STMT, endpoint.getAddress(), response);

                if (response.getStatus() == ResponseStatus.OK) {
                    succeed(routingContext, response);
                } else {
                    fail(routingContext, response.getStatus());
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error("handle:failure", result.cause());
                    logger.error("handle:failure{}", routingContext.normalisedPath());
                }

                fail(routingContext, ResponseStatus.ERROR);
            }
        } catch (Exception ex) {
            logger.error("handle:exception", ex);
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

        if (error == null) {
            error = ResponseStatus.ERROR;
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
