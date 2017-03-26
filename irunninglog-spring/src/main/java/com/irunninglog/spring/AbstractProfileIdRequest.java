package com.irunninglog.spring;

import com.irunninglog.api.IProfileIdRequest;

public abstract class AbstractProfileIdRequest<T extends IProfileIdRequest> extends AbstractRequest<T> implements IProfileIdRequest<T> {

    private final T myself = (T) this;

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
