package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;

abstract class AbstractGetDataVerticle<T extends IResponse> extends AbstractEndpointVerticle<IGetDataRequest, T> {

    final IDataService dataService;

    AbstractGetDataVerticle(IDataService dataService, IFactory factory, IMapper mapper, Class<T> responseClass) {
        super(factory, mapper, IGetDataRequest.class, responseClass);

        this.dataService = dataService;
    }

}
