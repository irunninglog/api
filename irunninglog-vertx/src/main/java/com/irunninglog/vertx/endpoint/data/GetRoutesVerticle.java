package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.GetRoutesResponse;
import com.irunninglog.data.IDataService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetRoutes)
public class GetRoutesVerticle extends AbstractGetDataVerticle<GetRoutesResponse> {

    public GetRoutesVerticle(IDataService dataService) {
        super(dataService, GetRoutesResponse::new);
    }

    @Override
    protected GetRoutesResponse handle(GetDataRequest request) {
        return dataService.routes(request);
    }

}
