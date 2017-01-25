package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.GetRunsResponse;
import com.irunninglog.data.IDataService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetRuns)
public class GetRunsVerticle extends AbstractGetDataVerticle<GetRunsResponse> {

    public GetRunsVerticle(IDataService dataService) {
        super(dataService, GetRunsResponse::new);
    }

    @Override
    protected GetRunsResponse handle(GetDataRequest request) {
        return dataService.runs(request);
    }

}
