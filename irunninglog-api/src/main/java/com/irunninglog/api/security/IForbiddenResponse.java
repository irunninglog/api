package com.irunninglog.api.security;

import com.irunninglog.api.IResponse;

public interface IForbiddenResponse<T extends IForbiddenResponse> extends IResponse<String, T> {

}
