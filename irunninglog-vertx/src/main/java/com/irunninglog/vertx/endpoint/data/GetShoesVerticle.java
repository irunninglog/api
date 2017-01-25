package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.GetShoesResponse;
import com.irunninglog.data.IDataService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetShoes)
public class GetShoesVerticle extends AbstractGetDataVerticle<GetShoesResponse> {

    public GetShoesVerticle(IDataService dataService) {
        super(dataService, GetShoesResponse::new);
    }

    @Override
    protected GetShoesResponse handle(GetDataRequest request) {
        return dataService.shoes(request);
    }

}
