package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetRuns)
public class GetRunsVerticle extends AbstractGetDataVerticle<IGetRunsResponse> {

    public GetRunsVerticle(IDataService dataService, IFactory factory) {
        super(dataService, factory, IGetRunsResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetRunsResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(dataService.runs(request.getProfileId()));
    }

}
