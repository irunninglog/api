package com.irunninglog.vertx.endpoint;

import com.irunninglog.service.AbstractResponse;

import java.util.function.Supplier;

public abstract class AbstractEndpointVerticle<Q, S extends AbstractResponse> extends AbstractRequestResponseVerticle<Q, S> {

    private final String address;

    protected AbstractEndpointVerticle(Class<Q> requestClass, Supplier<S> responseSupplier) {
        super(requestClass, responseSupplier);

        EndpointVerticle endpointVerticle = getClass().getAnnotation(EndpointVerticle.class);
        address = endpointVerticle.endpoint().getId();
    }

    @Override
    protected final String address() {
        return address;
    }

}
