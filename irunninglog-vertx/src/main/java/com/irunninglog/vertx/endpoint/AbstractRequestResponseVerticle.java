package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestResponseVerticle<Q extends IRequest, S extends IResponse> extends AbstractVerticle {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final IFactory factory;
    private final IMapper mapper;
    private final Class<S> responseClass;
    private final Class<Q> requestClass;

    protected AbstractRequestResponseVerticle(IFactory factory, IMapper mapper, Class<Q> requestClass, Class<S> responseClass) {
        super();

        this.factory = factory;
        this.mapper = mapper;
        this.responseClass = responseClass;
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
        return msg -> vertx.<String>executeBlocking(future -> handleResponse(future, msg),
                result -> msg.reply(result.result()));
    }

    private void handleResponse(Future<String> future, Message<String> msg) {
        long start = System.currentTimeMillis();

        logger.info("handler:{}:{}", address(), msg.body());

        try {
            logger.info("handler:start");

            Q request = mapper.decode(msg.body(), requestClass);

            logger.info("handler:request:{}", request);

            S response = factory.get(responseClass);
            handle(request, response);

            logger.info("handler:response:{}", response);

            future.complete(mapper.encode(response));
        } catch (Exception ex) {
            try {
                logger.error("handler:exception:{}", ex);

                S response = fromException(ex);

                logger.error("handler:exception:{}", response);

                future.complete(mapper.encode(response));
            } catch (Exception ex1) {
                logger.error("handler:exception:" + ex1, ex1);

                future.fail(ex1);
            }
        } finally {
            logger.info("handler:{}:{}ms", address(), System.currentTimeMillis() - start);
        }
    }

    protected abstract String address();

    protected abstract void handle(Q request, S response);

    private S fromException(Exception ex) {
        S response = factory.get(responseClass);

        ResponseStatus status;
        boolean statusException = ex instanceof ResponseStatusException;

        logger.info("fromException:{}:{}", statusException, ex.getClass());

        if (statusException) {
            status = ((ResponseStatusException) ex).getResponseStatus();
        } else {
            status = ResponseStatus.ERROR;
        }

        logger.info("fromException:{}", status);

        response.setStatus(status);

        return response;
    }

}
