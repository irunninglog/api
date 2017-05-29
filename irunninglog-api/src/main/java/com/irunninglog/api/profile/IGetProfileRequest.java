package com.irunninglog.api.profile;

import com.irunninglog.api.IProfileIdRequest;

public interface IGetProfileRequest extends IProfileIdRequest<IGetProfileRequest> {

    String getToken();

    IGetProfileRequest setToken(String token);

}
