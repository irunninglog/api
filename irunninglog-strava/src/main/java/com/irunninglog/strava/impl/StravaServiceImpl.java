package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

final class StravaServiceImpl implements IStravaService {

    private static final Logger LOG = LoggerFactory.getLogger(StravaServiceImpl.class);

    private final int id;
    private final String secret;

    private final IFactory factory;

    StravaServiceImpl(Environment environment, IFactory factory) {
        this.factory = factory;

        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public IUser userFromCode(String code) throws AuthnException {
        AuthorisationService service = new AuthorisationServiceImpl();
        Token token= service.tokenExchange(id, secret, code);
        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    @Override
    public IUser userFromToken(String token) throws AuthnException {
        Token apiToken = new Token();
        apiToken.setToken(token);
        API api = new API(apiToken);

        StravaAthlete athlete = api.getAuthenticatedAthlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    @Override
    public IStravaAthlete athlete(IUser user) {
        Token apiToken = new Token();
        apiToken.setToken(user.getToken());

        API api = new API(apiToken);
        StravaAthlete stravaAthlete = api.getAuthenticatedAthlete();

        return factory.get(IStravaAthlete.class)
                .setId(stravaAthlete.getId())
                .setFirstname(stravaAthlete.getFirstname())
                .setLastname(stravaAthlete.getLastname())
                .setEmail(stravaAthlete.getEmail()).setAvatar(stravaAthlete.getProfileMedium());
    }

    @Override
    public List<IStravaRun> runs(IUser user) {
        Token apiToken = new Token();
        apiToken.setToken(user.getToken());

        API api = new API(apiToken);

        List<IStravaRun> list = new ArrayList<>();

        int page = 1;
        int count = -1;
        while (count != 0) {
            StravaActivity [] activities = api.listAuthenticatedAthleteActivities(null, null, page, 200);

            count = activities.length;

            for (StravaActivity stravaActivity : activities) {
                if (stravaActivity.getType() == StravaActivityType.RUN) {
                    list.add(fromStravaActivity(stravaActivity));
                }
            }

            LOG.info("runs:{}:{}:{}", user.getUsername(), page, count);

            page++;
        }

        LOG.info("runs:{}:{}", user.getUsername(), list.size());

        return list;
    }

    private IStravaRun fromStravaActivity(StravaActivity stravaActivity) {
        return factory.get(IStravaRun.class)
                .setId(stravaActivity.getId())
                .setStartTime(stravaActivity.getStartDate())
                .setStartTimeLocal(stravaActivity.getStartDateLocal())
                .setTimezone(stravaActivity.getTimezone());
    }

}
