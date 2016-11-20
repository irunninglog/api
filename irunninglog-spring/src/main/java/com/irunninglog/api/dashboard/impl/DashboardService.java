package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.dashboard.*;
import com.irunninglog.api.profile.impl.IProfileEntityRepository;
import com.irunninglog.api.profile.impl.ProfileEntity;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.api.service.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DashboardService implements IDashboardService {

    private final IProfileEntityRepository profileEntityRepository;
    private final DashboardShoesService shoesService;
    private final DashboardGoalsService goalsService;
    private final DashboardProgressService progressService;
    private final DashboardStreaksService streaksService;

    @Autowired
    public DashboardService(IProfileEntityRepository profileEntityRepository,
                            DashboardGoalsService goalsService,
                            DashboardShoesService shoesService,
                            DashboardProgressService progressService,
                            DashboardStreaksService streaksService) {
        super();

        this.profileEntityRepository = profileEntityRepository;
        this.goalsService = goalsService;
        this.shoesService = shoesService;
        this.progressService = progressService;
        this.streaksService = streaksService;
    }

    @Override
    public DashboardResponse get(DashboardRequest request) {
        ProfileEntity userEntity = profileEntityRepository.findOne(request.getId());
        if (userEntity == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        DashboardInfo info = new DashboardInfo();

        info.getProgress().addAll(progress(userEntity, request.getOffset()));
        info.getGoals().addAll(goals(userEntity));
        info.getShoes().addAll(shoes(userEntity));
        info.getStreaks().addAll(streaks(userEntity));

        return new DashboardResponse()
                .setBody(info)
                .setStatus(ResponseStatus.Ok);
    }

    private Collection<ProgressInfo> progress(ProfileEntity profileEntity, int offset) {
        return progressService.progress(profileEntity, offset);
    }

    private Collection<ProgressInfo> goals(ProfileEntity profileEntity) {
        return goalsService.goals(profileEntity);
    }

    private Collection<ProgressInfo> streaks(ProfileEntity profileEntity) {
        return streaksService.streaks(profileEntity);
    }

    private Collection<ProgressInfo> shoes(ProfileEntity profileEntity) {
        return shoesService.shoes(profileEntity);
    }

}
