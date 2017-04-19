package com.irunninglog.vertx.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.api.report.IReportService;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.REPORT_MILEAGE_MONTH_GET, request = IGetReportRequest.class, response = IGetMultiSetResponse.class)
public final class GetMileageByMonthVerticle extends AbstractGetReportVerticle<IGetMultiSetResponse> {

    public GetMileageByMonthVerticle(IReportService reportService, IFactory factory, IMapper mapper) {
        super(reportService, factory, mapper);
    }

    @Override
    protected void handle(IGetReportRequest request, IGetMultiSetResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(reportService.mileageByMonth(request.getProfileId()));
    }

}
