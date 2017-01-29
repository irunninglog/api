package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.report.GetMultiSetResponse;
import com.irunninglog.report.GetReportRequest;
import com.irunninglog.report.IReportService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetMileageByMonth)
public final class GetMileageByMonthVerticle extends AbstractGetReportVerticle<GetMultiSetResponse> {

    public GetMileageByMonthVerticle(IReportService reportService) {
        super(reportService, GetMultiSetResponse::new);
    }

    @Override
    protected GetMultiSetResponse handle(GetReportRequest request) {
        return reportService.mileageByMonth(request);
    }

}
