package com.irunninglog.spring.identity;

import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
public class IdentityService implements IIdentityService {

    private final IProfileEntityRepository profileEntityRepository;

    @Autowired
    public IdentityService(IProfileEntityRepository profileEntityRepository) {
        this.profileEntityRepository = profileEntityRepository;
    }

    @Override
    public IIdentity identity(String username) {
        return null;
    }

}
