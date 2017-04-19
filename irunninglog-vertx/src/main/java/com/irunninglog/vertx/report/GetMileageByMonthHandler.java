package com.irunninglog.vertx.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.REPORT_MILEAGE_MONTH_GET, request = IGetReportRequest.class, response = IGetMultiSetResponse.class)
public final class GetMileageByMonthHandler extends AbstractGetReportHandler<IGetMultiSetResponse> {

    public GetMileageByMonthHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
