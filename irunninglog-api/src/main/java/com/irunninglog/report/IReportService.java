package com.irunninglog.report;

public interface IReportService {

    GetMultiSetResponse mileageByMonth(GetReportRequest request);

    GetDataSetResponse mileageByRoute(GetReportRequest request);

    GetDataSetResponse mileageByRun(GetReportRequest request);

    GetDataSetResponse mileageByShoe(GetReportRequest request);

}
