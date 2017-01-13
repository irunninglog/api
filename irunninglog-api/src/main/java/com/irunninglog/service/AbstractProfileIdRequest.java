package com.irunninglog.service;

public abstract class AbstractProfileIdRequest<T extends AbstractProfileIdRequest> extends AbstractRequest<T> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private long id;

    public final long getId() {
        return id;
    }

    public final T setId(long id) {
        this.id = id;
        return myself;
    }

}
