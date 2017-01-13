package com.irunninglog.vertx.endpoint;

import com.irunninglog.service.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public abstract class AbstractRequestResponseVerticle<Q extends AbstractRequest, S extends AbstractResponse> extends AbstractVerticle {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Supplier<S> constructor;
    private final Class<Q> requestClass;

    protected AbstractRequestResponseVerticle(Class<Q> requestClass, Supplier<S> responseSupplier) {
        super();

        this.constructor = responseSupplier;
        this.requestClass = requestClass;
    }

    @Override
    public final void start() throws Exception {
        logger.info("start:start");

        super.start();

        vertx.eventBus().<String>consumer(address()).handler(handler());

        logger.info("start:end");
    }

    private Handler<Message<String>> handler() {
        return msg -> vertx.<String>executeBlocking(future -> {
                    long start = System.currentTimeMillis();

                    logger.info("handler:{}:{}", address(), msg.body());

                    try {
                        logger.info("handler:start");

                        Q request = Json.decodeValue(msg.body(), requestClass);

                        logger.info("handler:request:{}", request);

                        S response = handle(request);

                        logger.info("handler:response:{}", response);

                        future.complete(Json.encode(response));
                    } catch (Exception ex) {
                        logger.error("handler:exception:{}", ex);

                        S response = fromException(ex);

                        logger.error("handler:exception:{}", response);

                        future.complete(Json.encode(response));
                    } finally {
                        logger.info("handler:{}:{}ms", address(), System.currentTimeMillis() - start);
                    }
                },
                result -> msg.reply(result.result()));
    }

    protected abstract String address();

    protected abstract S handle(Q request);

    private S fromException(Exception ex) {
        S response = constructor.get();

        ResponseStatus status;
        boolean statusException = ex instanceof ResponseStatusException;

        logger.info("fromException:{}:{}", statusException, ex.getClass());

        if (statusException) {
            status = ((ResponseStatusException) ex).getResponseStatus();
        } else {
            status = ResponseStatus.Error;
        }

        logger.info("fromException:{}", status);

        response.setStatus(status);

        return response;
    }

}
