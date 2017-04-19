package com.irunninglog.vertx.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.REPORT_MILEAGE_SHOE_GET, request = IGetReportRequest.class, response = IGetDataSetResponse.class)
public final class GetMileageByShoeHandler extends AbstractGetReportHandler<IGetDataSetResponse> {

    public GetMileageByShoeHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
