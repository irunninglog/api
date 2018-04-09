package com.irunninglog.vertx.runs;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PUT_RUN)
public class PutRunVerticle extends AbstractRequestResponseVerticle {

    private final IRunsService runsService;

    public PutRunVerticle(IFactory factory, IMapper mapper, IRunsService runsService) {
        super(factory, mapper);
        this.runsService = runsService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        System.out.println();
    }
}
