package com.irunninglog.api.profile.impl;

import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.api.service.ResponseStatusException;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.profile.Profile;
import com.irunninglog.api.profile.ProfileRequest;
import com.irunninglog.api.profile.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService implements IProfileService {

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
                .setLastName(entity.getLastName());

        return new ProfileResponse()
                .setStatus(ResponseStatus.Ok)
                .setBody(profile);
    }

}
