package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.report.GetReportRequest;
import com.irunninglog.report.IReportService;
import com.irunninglog.service.AbstractResponse;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;

import java.util.function.Supplier;

abstract class AbstractGetReportVerticle<T extends AbstractResponse> extends AbstractEndpointVerticle<GetReportRequest, T> {

    final IReportService reportService;

    AbstractGetReportVerticle( IReportService reportService, Supplier<T> responseSupplier) {
        super(GetReportRequest.class, responseSupplier);

        this.reportService = reportService;
    }

}
