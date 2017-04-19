package com.irunninglog.vertx.report;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import io.vertx.core.Vertx;

abstract class AbstractGetReportHandler<T extends IResponse> extends AbstractProfileIdRouteHandler<IGetReportRequest, T> {

    AbstractGetReportHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
