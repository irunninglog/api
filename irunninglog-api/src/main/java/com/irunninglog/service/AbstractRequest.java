package com.irunninglog.service;

public abstract class AbstractRequest<T extends AbstractRequest> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private long id;
    private int offset;

    public final long getId() {
        return id;
    }

    public final T setId(long id) {
        this.id = id;
        return myself;
    }

    public final int getOffset() {
        return offset;
    }

    public final T setOffset(int offset) {
        this.offset = offset;
        return myself;
    }

}
