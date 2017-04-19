package com.irunninglog.vertx.data;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;

abstract class AbstractGetDataVerticle<T extends IResponse> extends AbstractProfileIdEndpointVerticle<IGetDataRequest, T> {

    final IDataService dataService;

    AbstractGetDataVerticle(IDataService dataService, IFactory factory, IMapper mapper) {
        super(factory, mapper);

        this.dataService = dataService;
    }

    @Override
    protected boolean authorized(IUser user, IGetDataRequest request) {
        return matches(user, request);
    }

}
