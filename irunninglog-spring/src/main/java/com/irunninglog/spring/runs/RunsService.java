package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.IUser;
import org.springframework.stereotype.Service;

@Service
final class RunsService implements IRunsService {

    @Override
    public IRun create(IUser user, IRun run) {
        throw new UnsupportedOperationException("create not supported");
    }

    @Override
    public IRun update(IUser user, IRun run) {
        throw new UnsupportedOperationException("update not supported");
    }

}
