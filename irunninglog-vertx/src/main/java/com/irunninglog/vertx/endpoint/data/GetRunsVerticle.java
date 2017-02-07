package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetRuns)
public class GetRunsVerticle extends AbstractGetDataVerticle<IGetRunsResponse> {

    public GetRunsVerticle(IDataService dataService, IFactory factory, IMapper mapper) {
        super(dataService, factory, mapper, IGetRunsResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetRunsResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(dataService.runs(request.getProfileId()));
    }

}
