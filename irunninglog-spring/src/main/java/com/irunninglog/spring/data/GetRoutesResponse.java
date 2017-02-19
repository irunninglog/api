package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.data.IRoutes;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetRoutesResponse extends AbstractResponse<IRoutes, GetRoutesResponse> implements IGetRoutesResponse<GetRoutesResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Routes.class)
    public IRoutes getBody() {
        return body();
    }

}
