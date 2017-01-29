package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.report.GetDataSetResponse;
import com.irunninglog.report.GetReportRequest;
import com.irunninglog.report.IReportService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetMileageByRoute)
public final class GetMileageByRouteVerticle extends AbstractGetReportVerticle<GetDataSetResponse> {

    public GetMileageByRouteVerticle(IReportService reportService) {
        super(reportService, GetDataSetResponse::new);
    }

    @Override
    protected GetDataSetResponse handle(GetReportRequest request) {
        return reportService.mileageByRoute(request);
    }

}
