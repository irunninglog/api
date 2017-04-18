package com.irunninglog.api.identity;

import com.irunninglog.api.IRequest;

public interface IIdentityRequest<T extends IIdentityRequest> extends IRequest<T> {

    String getUsername();

    T setUsername(String username);

}
