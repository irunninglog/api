package com.irunninglog.spring.report.impl;

import com.irunninglog.report.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.service.ApiService;

@ApiService
public final class ReportService implements IReportService {

    @Override
    public GetMultiSetResponse mileageByMonth(GetReportRequest request) {
        return new GetMultiSetResponse().setBody(new MultiSet()).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetDataSetResponse mileageByRoute(GetReportRequest request) {
        return new GetDataSetResponse().setBody(new DataSet()).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetDataSetResponse mileageByRun(GetReportRequest request) {
        return new GetDataSetResponse().setBody(new DataSet()).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetDataSetResponse mileageByShoe(GetReportRequest request) {
        return new GetDataSetResponse().setBody(new DataSet()).setStatus(ResponseStatus.Ok);
    }

}
