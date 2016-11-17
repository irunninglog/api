package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.dashboard.ProgressInfo;
import com.irunninglog.api.profile.impl.ProfileEntity;
import com.irunninglog.api.workout.impl.WorkoutEntity;
import com.irunninglog.api.workout.impl.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class DashboardProgressService {

    private final WorkoutsService workoutsService;

    @Autowired
    public DashboardProgressService(WorkoutsService workoutsService) {
        this.workoutsService = workoutsService;
    }

    Collection<ProgressInfo> progress(ProfileEntity profile, int offset) {
        List<WorkoutEntity> weekly = workoutsService.findWorkoutsThisWeek(profile.getId(), profile.getWeekStart(), offset);

        return Collections.emptyList();
    }

}
