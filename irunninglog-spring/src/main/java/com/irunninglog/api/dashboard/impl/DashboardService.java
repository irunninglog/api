package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.dashboard.DashboardInfo;
import com.irunninglog.api.dashboard.DashboardRequest;
import com.irunninglog.api.dashboard.DashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.service.ResponseStatus;
import org.springframework.stereotype.Service;

@Service
public class DashboardService implements IDashboardService {

    @Override
    public DashboardResponse get(DashboardRequest request) {
        return new DashboardResponse()
                .setBody(new DashboardInfo())
                .setStatus(ResponseStatus.Ok);
    }

}
