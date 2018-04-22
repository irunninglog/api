package com.irunninglog.api.runs;

import com.irunninglog.api.security.IUser;

public interface IRunsService {

    void create(IUser user, IRun run);

    void update(IUser user, IRun run);

}
