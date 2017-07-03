package com.irunninglog.vertx.shoes;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GET_SHOES)
public class GetShoesVerticle extends AbstractRequestResponseVerticle {

    private final IShoesService shoesService;

    public GetShoesVerticle(IFactory factory, IMapper mapper, IShoesService shoesService) {
        super(factory, mapper);

        this.shoesService = shoesService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        response.setStatus(ResponseStatus.OK).setBody(shoesService.getShoes(request.getUser()));
    }

}
