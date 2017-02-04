package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;

abstract class AbstractGetDataVerticle<T extends IResponse> extends AbstractEndpointVerticle<IGetDataRequest, T> {

    final IDataService dataService;

    AbstractGetDataVerticle(IDataService dataService, IFactory factory, Class<T> responseClass) {
        super(factory, IGetDataRequest.class, responseClass);

        this.dataService = dataService;
    }

}
