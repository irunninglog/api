package com.irunninglog.spring;

import com.irunninglog.api.IProfileIdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProfileIdRequest<T extends IProfileIdRequest> extends AbstractRequest<T> implements IProfileIdRequest<T> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    protected final Logger logger = LoggerFactory.getLogger(myself.getClass());

    private long profileId;

    @Override
    public T setProfileId(long profileId) {
        this.profileId = profileId;
        return myself;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

}
