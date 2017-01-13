package com.irunninglog.vertx.endpoint.ping;

import com.irunninglog.ping.Ping;
import com.irunninglog.ping.PingRequest;
import com.irunninglog.ping.PingResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

import java.time.LocalDateTime;

@EndpointVerticle(endpoint = Endpoint.Ping)
public class PingVerticle extends AbstractEndpointVerticle<PingRequest, PingResponse> {

    public PingVerticle() {
        super(PingRequest.class, PingResponse::new);
    }

    @Override
    protected PingResponse handle(PingRequest request) {
        return new PingResponse()
                .setStatus(ResponseStatus.Ok)
                .setBody(new Ping().setTimestamp(LocalDateTime.now().toString()));
    }

}
