package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.strava.IStravaApi;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class StravaServiceImpl implements IStravaService {

    private static final Logger LOG = LoggerFactory.getLogger(StravaServiceImpl.class);

    private final IFactory factory;
    private final IStravaApi api;

    @Autowired
    StravaServiceImpl(IFactory factory, IStravaApi api) {
        this.factory = factory;
        this.api = api;
    }

    @Override
    public IUser userFromCode(String code) throws AuthnException {
        Token token = api.token(code);

        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    @Override
    public IUser userFromToken(String token) throws AuthnException {
        StravaAthlete athlete = api.athlete(token);

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    @Override
    public IStravaAthlete athlete(IUser user) {
        StravaAthlete stravaAthlete = api.athlete(user.getToken());

        return factory.get(IStravaAthlete.class)
                .setId(stravaAthlete.getId())
                .setFirstname(stravaAthlete.getFirstname())
                .setLastname(stravaAthlete.getLastname())
                .setEmail(stravaAthlete.getEmail())
                .setAvatar(stravaAthlete.getProfileMedium());
    }

    @Override
    public List<IStravaRun> runs(IUser user) {
        List<IStravaRun> list = new ArrayList<>();

        int page = 1;
        int count = -1;
        while (count != 0) {
            StravaActivity [] activities = api.activities(user, page);

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
                .setTimezone(stravaActivity.getTimezone())
                .setDistance(stravaActivity.getDistance())
                .setShoes(stravaActivity.getGearId());
    }

    @Override
    public List<IShoe> shoes(IUser user) {
        StravaAthlete stravaAthlete = api.athlete(user.getToken());

        // TODO - Load real shoe from API
        return stravaAthlete.getShoes().stream().map(gear -> factory.get(IShoe.class)
                .setId(gear.getId())
                .setName(gear.getName())
                .setBrand(gear.getBrandName())
                .setModel(gear.getModelName())
                .setDesription(gear.getDescription())
                .setDistance(gear.getDistance())
                .setPrimary(gear.getPrimary())).collect(Collectors.toList());
    }

}
