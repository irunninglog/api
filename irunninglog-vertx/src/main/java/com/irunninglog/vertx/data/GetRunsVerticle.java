package com.irunninglog.vertx.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.RUNS_GET)
public final class GetRunsVerticle extends AbstractGetDataVerticle<IGetRunsResponse> {

    public GetRunsVerticle(IDataService dataService, IFactory factory, IMapper mapper) {
        super(dataService, factory, mapper, IGetRunsResponse.class);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetRunsResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(dataService.runs(request.getProfileId()));
    }

}
