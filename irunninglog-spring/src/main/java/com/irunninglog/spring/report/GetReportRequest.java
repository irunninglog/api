package com.irunninglog.spring.report;

import com.irunninglog.api.report.IGetReportRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetReportRequest extends AbstractProfileIdRequest<IGetReportRequest> implements IGetReportRequest {

    public GetReportRequest() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
