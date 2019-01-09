package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.strava.StravaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class RunsService implements IRunsService {

    private final StravaApiService stravaApiService;

    @Autowired
    RunsService(StravaApiService stravaApiService) {
        this.stravaApiService = stravaApiService;
    }

    @Override
    public IRun create(IUser user, IRun run) {
        return stravaApiService.create(user, run);
    }

    @Override
    public IRun update(IUser user, IRun run) {
        return stravaApiService.update(user, run);
    }

}
