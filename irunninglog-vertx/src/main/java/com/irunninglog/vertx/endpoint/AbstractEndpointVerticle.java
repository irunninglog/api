package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;

public abstract class AbstractEndpointVerticle<Q extends IRequest, S extends IResponse> extends AbstractRequestResponseVerticle<Q, S> {

    private final String address;

    protected AbstractEndpointVerticle(IFactory factory, Class<Q> requestClass, Class<S> responseClass) {
        super(factory, requestClass, responseClass);

        EndpointVerticle endpointVerticle = getClass().getAnnotation(EndpointVerticle.class);
        address = endpointVerticle.endpoint().getAddress();
    }

    @Override
    protected final String address() {
        return address;
    }

}
