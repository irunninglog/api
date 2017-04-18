package com.irunninglog.vertx.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.SHOES_GET)
public final class GetShoesVerticle extends AbstractGetDataVerticle<IGetShoesResponse> {

    public GetShoesVerticle(IDataService dataService, IFactory factory, IMapper mapper) {
        super(dataService, factory, mapper, IGetShoesResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetShoesResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(dataService.shoes(request.getProfileId()));
    }

}
