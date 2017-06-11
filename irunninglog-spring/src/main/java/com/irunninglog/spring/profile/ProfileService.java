package com.irunninglog.spring.profile;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.spring.service.ApiService;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
final class ProfileService implements IProfileService {

    private final IFactory factory;

    @Autowired
    public ProfileService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public IProfile get(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);

        API api = new API(apiToken);
        StravaAthlete athlete = api.getAuthenticatedAthlete();

        return factory.get(IProfile.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setFirstName(athlete.getFirstname())
                .setLastName(athlete.getLastname())
                .setAvatar(athlete.getProfileMedium());
    }

}
