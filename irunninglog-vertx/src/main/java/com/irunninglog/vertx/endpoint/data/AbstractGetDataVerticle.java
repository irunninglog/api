package com.irunninglog.vertx.endpoint.data;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.endpoint.AbstractProfileIdEndpointVerticle;

abstract class AbstractGetDataVerticle<T extends IResponse> extends AbstractProfileIdEndpointVerticle<IGetDataRequest, T> {

    final IDataService dataService;

    AbstractGetDataVerticle(IDataService dataService, IFactory factory, IMapper mapper, Class<T> responseClass) {
        super(factory, mapper, IGetDataRequest.class, responseClass);

        this.dataService = dataService;
    }

    @Override
    protected boolean authorized(IUser user, IGetDataRequest request) {
        return matches(user, request);
    }

}
