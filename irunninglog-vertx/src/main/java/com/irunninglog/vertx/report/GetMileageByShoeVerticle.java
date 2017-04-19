package com.irunninglog.vertx.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.REPORT_MILEAGE_SHOE_GET, request = IGetReportRequest.class, response = IGetDataSetResponse.class)
public final class GetMileageByShoeVerticle extends AbstractGetReportVerticle<IGetDataSetResponse> {

    public GetMileageByShoeVerticle(IReportService reportService, IFactory factory, IMapper mapper) {
        super(reportService, factory, mapper);
    }

    @Override
    protected void handle(IGetReportRequest request, IGetDataSetResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(reportService.mileageByShoe(request.getProfileId()));
    }

}
