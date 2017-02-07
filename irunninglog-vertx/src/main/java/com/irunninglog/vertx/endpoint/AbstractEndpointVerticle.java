package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.mapping.IMapper;

public abstract class AbstractEndpointVerticle<Q extends IRequest, S extends IResponse> extends AbstractRequestResponseVerticle<Q, S> {

    private final String address;

    protected AbstractEndpointVerticle(IFactory factory, IMapper mapper, Class<Q> requestClass, Class<S> responseClass) {
        super(factory, mapper, requestClass, responseClass);

        EndpointVerticle endpointVerticle = getClass().getAnnotation(EndpointVerticle.class);
        address = endpointVerticle.endpoint().getAddress();
    }

    @Override
    protected final String address() {
        return address;
    }

}
