package com.irunninglog.spring.dashboard;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetDashboardResponse extends AbstractResponse<DashboardInfo, GetDashboardResponse> implements IGetDashboardResponse<DashboardInfo, GetDashboardResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = DashboardInfo.class)
    public DashboardInfo getBody() {
        return body();
    }

}
