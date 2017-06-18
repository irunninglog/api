package com.irunninglog.spring;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class DeafultResponse implements IResponse {

    private ResponseStatus status;
    private Object body;

    @Override
    public IResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public IResponse setBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public Object getBody() {
        return body;
    }

}
