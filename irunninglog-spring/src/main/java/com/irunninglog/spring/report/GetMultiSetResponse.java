package com.irunninglog.spring.report;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IMultiSet;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetMultiSetResponse extends AbstractResponse<IMultiSet, GetMultiSetResponse> implements IGetMultiSetResponse<GetMultiSetResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MultiSet.class)
    public IMultiSet getBody() {
        return body();
    }

}
