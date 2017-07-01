package com.irunninglog.api.streaks;

import com.irunninglog.api.security.IUser;

public interface IStreaksService {

    IStreaks getStreaks(IUser user, int offset);

}
