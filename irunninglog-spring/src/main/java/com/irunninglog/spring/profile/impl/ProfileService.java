package com.irunninglog.spring.profile.impl;

import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.Profile;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@ApiService
public final class ProfileService implements IProfileService {

    private final IProfileEntityRepository repository;

    @Autowired
    public ProfileService(IProfileEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProfileResponse get(ProfileRequest request) {
        ProfileEntity entity = repository.findOne(request.getId());

        if (entity == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        Profile profile = new Profile()
                .setId(entity.getId())
                .setEmail(entity.getEmail())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setBirthday(entity.getBirthday().format(DateTimeFormatter.ISO_DATE))
                .setGender(entity.getGender())
                .setWeekStart(entity.getWeekStart())
                .setPreferredUnits(entity.getPreferredUnits())
                .setWeeklyTarget(entity.getWeeklyTarget())
                .setMonthlyTarget(entity.getMonthlyTarget())
                .setYearlyTarget(entity.getYearlyTarget())
                .setDefaultRouteId(entity.getDefaultRoute() == null ? -1 : entity.getDefaultRoute().getId())
                .setDefaultRunId(entity.getDefaultRun() == null ? -1 : entity.getDefaultRun().getId())
                .setDefaultShoeId(entity.getDefaultShoe() == null ? -1 : entity.getDefaultShoe().getId());

        return new ProfileResponse()
                .setStatus(ResponseStatus.Ok)
                .setBody(profile);
    }

}
