package com.irunninglog.spring;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;

public abstract class AbstractResponse<R, T extends AbstractResponse> implements IResponse<R, T> {

    private R body;
    private ResponseStatus status;

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    @Override
    public final T setStatus(ResponseStatus status) {
        this.status = status;
        return myself;
    }

    @Override
    public final ResponseStatus getStatus() {
        return status;
    }

    @Override
    public final T setBody(R body) {
        this.body = body;
        return myself;
    }

    protected final R body() {
        return this.body;
    }

}
