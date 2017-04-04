package com.irunninglog.vertx.endpoint.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PING)
public class PingVerticle extends AbstractRequestResponseVerticle<IPingRequest, IPingResponse> {

    private final IPingService pingService;

    public PingVerticle(IFactory factory, IMapper mapper, IPingService pingService) {
        super(factory, mapper, IPingRequest.class, IPingResponse.class);

        this.pingService = pingService;
    }

    @Override
    protected void handle(IPingRequest request, IPingResponse response) {
        response.setStatus(ResponseStatus.OK).setBody(pingService.ping());
    }

    @Override
    protected boolean authorized(IUser user, IPingRequest request) {
        return Boolean.TRUE;
    }

}
