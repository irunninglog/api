package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
final class DashboardService implements IDashboardService {

    private final IProfileEntityRepository profileEntityRepository;
    private final DashboardShoesService shoesService;
    private final DashboardGoalsService goalsService;
    private final DashboardProgressService progressService;
    private final DashboardStreaksService streaksService;
    private final IFactory factory;

    @Autowired
    public DashboardService(IProfileEntityRepository profileEntityRepository,
                            DashboardGoalsService goalsService,
                            DashboardShoesService shoesService,
                            DashboardProgressService progressService,
                            DashboardStreaksService streaksService,
                            IFactory factory) {
        super();

        this.profileEntityRepository = profileEntityRepository;
        this.goalsService = goalsService;
        this.shoesService = shoesService;
        this.progressService = progressService;
        this.streaksService = streaksService;
        this.factory = factory;
    }

    @Override
    public IDashboardInfo get(long profileId, int offset) {
        ProfileEntity profile = profileEntityRepository.findOne(profileId);
        if (profile == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        IDashboardInfo info = factory.get(IDashboardInfo.class);

        info.getProgress().addAll(progressService.progress(profile, offset));
        info.getGoals().addAll(goalsService.goals(profile));
        info.getShoes().addAll(shoesService.shoes(profile));
        info.getStreaks().addAll(streaksService.streaks(profile, offset));

        return info;
    }

}
