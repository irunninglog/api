package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.service.AbstractResponse;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstactRouteHandler<Q, S extends AbstractResponse> implements IRouteHandler {

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
            logger.info("handle:start:{}", routingContext.currentRoute());

            Q request = request(routingContext);

            String requestString = Json.encode(request);

            logger.info("handle:{}:{}", address(), requestString);

            vertx.eventBus().<String>send(address().getAddress(), requestString, result -> {
                if (result.succeeded()) {
                    String resultString = result.result().body();

                    logger.info("handle:{}:{}", address(), resultString);

                    S response = Json.decodeValue(resultString, responseClass);

                    if (response.getStatus() == ResponseStatus.Ok) {
                        routingContext.request().response()
                                .setChunked(true)
                                .putHeader("Content-Type", "application/json")
                                .setStatusCode(response.getStatus().getCode())
                                .write(Json.encode(response.getBody()))
                                .end();
                    } else {
                        routingContext.request().response()
                                .setChunked(true)
                                .setStatusCode(response.getStatus().getCode())
                                .write(response.getStatus().getMessage())
                                .end();
                    }
                } else {
                    routingContext.request().response().setChunked(true)
                            .setStatusCode(ResponseStatus.Error.getCode())
                            .write(ResponseStatus.Error.getMessage())
                            .end();
                }
            });
        } finally {
            logger.info("handle:end:{}:{}ms", routingContext.currentRoute(), System.currentTimeMillis() - start);
        }
    }

    protected abstract Q request(RoutingContext routingContext);

    protected abstract Address address();

}
