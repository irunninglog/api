package com.irunninglog.vertx.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.ROUTES_GET, request = IGetDataRequest.class, response = IGetRoutesResponse.class)
public final class GetRoutesVerticle extends AbstractGetDataVerticle<IGetRoutesResponse> {

    public GetRoutesVerticle(IDataService dataService, IFactory factory, IMapper mapper) {
        super(dataService, factory, mapper);
    }

    @Override
    protected void handle(IGetDataRequest request, IGetRoutesResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(dataService.routes(request.getProfileId()));
    }

}
