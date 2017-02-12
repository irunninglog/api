package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetDashboardResponse extends AbstractResponse<IDashboardInfo, GetDashboardResponse> implements IGetDashboardResponse<GetDashboardResponse> {

}
