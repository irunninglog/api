package com.irunninglog.spring.profile;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.strava.StravaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class ProfileService implements IProfileService {

    private final IFactory factory;
    private final StravaApiService stravaApiService;

    @Autowired
    public ProfileService(IFactory factory, StravaApiService stravaApiService) {
        this.factory = factory;
        this.stravaApiService = stravaApiService;
    }

    @Override
    public IProfile get(IUser user) {
        IAthlete athlete = stravaApiService.athlete(user);

        return factory.get(IProfile.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setFirstName(athlete.getFirstname())
                .setLastName(athlete.getLastname())
                .setAvatar(athlete.getAvatar());
    }

}
