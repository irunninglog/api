package com.irunninglog.vertx.endpoint.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.REPORT_MILEAGE_SHOE_GET)
public final class GetMileageByShoeVerticle extends AbstractGetReportVerticle<IGetDataSetResponse> {

    public GetMileageByShoeVerticle(IReportService reportService, IFactory factory, IMapper mapper) {
        super(reportService, factory, mapper, IGetDataSetResponse.class);
    }

    @Override
    protected void handle(IGetReportRequest request, IGetDataSetResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(reportService.mileageByShoe(request.getProfileId()));
    }

}
