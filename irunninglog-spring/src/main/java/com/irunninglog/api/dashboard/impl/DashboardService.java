package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.dashboard.*;
import com.irunninglog.api.profile.impl.IProfileEntityRepository;
import com.irunninglog.api.profile.impl.ProfileEntity;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.api.service.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class DashboardService implements IDashboardService {

    private final IProfileEntityRepository profileEntityRepository;
    private final DashboardShoesService shoesService;
    private final DashboardGoalsService goalsService;

    @Autowired
    public DashboardService(IProfileEntityRepository profileEntityRepository,
                            DashboardGoalsService goalsService,
                            DashboardShoesService shoesService) {
        super();

        this.profileEntityRepository = profileEntityRepository;
        this.goalsService = goalsService;
        this.shoesService = shoesService;
    }

    @Override
    public DashboardResponse get(DashboardRequest request) {
        ProfileEntity userEntity = profileEntityRepository.findOne(request.getId());
        if (userEntity == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        DashboardInfo info = new DashboardInfo();

        info.getProgress().addAll(progress());
        info.getGoals().addAll(goals(userEntity));
        info.getShoes().addAll(shoes(userEntity));
        info.getStreaks().addAll(streaks());

        return new DashboardResponse()
                .setBody(info)
                .setStatus(ResponseStatus.Ok);
    }

    private Collection<ProgressInfo> progress() {
        return Collections.emptyList();
    }

    private Collection<ProgressInfo> goals(ProfileEntity profileEntity) {
        return goalsService.goals(profileEntity);
    }

    private Collection<ProgressInfo> streaks() {
        return Collections.emptyList();
    }

    private Collection<ProgressInfo> shoes(ProfileEntity profileEntity) {
        return shoesService.shoes(profileEntity);
    }

}
