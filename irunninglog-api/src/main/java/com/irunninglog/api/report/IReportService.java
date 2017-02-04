package com.irunninglog.api.report;

public interface IReportService {

    IMultiSet mileageByMonth(long profileId);

    IDataSet mileageByRoute(long profileId);

    IDataSet mileageByRun(long profileId);

    IDataSet mileageByShoe(long profileId);

}
