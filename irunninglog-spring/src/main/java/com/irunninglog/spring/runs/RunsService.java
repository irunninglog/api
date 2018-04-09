package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class RunsService implements IRunsService {

    private final IStravaService stravaService;

    @Autowired
    RunsService(IStravaService stravaService) {
        this.stravaService = stravaService;
    }

}
