package com.irunninglog.vertx.streaks;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GET_STREAKS)
public class GetStreaksVerticle extends AbstractRequestResponseVerticle {

    private final IStreaksService streaksService;

    // Public for reflection
    @SuppressWarnings("WeakerAccess")
    public GetStreaksVerticle(IFactory factory, IMapper mapper, IStreaksService streaksService) {
        super(factory, mapper);

        this.streaksService = streaksService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) {
        response.setStatus(ResponseStatus.OK).setBody(streaksService.getStreaks(request.getUser(), request.getOffset()));
    }

}
