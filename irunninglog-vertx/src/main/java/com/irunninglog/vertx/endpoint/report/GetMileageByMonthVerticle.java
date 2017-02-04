package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetMileageByMonth)
public final class GetMileageByMonthVerticle extends AbstractGetReportVerticle<IGetMultiSetResponse> {

    public GetMileageByMonthVerticle(IReportService reportService, IFactory factory) {
        super(reportService, factory, IGetMultiSetResponse.class);
    }

    @Override
    protected void handle(IGetReportRequest request, IGetMultiSetResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(reportService.mileageByMonth(request.getProfileId()));
    }

}
