package com.irunninglog.api.profile;

import com.irunninglog.api.security.IUser;

public interface IProfileService {

    IProfile get(IUser user);

}
