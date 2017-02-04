package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetShoes)
public class GetShoesVerticle extends AbstractGetDataVerticle<IGetShoesResponse> {

    public GetShoesVerticle(IDataService dataService, IFactory factory) {
        super(dataService, factory, IGetShoesResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetShoesResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(dataService.shoes(request.getProfileId()));
    }

}
