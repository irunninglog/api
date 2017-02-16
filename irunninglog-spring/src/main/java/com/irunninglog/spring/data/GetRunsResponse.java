package com.irunninglog.spring.data;

import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.data.IRuns;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetRunsResponse extends AbstractResponse<IRuns, GetRunsResponse> implements IGetRunsResponse<GetRunsResponse> {

}
