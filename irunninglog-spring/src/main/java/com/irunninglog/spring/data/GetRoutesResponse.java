package com.irunninglog.spring.data;

import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.data.IRoutes;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetRoutesResponse extends AbstractResponse<IRoutes, GetRoutesResponse> implements IGetRoutesResponse<GetRoutesResponse> {

}
