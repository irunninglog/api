package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PING, request = IPingRequest.class, response = IPingResponse.class)
public final class PingVerticle extends AbstractRequestResponseVerticle<IPingRequest, IPingResponse> {

    private final IPingService pingService;

    public PingVerticle(IFactory factory, IMapper mapper, IPingService pingService) {
        super(factory, mapper);

        this.pingService = pingService;
    }

    @Override
    protected void handle(IPingRequest request, IPingResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(pingService.ping());
    }

    @Override
    protected boolean authorized(IUser user, IPingRequest request) {
        return Boolean.TRUE;
    }

}
