package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetRoutes)
public class GetRoutesVerticle extends AbstractGetDataVerticle<IGetRoutesResponse> {

    public GetRoutesVerticle(IDataService dataService, IFactory factory) {
        super(dataService, factory, IGetRoutesResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetRoutesResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(dataService.routes(request.getProfileId()));
    }

}
