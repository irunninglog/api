package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetDashboardRequest extends AbstractProfileIdRequest<IGetDashboardRequest> implements IGetDashboardRequest {

    public GetDashboardRequest() {
        super();

        logger.debug("Created {}", hashCode());
    }

}
