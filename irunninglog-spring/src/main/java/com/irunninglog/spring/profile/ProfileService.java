package com.irunninglog.spring.profile;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class ProfileService implements IProfileService {

    private final IFactory factory;
    private final IStravaService stravaService;

    @Autowired
    public ProfileService(IFactory factory, IStravaService stravaService) {
        this.factory = factory;
        this.stravaService = stravaService;
    }

    @Override
    public IProfile get(IUser user) {
        IStravaAthlete athlete = stravaService.athlete(user);

        return factory.get(IProfile.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setFirstName(athlete.getFirstname())
                .setLastName(athlete.getLastname())
                .setAvatar(athlete.getAvatar());
    }

}
