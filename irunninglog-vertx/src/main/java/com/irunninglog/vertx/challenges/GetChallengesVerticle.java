package com.irunninglog.vertx.challenges;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GET_CHALLENGES)
public class GetChallengesVerticle extends AbstractRequestResponseVerticle {

    private final IChallengesService service;

    public GetChallengesVerticle(IFactory factory, IMapper mapper, IChallengesService service) {
        super(factory, mapper);

        this.service = service;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        response.setStatus(ResponseStatus.OK).setBody(service.getChallenges(request.getUser()));
    }

}
