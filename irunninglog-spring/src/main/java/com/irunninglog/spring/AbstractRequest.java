package com.irunninglog.spring;

import com.irunninglog.api.IRequest;

public abstract class AbstractRequest<T extends IRequest> implements IRequest<T> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private int offset;

    @Override
    public T setOffset(int offset) {
        this.offset = offset;
        return myself;
    }

    @Override
    public int getOffset() {
        return offset;
    }

}
