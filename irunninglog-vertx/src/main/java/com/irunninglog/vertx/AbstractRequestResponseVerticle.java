package com.irunninglog.vertx;

import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestResponseVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IFactory factory;
    private final IMapper mapper;

    private final String address;

    public AbstractRequestResponseVerticle(IFactory factory, IMapper mapper) {
        super();

        this.factory = factory;
        this.mapper = mapper;

        EndpointVerticle endpointVerticle = getClass().getAnnotation(EndpointVerticle.class);
        address = endpointVerticle.endpoint().getAddress();
    }

    @Override
    public final void start() throws Exception {
        logger.info("start:start");

        super.start();

        vertx.eventBus().<String>consumer(address).handler(handler());

        logger.info("start:end");
    }

    private Handler<Message<String>> handler() {
        return msg -> vertx.<String>executeBlocking(future -> handleResponse(future, msg),
                result -> msg.reply(result.result()));
    }

    private void handleResponse(Future<String> future, Message<String> msg) {
        long start = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug("handler:{}:{}", address, msg.body());
        }

        try {
            logger.info("handler:start");

            IRequest request = mapper.decode(msg.body(), IRequest.class);

            logger.info("handler:request:{}", request);

            IResponse response = factory.get(IResponse.class);
            handle(request, response);

            logger.info("handler:response:{}", response);

            future.complete(mapper.encode(response));
        } catch (Exception ex) {
            logger.error("handler:exception:{}", ex);

            IResponse response = fromException(ex);

            logger.error("handler:exception:{}", response);

            future.complete(mapper.encode(response));
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("handler:{}:{}ms", address, System.currentTimeMillis() - start);
            }
        }
    }

    protected abstract void handle(IRequest request, IResponse response) throws AuthnException;

    private IResponse fromException(Exception ex) {
        IResponse response = factory.get(IResponse.class);

        ResponseStatus status;

        logger.info("fromException:{}", ex.getClass());

        if (ex instanceof AuthnException) {
            status = ResponseStatus.UNAUTHENTICATED;
        } else {
            status = ResponseStatus.ERROR;
        }

        logger.info("fromException:{}", status);

        response.setStatus(status);

        return response;
    }

}
