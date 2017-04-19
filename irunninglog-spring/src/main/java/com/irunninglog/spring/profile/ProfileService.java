package com.irunninglog.spring.profile;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
final class ProfileService implements IProfileService {

    private final IProfileEntityRepository repository;
    private final IFactory factory;

    @Autowired
    public ProfileService(IProfileEntityRepository repository, IFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public IProfile get(long profileId) {
        ProfileEntity entity = repository.findOne(profileId);

        if (entity == null) {
            throw new ResponseStatusException(ResponseStatus.NOT_FOUND);
        }

        return factory.get(IProfile.class)
                .setId(entity.getId())
                .setUsername(entity.getUsername())
                .setWeekStart(entity.getWeekStart())
                .setPreferredUnits(entity.getPreferredUnits())
                .setWeeklyTarget(entity.getWeeklyTarget())
                .setMonthlyTarget(entity.getMonthlyTarget())
                .setYearlyTarget(entity.getYearlyTarget())
                .setDefaultRouteId(entity.getDefaultRoute() == null ? -1 : entity.getDefaultRoute().getId())
                .setDefaultRunId(entity.getDefaultRun() == null ? -1 : entity.getDefaultRun().getId())
                .setDefaultShoeId(entity.getDefaultShoe() == null ? -1 : entity.getDefaultShoe().getId());
    }

}
