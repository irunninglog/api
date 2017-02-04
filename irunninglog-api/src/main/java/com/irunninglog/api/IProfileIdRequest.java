package com.irunninglog.api;

public interface IProfileIdRequest<T extends IProfileIdRequest> extends IRequest<T> {

    T setProfileId(long profileId);

    long getProfileId();

}
