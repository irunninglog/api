package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.GetShoesResponse;
import com.irunninglog.data.IDataService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetShoes)
public class GetShoesVerticle extends AbstractEndpointVerticle<GetDataRequest, GetShoesResponse> {

    private final IDataService dataService;

    public GetShoesVerticle(IDataService dataService) {
        super(GetDataRequest.class, GetShoesResponse::new);

        this.dataService = dataService;
    }

    @Override
    protected GetShoesResponse handle(GetDataRequest request) {
        return dataService.shoes(request);
    }

}
