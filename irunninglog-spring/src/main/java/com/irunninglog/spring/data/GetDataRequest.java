package com.irunninglog.spring.data;

import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetDataRequest extends AbstractProfileIdRequest<IGetDataRequest> implements IGetDataRequest {

}
