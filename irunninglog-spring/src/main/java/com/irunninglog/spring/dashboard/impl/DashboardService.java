package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.profile.impl.IProfileEntityRepository;
import com.irunninglog.profile.impl.ProfileEntity;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.dashboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public final class DashboardService implements IDashboardService {

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
        ProfileEntity profile = profileEntityRepository.findOne(request.getId());
        if (profile == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        DashboardInfo info = new DashboardInfo();

        info.getProgress().addAll(progressService.progress(profile, request.getOffset()));
        info.getGoals().addAll(goalsService.goals(profile));
        info.getShoes().addAll(shoesService.shoes(profile));
        info.getStreaks().addAll(streaksService.streaks(profile));

        return new DashboardResponse()
                .setBody(info)
                .setStatus(ResponseStatus.Ok);
    }

}
