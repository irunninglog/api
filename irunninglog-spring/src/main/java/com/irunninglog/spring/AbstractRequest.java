package com.irunninglog.spring;

import com.irunninglog.api.IRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequest<T extends IRequest> implements IRequest<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
