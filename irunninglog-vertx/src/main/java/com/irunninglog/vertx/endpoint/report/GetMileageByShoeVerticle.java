package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.report.GetDataSetResponse;
import com.irunninglog.report.GetReportRequest;
import com.irunninglog.report.IReportService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetMileageByShoe)
public final class GetMileageByShoeVerticle extends AbstractGetReportVerticle<GetDataSetResponse> {

    public GetMileageByShoeVerticle(IReportService reportService) {
        super(reportService, GetDataSetResponse::new);
    }

    @Override
    protected GetDataSetResponse handle(GetReportRequest request) {
        return reportService.mileageByShoe(request);
    }

}
