package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.IDataService;
import com.irunninglog.service.AbstractResponse;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;

import java.util.function.Supplier;

abstract class AbstractGetDataVerticle<T extends AbstractResponse> extends AbstractEndpointVerticle<GetDataRequest, T> {

    final IDataService dataService;

    AbstractGetDataVerticle(IDataService dataService, Supplier<T> responseSupplier) {
        super(GetDataRequest.class, responseSupplier);

        this.dataService = dataService;
    }

}
