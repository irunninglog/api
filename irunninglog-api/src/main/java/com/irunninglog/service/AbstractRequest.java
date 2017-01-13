package com.irunninglog.service;

public abstract class AbstractRequest<T extends AbstractRequest> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private int offset;

    public final int getOffset() {
        return offset;
    }

    public final T setOffset(int offset) {
        this.offset = offset;
        return myself;
    }

}
