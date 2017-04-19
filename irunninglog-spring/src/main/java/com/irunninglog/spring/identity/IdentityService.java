package com.irunninglog.spring.identity;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
final class IdentityService implements IIdentityService {

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
        ProfileEntity entity = profileEntityRepository.findByUsername(username);
        if (entity == null) {
            LOG.info("No profile exists for {}", username);
            throw new ResponseStatusException(ResponseStatus.UNAUTHORIZED);
        } else {
            LOG.info("Retrieving existing profile/identity for {}", username);
            return factory.get(IIdentity.class).setUsername(entity.getUsername()).setId(entity.getId());
        }
    }

}
