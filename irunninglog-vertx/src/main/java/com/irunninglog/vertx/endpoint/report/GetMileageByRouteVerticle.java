package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetMileageByRoute)
public final class GetMileageByRouteVerticle extends AbstractGetReportVerticle<IGetDataSetResponse> {

    public GetMileageByRouteVerticle(IReportService reportService, IFactory factory) {
        super(reportService, factory, IGetDataSetResponse.class);
    }

    @Override
    protected void handle(IGetReportRequest request, IGetDataSetResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(reportService.mileageByRoute(request.getProfileId()));
    }

}
