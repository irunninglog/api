package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;

abstract class AbstractGetReportVerticle<T extends IResponse> extends AbstractEndpointVerticle<IGetReportRequest, T> {

    final IReportService reportService;

    AbstractGetReportVerticle(IReportService reportService, IFactory factory, IMapper mapper, Class<T> responseClass) {
        super(factory, mapper, IGetReportRequest.class, responseClass);

        this.reportService = reportService;
    }

}
