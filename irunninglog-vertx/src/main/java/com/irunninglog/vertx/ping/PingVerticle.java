package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PING)
public final class PingVerticle extends AbstractRequestResponseVerticle {

    private final IPingService pingService;

    // Public for reflection
    @SuppressWarnings("WeakerAccess")
    public PingVerticle(IFactory factory, IMapper mapper, IPingService pingService) {
        super(factory, mapper);

        this.pingService = pingService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) {
        response.setStatus(ResponseStatus.OK).setBody(pingService.ping());
    }

}
