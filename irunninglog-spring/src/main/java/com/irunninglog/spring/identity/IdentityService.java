package com.irunninglog.spring.identity;

import com.irunninglog.api.Unit;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

@ApiService
public class IdentityService implements IIdentityService {

    private static final Logger LOG = LoggerFactory.getLogger(IdentityService.class);

    private final IProfileEntityRepository profileEntityRepository;
    private final IFactory factory;

    @Autowired
    public IdentityService(IProfileEntityRepository profileEntityRepository,
                           IFactory factory) {

        this.profileEntityRepository = profileEntityRepository;
        this.factory = factory;
    }

    @Override
    public IIdentity identity(String username) {
        ProfileEntity entity = profileEntityRepository.findByEmail(username);
        if (entity == null) {
            LOG.info("Creating a new profile/identity for {}", username);
            return newIdentity(username);
        } else {
            LOG.info("Retrieving existing profile/identity for {}", username);
            return fromExistingProfile(entity, Boolean.FALSE);
        }
    }

    private IIdentity newIdentity(String username) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail(username);
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.ENGLISH);

        return fromExistingProfile(profileEntityRepository.save(profileEntity), Boolean.TRUE);
    }

    private IIdentity fromExistingProfile(ProfileEntity entity, boolean created) {
        return factory.get(IIdentity.class).setUsername(entity.getEmail()).setId(entity.getId()).setCreated(created);
    }

}
