package com.irunninglog.vertx.report;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;

abstract class AbstractGetReportVerticle<T extends IResponse> extends AbstractProfileIdEndpointVerticle<IGetReportRequest, T> {

    final IReportService reportService;

    AbstractGetReportVerticle(IReportService reportService, IFactory factory, IMapper mapper, Class<T> responseClass) {
        super(factory, mapper, IGetReportRequest.class, responseClass);

        this.reportService = reportService;
    }

    @Override
    protected final boolean authorized(IUser user, IGetReportRequest request) {
        return matches(user, request);
    }

}
