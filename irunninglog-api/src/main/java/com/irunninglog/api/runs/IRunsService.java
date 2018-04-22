package com.irunninglog.api.runs;

import com.irunninglog.api.security.IUser;

public interface IRunsService {

    IRun create(IUser user, IRun run);

    IRun update(IUser user, IRun run);

}
