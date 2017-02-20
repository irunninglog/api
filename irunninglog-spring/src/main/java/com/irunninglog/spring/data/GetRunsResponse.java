package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetRunsResponse extends AbstractResponse<Runs, GetRunsResponse> implements IGetRunsResponse<Runs, GetRunsResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Runs.class)
    public Runs getBody() {
        return body();
    }

}
