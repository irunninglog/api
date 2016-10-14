package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.service.AbstractResponse;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstactRouteHandler<R extends AbstractResponse> implements IRouteHandler {

    final Logger logger = LoggerFactory.getLogger(getClass());
    private final Vertx vertx;

    AbstactRouteHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public final void handle(RoutingContext routingContext) {
        logger.info("Handling {}", routingContext.currentRoute());

        String requestString = request(routingContext);

        vertx.eventBus().<String>send(address().getAddress(), requestString, result -> {
            if (result.succeeded()) {
                logger.info("Got a successful result {}", result.result().body());

                R response = response(result.result().body());

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
    }

    protected abstract R response(String body);

    protected abstract Address address();

    protected abstract String request(RoutingContext routingContext);

}
